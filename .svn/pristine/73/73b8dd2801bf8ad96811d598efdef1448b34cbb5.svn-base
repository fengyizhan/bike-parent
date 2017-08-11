package com.tiamaes.bike.connector.protocol.handler;

import javax.annotation.Resource;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.RedisKey;

@Component
public class SerialRepository {
	
	@Resource(name = "stringRedisTemplate")
	private ValueOperations<String, Long> longOperator;
	
	public int generateSerialNo(String id) {
		Assert.notNull(id, "'id' must not be null!");
		
		String key = String.format(RedisKey.SERIALNO_SIMNO, id);
		Long serialNo = longOperator.increment(key, 1);
		if (serialNo >= Short.MAX_VALUE) {
			longOperator.increment(key, Math.negateExact(serialNo) + 1);
			return 1;
		}
		return serialNo.intValue();
	}
}
