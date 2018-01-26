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
	
//	public boolean isInvalidChain() {
//		System.err.format(">>> isInvalidChain()%n");
//		Boolean result;
//		if(!(super.getFirst().equals(new Chain().getFirst()))) {
//			result = true;
//		}else {
//			System.err.format(">>> isInvalidChain() 1%n");
//			Iterator<Block> it = this.iterator();
//			Block previousBlock = it.next();
//			Block currentBlock;
//			result = false;
//			while(it.hasNext()) {
//				System.err.format(">>> isInvalidChain() 2.1%n");
//				currentBlock = it.next();
////				System.err.format(">>> isInvalidChain() 2.5 cur:%s prev:%s%n",currentBlock.getIndex(), previousBlock.getIndex());
//				if(!currentBlock.isValidBlock(previousBlock)) {
//					System.err.format(">>> isInvalidChain() 3%n");
//					result = true;
//				}
//				System.err.format(">>> isInvalidChain() 4%n");
//				previousBlock = currentBlock;
//			}
//		}
//		System.err.format(">>> isInvalidChain() 5%n");
//		return result;
//	}
	
	public Chain addNewBlock(String data,int difficulty) {
//		System.err.println("chain: addNewBlock");
		addNewBlock(getLatestBlock().generateNextBlock(data,difficulty));
		return this;
	}
	
	public void addNewBlock(Block b) {
		add(b);
	}

	
	public Block getLatestBlock() {
		return getLast();
	}
	

	
//	public Chain(Long index, String previousHash, String timestamp, String data) {
//		super();
////		chain = new LinkedList<Block>();
//		super.add(new Block(index, previousHash, timestamp, data));
//	}
	public Chain() {
		;
	}
	public Chain(Block block) {
		this();
		super.add(block);
	}
//	@JsonCreator
//	private Chain(@JsonProperty("index") Long index, @JsonProperty("previousHash") String previousHash,
//			@JsonProperty("timestamp") String timestamp, @JsonProperty("data") String data,
//			@JsonProperty("hash") String hash) {
//		super();
//		super.add(new Block(index, previousHash, timestamp, data, hash));
//		System.err.println("jsoncreator");
//	}
	
}


//Block(Long index, String previousHash, String timestamp, String data) {