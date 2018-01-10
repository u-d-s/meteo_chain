package com.birdmanbros.blockchain.meteo_chain;

import java.util.Iterator;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Chain extends LinkedList<Block>{

	public boolean isNotLongerThan(Chain chain) {
		System.err.format("DDDDDD %d %d%n",super.size(), chain.size() );
		return super.size() <= chain.size() ? true : false;
	}
	
	public boolean isInvalidChain() {
		Boolean result;
		if(!(super.getFirst().equals(new Chain().getFirst()))) {
			result = true;
		}else {
			Iterator<Block> it = this.iterator();
			Block previousBlock = it.next();
			Block currentBlock;
			result = false;
			while(it.hasNext()) {
				currentBlock = it.next();
				if(!currentBlock.isValidBlock(previousBlock)) {
					result = true;
				}
				previousBlock = currentBlock;
			}
		}
		
		return result;
	}
	
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
		System.err.println("chain()");
		
	}
	
	@JsonCreator
	private Chain(@JsonProperty("index") Long index, @JsonProperty("previousHash") String previousHash,
			@JsonProperty("timestamp") String timestamp, @JsonProperty("data") String data,
			@JsonProperty("hash") String hash) {
		super();
		super.add(new Block(index, previousHash, timestamp, data, hash));
		System.err.println("jsoncreator");
	}
	
}


//Block(Long index, String previousHash, String timestamp, String data) {