package com.birdmanbros.blockchain.meteo_chain;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class Block {
	private Long index;
	private String previousHash;
	private String timestamp;
	private String data;
	private String proof;
	private String hash;
	private int difficulty;
	private StringBuilder blockString;
//	@JsonIgnore
//	private ObjectMapper mapper;

	static private int slots = 101;
	
	
	public Boolean isNotLongerThan(Chain chain) {
		return index <= chain.getLatestBlock().getIndex() ? true : false;
	}
	
	public Boolean canBeAppendedTo(Chain chain) {
		return (index == 1 + chain.getLatestBlock().getIndex()) && (previousHash.equals(chain.getLatestBlock().getHash())) ?
				true : false ;
	}
	
	public Block generateNextBlock(String data, int difficulty) {
		return new Block(index + 1, hash, LocalDateTime.now().toString(), data, difficulty);
	}
	
	public boolean isValidBlock(Block previousBlock) {
		return (index != 1+ previousBlock.getIndex()) ||
				(!previousHash.equals(previousBlock.getHash()))	|| 
				(!hash.equals(this.calculateHash())) ? 
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
	
//	private BigInteger calculateProof(BigInteger proof) {
//		BigInteger nextProof = BigInteger.valueOf(0L);
//		
//		while(validNextProof(nextProof)) {
//			nextProof.add(BigInteger.ONE);
//			System.out.format(".");
//		}
//		System.out.format("%n");
//		
//		return nextProof;
//	}
	
//	private boolean validNextProof(BigInteger nextProof) {
//		byte[] cipher_byte = null;
//		
//		byte[] guess_bytes = nextProof.multiply(proof).toString(16).getBytes();
//		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-256");
//			md.update(guess_bytes);
//			cipher_byte = md.digest();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		
//		return 32 - cipher_byte.length == difficulty;
//		
//	}
	
	
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
		updateBlockString();
	}
	public String getPreviousHash() {
		return previousHash;
	}
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
		updateBlockString();
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
		updateBlockString();
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
		updateBlockString();
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getProof() {
		return proof;
	}
	public void setProof(String proof) {
		this.proof = proof;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		updateBlockString();
	}

	public static int getSlots() {
		return slots;
	}
	public static void setSlots(int slots) {
		Block.slots = slots;
	}

	public Block() {
		super();
//		mapper = new ObjectMapper();
	}
	public Block(Long index, String previousHash, String timestamp, String data, int difficulty) {
		this();
		setIndex(index);
		setPreviousHash(previousHash);
		setTimestamp(timestamp);
		setData(data);
		setDifficulty(difficulty);
		
		calculateHashAndProof();
		
	}
//	public Block(Long index, String previousHash, String timestamp, String data, String hash) {
//		this();
//		this.index = index;
//		this.previousHash = previousHash;
//		this.timestamp = timestamp;
//		this.data = data;
//		this.hash = hash;
//	}


//	public Block(String jsonStr) {
//		super();
//		try {
//			Block tmp = mapper.readValue(jsonStr, Block.class);
//
//			this.index = tmp.getIndex();
//			this.previousHash = tmp.getPreviousHash();
//			this.timestamp = tmp.getTimestamp();
//			this.data = tmp.getData();
//			this.hash = tmp.getHash();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	private void updateBlockString() {
		blockString.setLength(0);
		
		blockString.append(new Long(index).toString());
		blockString.append(previousHash);
		blockString.append(timestamp);
		blockString.append(data);
		blockString.append(new Integer(difficulty).toString());
	}
	
	private void calculateHashAndProof() {
		BigInteger proof = BigInteger.valueOf(0L);
	
		String calculatedHash = null;
		
		int printCounter = 1;
		System.out.format("%n");
		while(!validProof(proof)) {
			proof.add(BigInteger.ONE);
			
			if(printCounter % 1000 == 0){ System.out.format(".");printCounter=1; }
		}
		System.out.format("%n");
		
		this.proof = proof.toString(16);
		this.hash = calculateHash(proof); 
		
//		Long moduloOperand = index + sumAsNumber(previousHash)+sumAsNumber(timestamp)+sumAsNumber(data);
//		return new Long(moduloOperand % Block.slots).toString();
		
		
//		private BigInteger calculateProof(BigInteger proof) {
//		BigInteger nextProof = BigInteger.valueOf(0L);
//		
//		while(validNextProof(nextProof)) {
//			nextProof.add(BigInteger.ONE);
//			System.out.format(".");
//		}
//		System.out.format("%n");
//		
//		return nextProof;
//	}
		
	
	}
	
	private String calculateHash() {
		return calculateHash(new BigInteger(this.proof,16));
	}
	
	private String calculateHash(BigInteger proof) {
		byte[] cipherBytes = null;
		
		int lengthOfBlockString = blockString.length();
		blockString.append(proof);
		byte[] blockStringWithProof = blockString.toString().getBytes(); 
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(blockStringWithProof);
			cipherBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		blockString.setLength(lengthOfBlockString);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cipherBytes.length; i++) {
			sb.append(String.format("%02x", cipherBytes[i]));
		}
		
		return sb.toString();
		
	}
		
	
	
	private boolean validProof(BigInteger proof) {
		int hashLength = calculateHash(proof).getBytes().length;
		return (32 - hashLength >= difficulty) ? true : false ;
	
}
	
	
	
	
//	public String calculateHash() {
//		return calculateHash(index, previousHash, timestamp, data);
//	}
	
//	private Long sumAsNumber(String str) {
//		long sum = 0;
//		try {
//			byte[] bytes = str.getBytes("UTF-8");
//			for(byte oneByte : bytes) {
//				sum += (long)oneByte;
//			}
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		return sum;
//	}
	

}