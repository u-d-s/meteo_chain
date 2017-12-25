package com.birdmanbros.blockchain.meteo_chain;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("node")
public class EndPoints {
	static Node node;

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
		return "post: "+memo;
	}
	
	@GET
	@Path("peers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPeers() {
		return  String.join(",", node.getPeers());
	}

	@POST
	@Path("peers")
	@Produces(MediaType.TEXT_PLAIN)
	public String postPeers(@FormParam("URI") String uri) {
		node.addPeer(uri);
		return "add: " + uri;
	}

	@GET
	@Path("chain")
	@Produces(MediaType.APPLICATION_JSON)
	public Chain getChain() {
		return node.getChain();
	}
	
//	!!!!!!!! UNDER CONSTRUCTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!
//	@POST
//	@Path("newBlock")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String postNewBlock(@FormParam("data") String data) {
//		node.addNewBlock(data);
//		return "add: " + data;
//	}
	

}
