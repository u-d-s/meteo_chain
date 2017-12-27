package com.birdmanbros.blockchain.meteo_chain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
	private String type;
	private String data;
	private ObjectMapper mapper;
	
	public String toJson() {
		String result;
			try {
				result = mapper.writeValueAsString(this);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				result = e.toString();
			}
		return result;
	}
	
	
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
	
	public Message() {
		mapper = new ObjectMapper();
	}
	
	public Message(String type, String data) {
		this();
		this.type = type;
		this.data = data;
	}
}
