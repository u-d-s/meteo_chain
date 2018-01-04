package com.birdmanbros.blockchain.meteo_chain;

import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Chain extends LinkedList<Block>{

	
	public Chain addNewBlock(String data) {
//		System.err.println("chain: addNewBlock");
		addNewBlock(getLatestBlock().generateNextBlock(data));
		return this;
	}
	
	public void addNewBlock(Block b) {
		add(b);
	}

	
	public Block getLatestBlock() {
		return getLast();
	}
	

	
	
	public Chain(Long index, String previousHash, String timestamp, String data) {
		super();
//		chain = new LinkedList<Block>();
		super.add(new Block(index, previousHash, timestamp, data));
	}
	
	public Chain() {
		// genesis block
		this(0L,"-1", "20171218", "alphaandomega");
		
	}

}


//Block(Long index, String previousHash, String timestamp, String data) {