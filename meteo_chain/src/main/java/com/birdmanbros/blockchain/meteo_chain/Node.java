package com.birdmanbros.blockchain.meteo_chain;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

//@Path("node")
public class Node {
	private Chain chain;
	private String url;
//	@JsonIgnore
//	@JsonManagedReference
//	@JsonBackReference
	private List<WebTarget> peers;
	private String memo;
	private Client httpClient;
	@JsonIgnore
	private ObjectMapper mapper;
	
	

	public void addNewBlock(String data) {
		chain.addNewBlock(data);
	}

	public void addPeer(String c) {
		if(!(c == null || c.isEmpty())) {
			peers.add(httpClient.target(c).path("/meteochain/node/p2pMessage"));
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
	
	
//	public void broadcast(String type, String data) {
//		Message message = new Message(type, data);
//		for(WebTarget wt: peers) {
//			sendP2PMessage(wt,message);
//		}
//	}
		
//	public void sendP2PMessage(WebTarget wt, Message message) {
//		Entity<Form> entity = Entity.entity(new Form().param("message", message.toJson()),
//			    MediaType.APPLICATION_FORM_URLENCODED_TYPE);
//		wt.request().post(entity, String.class);
//	}

	
	public Message handleMessage(String message_str) throws JsonParseException, IOException{
		Message req = mapper.readValue(message_str, Message.class);
				
		switch(req.getType()) {
		case "TEST":
			System.err.println(">> handleMessage TEST: "+message_str);
			return new Message("TEST", "received "+message_str);
			
		case "REQ_ALL":
			System.err.println(">> handleMessage REQ_ALL: "+message_str);
			return resAll();
		case "REQ_LATEST":
			System.err.println(">> handleMessage REQ_LATEST: "+message_str);
			return resLatest();
		case "SEND_LATEST":
			System.err.println(">> handleMessage SEND_LATEST: "+message_str);
			return receivedLatestBlock(req);
		default:
			System.err.println(">> I don't know Message.type. "+message_str);
			return new Message("DEFAULT", "received "+message_str);
		}
	}
	
	
	private Message resAll() throws JsonProcessingException {
		return new Message("RES_ALL",mapper.writeValueAsString(chain));
	}
	
	private Message resLatest() throws JsonProcessingException {
		return new Message("RES_LATEST", mapper.writeValueAsString(chain.getLatestBlock()));
	}
	
	private Message receivedLatestBlock(Message message) throws IOException {
		Message res = new Message("RES_RECEIVED_LATEST", "received "+ mapper.writeValueAsString(message));
		System.err.format("DEBUG %s %s5n", message.getType(), message.getData());
		Block receivedBlock = mapper.readValue(message.getData(), Block.class);
		
		if(receivedBlock.isNotLongerThan(chain)) {
			res.addData("\n-- received blockchain is not longer than current blockchain. Did nothing. --");
		}else if(receivedBlock.canBeAppndedTo(chain)) {
			res.addData("\n-- we can append the received block to our chain. --");
		}else {
			res.addData("\n-- received blockchain is longer than current blockchain. --");
		}
		
		return res;
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
	
	public void setPeers(List<WebTarget> peers) {
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
		chain = new Chain();
		peers = new LinkedList<WebTarget>();
		httpClient = ClientBuilder.newClient();
		memo = "alo";
		mapper = new ObjectMapper();
	}
	public Node(String url) {
		this();
		this.url = url;
	}

}
