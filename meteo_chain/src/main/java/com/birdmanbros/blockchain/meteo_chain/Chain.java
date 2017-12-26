package com.birdmanbros.blockchain.meteo_chain;

import java.util.LinkedList;

public class Chain extends LinkedList<Block>{
//	private List<Block> chain;
//	
//	
//	public List<Block> getChain() {
//		return chain;
//	}
//	public void setChain(List<Block> chain) {
//		this.chain = chain;
//	}
	
	public void addNewBlock(String data) {
//		System.err.println("chain: addNewBlock");
		setLatestBlock(getLatestBlock().generateNextBlock(data));
	}
	
	public Block getLatestBlock() {
		return getLast();
	}
	
	public void setLatestBlock(Block b) {
		add(b);
	}

	
	
	public Chain(Long index, String previousHash, String timestamp, String data) {
		super();
//		chain = new LinkedList<Block>();
		super.add(new Block(index, previousHash, timestamp, data));
	}
	
	public Chain() {
		// genesis block
		this(0L,"-1", "20171218", "alpha&omega");
		
	}

}


//Block(Long index, String previousHash, String timestamp, String data) {