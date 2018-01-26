package com.birdmanbros.blockchain.meteo_chain;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Path("node")
public class Node {
	static private Block genesisBlock = new Block(0L,"-1", "20171218", "alphaandomega", 1); // genesis block
	static private int difficulty = 1;
	private Chain chain;
	private String url;
//	@JsonIgnore
//	@JsonManagedReference
//	@JsonBackReference
	private Set<WebTarget> peers;
	private String memo;
	private Client httpClient;
	@JsonIgnore
	private ObjectMapper mapper;
	
	
	public void broadcastLatestBlock() throws IOException {
		broadcast(new Message("SEND_LATEST", mapper.writeValueAsString(chain.getLatestBlock())));
	}
	
	public void initializeChain() {
		chain = new Chain(genesisBlock);
	}

	public void addNewBlock(String data) {
		chain.addNewBlock(data,difficulty);
	}

	public void addPeer(String c) throws IOException {
		WebTarget wt = httpClient.target(c).path("/meteochain/node/p2pMessage");
		if(!(c == null || c.isEmpty())) {
			peers.add(wt);
			sendRequest(wt, new Message("REQ_HANDSHAKE", url));
//			System.err.format("DEBUG>> %s%n %s%n", target.getUri(), c);
		}
	}



//	public void handleMessage(Message message) throws MeteoException {
//		System.out.format(">> Node(%s) received message -- type:%s data:%s%n", url, message.getType(),
//				message.getData());
//		if (message.getType().equals("QUERY_LATEST")) {
//			System.out.println(">> QUERY_LATEST");
//		} else if (message.getType().equals("QUERY_ALL")) {
//			System.out.println(">> QUERY_ALL");
//		} else if (message.getType().equals("RESPONSE_BLOCKCHAIN")) {
//			System.out.println(">> RESPONSE_BLOCKCHAIN");
//		} else {
//			throw new MeteoExceptionP2PCommunication("Wrong Message");
//		}
//	}

//	public void startHttpServer() {
//		// final String BASE_URI = "http://localhost:58080/meteo_chain/";
//		final String BASE_URI = url + "/meteochain";
//		final ResourceConfig rc = new ResourceConfig().packages("com.birdmanbros.blockchain.meteo_chain");
//		final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
//		System.out.println(String.format(
//				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
//				BASE_URI));
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		server.stop();
//	}

//	public String toJson() {
//		String result;
//		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
//
//		try {
//			result = mapper.writeValueAsString(this);
//		} catch (Exception x) {
//			x.printStackTrace();
//			result = "EXCEPTION from ObjectMpper.writeValueAsString";
//		}
//
//		return result;
//	}
	
	
//	public void broadcast(Block block) {
////		for(WebTarget wt: peers) {
////			sendP2PMessage(wt,message);
////		}
//	}
	
