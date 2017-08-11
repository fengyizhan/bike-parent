package com.tiamaes.bike.api;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.messages.SMS;



@RunWith(SpringRunner.class)
@SpringBootTest
public class SMSTest {
//	private static Logger logger = LogManager.getLogger(KafkaDemoTest.class);
	
	@Autowired
	@Qualifier("smsUtil")
	private SMS sms;
	@Resource
	private ObjectMapper jsonObjectMapper;
	@Resource
	private RedisTemplate<String,String> redisTemplate;
	@Before
	public void before() {
	}
	
	@Test
	public void sendVerifyCode() throws Exception {
		String mobile="15238006102";
		//生成随机验证码
//		int number=SystemUtil.numberGenerator(6);
		int number=123456;
		//存储到session中以便登陆验证比对，5分钟失效
		String key=String.format(RedisKey.MESSAGE_VERIFYCODE,mobile);
		redisTemplate.opsForValue().set(key, String.valueOf(number),60,TimeUnit.MINUTES);
		//发送短信验证码
		sms.send(mobile,"（共享单车）验证码："+number+"，60分钟内有效。");
		String verifyCode=redisTemplate.opsForValue().get(key);
		System.out.println(mobile+"收到的验证码为："+verifyCode);
	}

	
}

