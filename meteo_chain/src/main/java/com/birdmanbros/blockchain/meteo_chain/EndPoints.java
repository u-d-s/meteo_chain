package com.birdmanbros.blockchain.meteo_chain;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("node")
public class EndPoints {
	static Node node;
	// static ObjectMapper mapper = new ObjectMapper();

	public static Node getNode() {
		return node;
	}

	public static void setNode(Node node) {
		EndPoints.node = node;
	}

	@GET
	@Path("ping")
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		return "pong";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Node root() {
		return node;
	}

	@POST
	@Path("memo")
	@Produces(MediaType.TEXT_PLAIN)
	public String postMemo(@FormParam("memo") String memo) {
		node.setMemo(memo);
		return "post: " + memo;
	}

	@GET
	@Path("peers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPeers() {
		// LinkedList<String> uri_list = new LinkedList<>();
		// for(WebTarget wt: node.getPeers()) {
		// uri_list.add(wt.getUri().toString());
		// }
		return node.getPeers();
	}

	@POST
	@Path("peers")
	@Produces(MediaType.TEXT_PLAIN)
	public String postPeers(@FormParam("URL") String url) {
		node.addPeer(url);
		return "add: " + url;
	}

	@GET
	@Path("chain")
	@Produces(MediaType.APPLICATION_JSON)
	public Chain getChain() {
		return node.getChain();
	}

	@POST
	@Path("newBlock")
	@Produces(MediaType.TEXT_PLAIN)
	public String postNewBlock(@FormParam("data") String data) {
		node.addNewBlock(data);
		return "add: " + data;
	}

	@POST
	@Path("broadcast")
	@Produces(MediaType.TEXT_PLAIN)
	public String postBroadcast(@FormParam("type") String type, @FormParam("data") String data) {
		Message message = new Message();
		message.setType(type);
		message.setData(data);
		node.broadcast(message);
		return "broadcast: " + " " + type + " " + data;
	}

	@POST
	@Path("p2pMessage")
	@Produces(MediaType.APPLICATION_JSON)
	public Message postP2PMessage(@FormParam("message") String message) {
		Message res = null;
		try {
			res = node.handleRequest(message);
		} catch (IOException e) {
			e.printStackTrace();
			res = new Message("IOEXCEPION", e.toString());
		}
		return res;
	}

	@POST
	@Path("responseMessage")
	@Produces(MediaType.TEXT_PLAIN)
	public String postresponseMessage(@FormParam("message") String message) {
		try {
			node.handleResponse(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "received " + message;
	}

}
