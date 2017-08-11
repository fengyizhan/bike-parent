package com.tiamaes.bike.wallet;

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
import com.tiamaes.bike.common.bean.wallet.Consume;
import com.tiamaes.bike.wallet.information.consume.service.ConsumeServiceInterface;
import com.tiamaes.mybatis.Pagination;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConsumeServiceTest {
	private static Logger logger = LogManager.getLogger(ConsumeServiceTest.class);
	
	private String username = "18538317749";
	
	private Integer id = 10000;

	@Autowired
	private ConsumeServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}

	@Test
	public void test001AddConsumeTest() throws Exception {
		Consume consume = new Consume();
		consume.setId(id);
		consume.setUsername(username);
		consume.setMoney(300.0F);
		consume.setCreateTime(new Date());
		service.addConsume(consume);
		Consume result = service.getConsumeById(id);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test002GetListOfConsumesByUsername() throws Exception {
		Consume consume = new Consume();
		consume.setUsername(username);
		Pagination<Consume> pagination = new Pagination<>(1, 10);
		List<Consume> consumes = service.getListOfConsumesByUsername(consume, pagination);
		Assert.assertNotNull(consumes);
		logger.debug(jacksonObjectMapper.writeValueAsString(consumes));
	}
	
	@Test
	public void test003UpdateConsumeTest() throws Exception {
		Consume consume = new Consume();
		consume.setUsername(username);
		consume.setId(id);
		consume.setUsername(username);
		consume.setMoney(500.0F);
		service.updateConsume(consume);
		Consume result = service.getConsumeById(id);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
}