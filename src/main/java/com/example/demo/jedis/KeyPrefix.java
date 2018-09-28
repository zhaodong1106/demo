package com.example.demo.jedis;

public interface KeyPrefix {
		
	public int expireSeconds();
	
	public String getPrefix();
	
}
