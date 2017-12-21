package com.birdmanbros.blockchain.meteo_chain;

public class Message {
	private String type;
	private String data;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	public Message(String type, String data) {
		this.type = type;
		this.data = data;
	}
}
