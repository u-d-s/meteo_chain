package com.birdmanbros.blockchain.meteo_chain;

import java.util.LinkedList;
import java.util.List;

public class Chain {
	private List<Block> chain;
	
	
	public List<Block> getChain() {
		return chain;
	}
	public void setChain(List<Block> chain) {
		this.chain = chain;
	}

	
	
	public Chain(Long index, String previousHash, String timestamp, String data) {
		chain = new LinkedList<Block>();
		chain.add(new Block(index, previousHash, timestamp, data));
	}
	
	public Chain() {
		// genesis block
		this(0L,"-1", "20171218", "alpha&omega");
		
	}

}


//Block(Long index, String previousHash, String timestamp, String data) {