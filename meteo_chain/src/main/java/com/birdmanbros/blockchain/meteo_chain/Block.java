package com.birdmanbros.blockchain.meteo_chain;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public class Block {
	private Long index;
	private String previousHash;
	private String timestamp;
	private String data;
	private String hash;

	static private int slots = 101;
	
	public Block generateNextBlock(String data) {
		return new Block(index + 1, hash, LocalDateTime.now().toString(), data);
//		 Block(Long index, String previousHash, String timestamp, String data, String hash)
		
	}
	
	public boolean isValidBlock(Block previousBlock) {
		return (index != previousBlock.getIndex() ||
				previousHash != previousBlock.getHash()	|| 
				hash != previousBlock.calculateHash()) ? 
						false : true;
	}

	public boolean equals(Block block) {
		return (index != block.getIndex() || 
				!previousHash.equals(block.getPreviousHash()) ||
				!timestamp.equals(block.getTimestamp()) || 
				!data.equals(block.getData())|| 
				!hash.equals(block.getHash())) ? 
						false : true;

	}
	
	
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
	}
	public String getPreviousHash() {
		return previousHash;
	}
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}


	public Block(Long index, String previousHash, String timestamp, String data) {
		super();
		this.index = index;
		this.previousHash = previousHash;
		this.timestamp = timestamp;
		this.data = data;
		this.hash = calculateHash();
	}
	
	private String calculateHash(Long index, String previousHash, String timestamp, String data) {
		Long moduloOperand = index + sumAsNumber(previousHash)+sumAsNumber(timestamp)+sumAsNumber(data);
		return new Long(moduloOperand % Block.slots).toString();
	}
	
	public String calculateHash() {
		return calculateHash(index, previousHash, timestamp, data);
	}
	
	private Long sumAsNumber(String str) {
		long sum = 0;
		try {
			byte[] bytes = str.getBytes("UTF-8");
			for(byte oneByte : bytes) {
				sum += (long)oneByte;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return sum;
	}
	

}