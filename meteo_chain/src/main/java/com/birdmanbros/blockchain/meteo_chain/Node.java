package com.birdmanbros.blockchain.meteo_chain;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Path("node")
public class Node {
	private Chain chain;
	private String uri;
//	@JsonIgnore
	private List<String> counterparties;
	
	
	public void addCounterparty(String c) {
		counterparties.add(c);
	}
	
	public void writeTo(Node c, Message message) throws MeteoException {
		c.handleMessage(message);
	}
	
	public void handleMessage(Message message) throws MeteoException {
		System.out.format(">> Node(%s) received message -- type:%s data:%s%n", uri,message.getType(),message.getData());
		if(message.getType().equals("QUERY_LATEST")) {
			System.out.println(">> QUERY_LATEST");
		}else if(message.getType().equals("QUERY_ALL")) {
			System.out.println(">> QUERY_ALL");
		}else if(message.getType().equals("RESPONSE_BLOCKCHAIN")) {
			System.out.println(">> RESPONSE_BLOCKCHAIN");
		}else {
			throw new MeteoExceptionP2PCommunication("Wrong Message");
		}
	}
	
	public void startHttpServer(String uri) {
//		final String BASE_URI = "http://localhost:58080/meteo_chain/";
		final String BASE_URI = uri;
		final ResourceConfig rc = new ResourceConfig().packages("com.birdmanbros.blockchain.meteo_chain");
		final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.stop();
	}
	
	public String toJson() {
		String result;
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		
		try {
			result = mapper.writeValueAsString(this);
		}catch(Exception x) {
			x.printStackTrace();
			result = "EXCEPTION from ObjectMpper.writeValueAsString";
		}
		
		return result;
	}
	

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "pong";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Node node() {
        return this;
    }
    
    @GET
    @Path("counterparties")
    @Produces(MediaType.APPLICATION_JSON)
    public String couterpartiesGET() {
        return String.join(",", counterparties);
    }
    
    @POST
    @Path("counterparties")
    @Produces(MediaType.TEXT_PLAIN)
    public String couterpartiesPOST() {
    	 return String.join(",", counterparties);
    }
	
	
	
	public Chain getChain() {
		return chain;
	}
	public void setChain(Chain chain) {
		this.chain = chain;
	}
	public List<String> getCounterparties() {
		return counterparties;
	}
	public void setCounterparties(List<String> counterparties) {
		this.counterparties = counterparties;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	
	public Node() {
		chain = new Chain();
		counterparties = new LinkedList<String>();
	}
	public Node(String uri) {
		this();
		this.uri = uri;
	}

}