	public void broadcast(Message message) {
		try {
			System.err.format(">>>broadcast %s%n", mapper.writeValueAsString(message));
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

		try {
			for (WebTarget wt : peers) {
				sendRequest(wt, message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Message sendRequest(WebTarget wt, Message message) throws IOException {
		try {
			System.err.format(">>>sendRequest %s%n", mapper.writeValueAsString(message));
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		Entity<Form> entity = Entity.entity(
				new Form().param("message", mapper.writeValueAsString(message)),
						MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		String res = wt.request().post(entity, String.class);
		
		handleResponse(res);
		return mapper.readValue(res, Message.class);
	}
	
	private void replaceChain(String receivedChain_str) throws IOException {
		System.err.format("SSSS %s%n",receivedChain_str);
		Chain receivedChain = mapper.readValue(receivedChain_str,Chain.class);
		
//		Object[] bs = receivedChain.toArray();
//		
//		System.err.format("DDDD %d %s ---- %s%n", 
//				receivedChain.size(), 
//				mapper.writeValueAsString(receivedChain.get(0)), 
//				mapper.writeValueAsString(receivedChain.get(1)));
//		System.err.format("DDDD %s --- %s%n", bs[0], bs[1]); 
		
		if(receivedChain.size() <= chain.size()) {
			System.out.format("the received blockchain is not longer than the current blockchain.%n");
		}else if(isInvalidChain(receivedChain)) {
			System.out.format("the received blockchain is invalid.%n");
		}else {
			System.out.format("the received blockchain is valid. Replacing the current blockchain with the received blockchain.%n");
			chain = receivedChain;
			broadcastLatestBlock();
//			broadcast(new Message("SEND_LATEST", mapper.writeValueAsString(chain.getLatestBlock())));
		}
	}
	
		
//	public void sendP2PMessage(WebTarget wt, Message message) {
//		Entity<Form> entity = Entity.entity(new Form().param("message", message.toJson()),
//			    MediaType.APPLICATION_FORM_URLENCODED_TYPE);
//		wt.request().post(entity, String.class);
//	}

	
	public void handleResponse(String message_str) throws JsonParseException, IOException {
		Message res = mapper.readValue(message_str, Message.class);
		
		switch(res.getType()) {
		case "RES_ALL":
			System.err.println(">> handleResponse RES_ALL: "+message_str);
			replaceChain(res.getData());
			break;
		case "RES_SEND_LATEST":
			System.err.println(">> handleResponse RES_SEND_LATEST: "+message_str);
			break;
		case "RES_HANDSHAKE":
			System.err.println(">> handleResponse RES_HANDSHAKE: "+message_str);
			Message m = receivedLatestBlock(res);
			System.err.println(">> receivedLatestBlock right after handshake: "+mapper.writeValueAsString(m));
			break;
		default:
			System.err.println(">> I don't know Message.type in handleResponse. "+message_str);
			break;
		}
	}

	public Message handleRequest(String message_str) throws JsonParseException, IOException{
		Message req = mapper.readValue(message_str, Message.class);
				
		switch(req.getType()) {
		case "TEST":
			System.err.println(">> handleRequest TEST: "+message_str);
			return new Message("TEST", "received "+message_str);
			
		case "REQ_ALL":
			System.err.println(">> handleRequest REQ_ALL: "+message_str);
			return resAll();
		case "REQ_LATEST":
			System.err.println(">> handleRequest REQ_LATEST: "+message_str);
			return resLatest();
		case "SEND_LATEST":
			System.err.println(">> handleRequest SEND_LATEST: "+message_str);
			return receivedLatestBlock(req);
		case "REQ_HANDSHAKE":
			return resHandshake(req);
		default:
			System.err.println(">> I don't know Message.type. "+message_str);
			return new Message("DEFAULT", "received "+message_str);
		}
	}
	
	private Message resHandshake(Message req) throws IOException {
		if(!(req.getData() == null || req.getData().isEmpty())) {
			peers.add(httpClient.target(req.getData()).path("/meteochain/node/p2pMessage"));
		}
		
		return new Message("RES_HANDSHAKE", mapper.writeValueAsString(chain.getLatestBlock()));
	}
	
	private Message resAll() throws JsonProcessingException {
		return new Message("RES_ALL",mapper.writeValueAsString(chain));
	}
	
	private Message resLatest() throws JsonProcessingException {
		return new Message("RES_LATEST", mapper.writeValueAsString(chain.getLatestBlock()));
	}
	
	private Message receivedLatestBlock(Message message) throws IOException {
		Message res = new Message("RES_SEND_LATEST", "received "+ mapper.writeValueAsString(message));
		Block receivedBlock = mapper.readValue(message.getData(), Block.class);
		
		if(receivedBlock.isNotLongerThan(chain)) {
			System.err.format("-- received blockchain is not longer than current blockchain. Did nothing. --\n");
			res.addData("\n-- received blockchain is not longer than current blockchain. Did nothing. --");
		}else if(receivedBlock.canBeAppendedTo(chain)) {
			System.err.format("-- we can append the received block to our chain. --\n");
			res.addData("\n-- we can append the received block to our chain. --");
			chain.addNewBlock(receivedBlock);
			broadcastLatestBlock();
//			broadcast(new Message("SEND_LATEST", mapper.writeValueAsString(chain.getLatestBlock())));
		}else {
			System.err.format("-- received blockchain is longer than current blockchain. but it can't be appended right now. i will broadcast quary for whole chain. --\n");
			res.addData("\n-- received blockchain is longer than current blockchain. but it can't be appended right now. i will broadcast quary for whole chain. --");
			broadcast(new Message("REQ_ALL","REQ_ALL to replaceChain"));
		}
		
		return res;
	}
	
	private boolean isInvalidChain(Chain receivedChain) {
		System.err.format(">>> isInvalidChain()%n");
		Boolean result;
		if(!(receivedChain.getFirst().equals(genesisBlock))) {
			result = true;
		}else {
			System.err.format(">>> isInvalidChain() 1%n");
			Iterator<Block> it = receivedChain.iterator();
			Block previousBlock = it.next();
			Block currentBlock;
			result = false;
			while(it.hasNext()) {
				System.err.format(">>> isInvalidChain() 2%n");
				currentBlock = it.next();
				System.err.format(">>> isInvalidChain() 2.5 cur:%s prev:%s%n",currentBlock.getIndex(), previousBlock.getIndex());
				if(!currentBlock.isValidBlock(previousBlock)) {
					System.err.format(">>> isInvalidChain() 3%n");
					result = true;
				}
				System.err.format(">>> isInvalidChain() 4%n");
				previousBlock = currentBlock;
			}
		}
		System.err.format(">>> isInvalidChain() 5%n");
		return result;
	}


	
//	private void sendAll() {
//		writeTo()
//	}
//	
//	public void writeTo(Node c, Message message) throws MeteoException {
//	c.handleMessage(message);
//}
	

	
	
	
	
	public Chain getChain() {
		return chain;
	}
	public void setChain(Chain chain) {
		this.chain = chain;
	}

	public String getPeers() {
		StringBuilder sb = new StringBuilder();
		if (peers.size() == 0) {
			sb.append("[]");
		} else {
			sb.append("[");
			for (WebTarget wt : peers) {
				sb.append("\"");
				sb.append(wt.getUri().toString());
				sb.append("\",");
			}
			sb.setCharAt(sb.length() - 1, ']');
		}
		return sb.toString();
	}
	
	public void setPeers(Set<WebTarget> peers) {
		this.peers = peers;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Node() {
		initializeChain();
		peers = new HashSet<WebTarget>();
		httpClient = ClientBuilder.newClient();
		memo = "alo";
		mapper = new ObjectMapper();
	}
	public Node(String url) {
		this();
		this.url = url;
	}

}
