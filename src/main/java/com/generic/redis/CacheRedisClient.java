package com.generic.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class CacheRedisClient extends RedisClient {

	public CacheRedisClient(GenericObjectPoolConfig poolConfig, String host, int port) {
		super(poolConfig, host, port);
	}
	
}
