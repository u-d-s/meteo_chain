package com.birdmanbros.blockchain.meteo_chain;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
	private String type;
	private String data;

	
	public void addData(String data) {
		this.data += data;
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
	
	
	public Message(String type, String data) {
		this.type = type;
		this.data = data;
	}
	
	public Message() {
		this.type = "";
		this.data = "";
	}
	
}
