package com.tiamaes.bike.storage;

import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.tiamaes.bike.common.RedisKey;



@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaDemoTest {
	private static Logger logger = LogManager.getLogger(KafkaDemoTest.class);
	
	@Autowired
	@Qualifier("kafkaTemplate")
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	@Qualifier("longRedisTemplate")
	private RedisTemplate<String, Long> longRedisTemplate;
	
	@Before
	public void before() {
		assertNotNull("KafkaTemplate<String, String> not found...", kafkaTemplate);
		assertNotNull("RedisTemplate<String, Long> not found...", longRedisTemplate);
	}
	
	@Test
	public void test2() throws Exception{
		ValueOperations<String, Long> operator = longRedisTemplate.opsForValue();
		logger.debug(operator.get(String.format(RedisKey.SERIALNO_SIMNO, "13783698404")));
		logger.debug(operator.get(String.format(RedisKey.SERIALNO_SIMNO, "13783698404")));
		Long value = operator.increment(String.format(RedisKey.SERIALNO_SIMNO, "13783698404"), 1);
		
		logger.debug(value);
		
		
		operator.set(String.format(RedisKey.SERIALNO_SIMNO, "13783698404"), 1L);
		
		logger.debug(operator.get(String.format(RedisKey.SERIALNO_SIMNO, "13783698404")));
	}

}

