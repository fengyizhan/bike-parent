package com.tiamaes.bike.authority;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.api.authority.user.service.UserServiceInterface;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.security.provisioning.MutableUser;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
	private static Logger logger = LogManager.getLogger(UserTest.class);
	

	@Autowired
	private UserServiceInterface userService;
	@Resource
	private PasswordEncoder passwordEncoder;
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;
	@Resource
	private RedisTemplate<String,String> redisTemplate;
	@Before
	public void before() {
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}

	
	@Test
	public void changepassword() throws Exception {
		User user=new User();
		String mobile="15238006102";
		String password="123456";
		user.setUsername(mobile);
		user.setPassword(passwordEncoder.encode(password));
		MutableUser p_user=new MutableUser(user);
		userService.upadatePassword(p_user);
		//存储到session中以便登陆验证比对，5分钟失效
		String key=String.format(RedisKey.MESSAGE_VERIFYCODE,mobile);
		redisTemplate.opsForValue().set(key, password,100,TimeUnit.MINUTES);
	}


	
}