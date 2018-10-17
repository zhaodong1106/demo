package com.example.demo.jedis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisPoolFactory {

//	@Value("${redis.host}")
	private String host;
//	@Value("${redis.port}")
	private int port;
//	@Value("${redis.poolMaxIdle}")
	private int poolMaxIdle;
//	@Value("${redis.poolMaxTotal}")
	private int poolMaxTotal;
//	@Value("${redis.timeout}")
	private int timeout;

	
	@Bean
	public JedisPool JedisPoolFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(poolMaxIdle);
		poolConfig.setMaxTotal(poolMaxTotal);
		JedisPool jp = new JedisPool(poolConfig,host, port,
				timeout*1000);
		return jp;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPoolMaxIdle() {
		return poolMaxIdle;
	}

	public void setPoolMaxIdle(int poolMaxIdle) {
		this.poolMaxIdle = poolMaxIdle;
	}

	public int getPoolMaxTotal() {
		return poolMaxTotal;
	}

	public void setPoolMaxTotal(int poolMaxTotal) {
		this.poolMaxTotal = poolMaxTotal;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
