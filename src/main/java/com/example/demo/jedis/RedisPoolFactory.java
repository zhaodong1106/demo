package com.example.demo.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisPoolFactory {

	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private int port;
	@Value("${redis.poolMaxIdle}")
	private int poolMaxIdle;
	@Value("${redis.poolMaxTotal}")
	private int maxTotal;
	@Value("${redis.timeout}")
	private int timeout;

	
	@Bean
	public JedisPool JedisPoolFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(poolMaxIdle);
		poolConfig.setMaxTotal(maxTotal);
		JedisPool jp = new JedisPool(poolConfig,host, port,
				timeout*1000);
		return jp;
	}
	
}
