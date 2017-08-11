package com.tiamaes.bike.api;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.api.information.driver.service.DriverServiceInterface;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.bike.common.bean.system.User.State;
import com.tiamaes.mybatis.Pagination;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DriverServiceTest {
	private static Logger logger = LogManager.getLogger(DriverServiceTest.class);
	
	private String username = "18538317749";

	@Autowired
	private DriverServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}

	@Test
	public void test001AddDriverTest() throws Exception {
		User user = new User();
		user.setUsername(username);
		user.setIdentityCard("41010619940929005X");
		user.setState(State.WHITE);
		user.setCreateDate(new Date());
		service.addDriver(user);
		User result = service.getDriverByUsername(username);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test002GetListOfDriversTest() throws Exception {
		User user = new User();
		Pagination<User> pagination = new Pagination<>(1 ,20);
		List<User> users = service.getListOfDrivers(user, pagination);
		Assert.assertNotNull(users);
		logger.debug(jacksonObjectMapper.writeValueAsString(users));
	}
	
	@Test
	public void test003UpdateDriverNicknameTest() throws Exception {
		User user = new User();
		user.setUsername(username);
		user.setNickname("啊哈哈哈");
		service.updateDriverNickname(user);
		User result = service.getDriverByUsername(username);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test004UpdateDriverStateTest() throws Exception {
		service.updateDriverState(username);
		User result = service.getDriverByUsername(username);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test005CheckIdentityCard() throws Exception {
		String identityCard = "41010619940929005X";
		boolean result = service.checkIdentityCard(identityCard);
		Assert.assertTrue(result);
	}
	
	@Test
	public void test006DeleteDriverTest() throws Exception {
		User user = service.getDriverByUsername(username);
		service.deleteDriver(user);
		User result = service.getDriverByUsername(username);
		Assert.assertNull(result);
	}
}