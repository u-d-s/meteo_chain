package com.birdmanbros.blockchain.meteo_chain;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Node {
	private Chain chain;
	private String uri;
	@JsonIgnore
	private List<Node> counterparties;
	
	
	public void addCounterparty(Node c) {
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
	
	
	public Chain getChain() {
		return chain;
	}
	public void setChain(Chain chain) {
		this.chain = chain;
	}
	public List<Node> getCounterparties() {
		return counterparties;
	}
	public void setCounterparties(List<Node> counterparties) {
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
		counterparties = new LinkedList<Node>();
	}
	public Node(String uri) {
		this();
		this.uri = uri;
	}

}
