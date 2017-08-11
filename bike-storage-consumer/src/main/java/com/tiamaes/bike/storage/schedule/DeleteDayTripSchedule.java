package com.tiamaes.bike.storage.schedule;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.common.RedisKey;

@Component
public class DeleteDayTripSchedule {
	
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
//	@Scheduled(cron="0 0 0 * * ?")
//    public void cronJob(){
//		stringRedisTemplate.delete(RedisKey.VEHICLES_TRIPS);
//    }

}
