package com.tiamaes.bike.storage.service;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.information.Park;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ParkService implements ParkServiceInterface {

	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, Park> parkOperator;
	
	@Override
	public Park getParkById(String id) {
		Assert.notNull(id, "停放区id不能为空!");
		Park park = parkOperator.get(RedisKey.PARKS, id);
		return park;
	}
}
