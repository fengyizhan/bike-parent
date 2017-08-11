package com.tiamaes.bike.api;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.tiamaes.bike.api.information.rate.service.RateServiceInterface;
import com.tiamaes.bike.common.bean.information.Rate;
import com.tiamaes.mybatis.Pagination;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RateServiceTest {
	
	private static Logger logger = LogManager.getLogger(RateServiceTest.class);
	
	private String id = "60590b96c2214b1a9ae57c2c25de1027";
	
	@Autowired
	private RateServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;
	
	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}
	
	@Test
	public void test001AddRate() throws Exception {
		Rate rate = new Rate();
		rate.setId(id);
		rate.setStagePrice(1.0F);
		rate.setStageTime(1.0F);
		rate.setStageLevel(1);
		rate.setCreateTime(new Date());
		service.addRate(rate);
		Rate result = service.getRateById(id);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test002GetListOfRates() throws Exception {
		Rate rate = new Rate();
		Pagination<Rate> pagination = new Pagination<>(1, 20);
		List<Rate> rates = service.getListOfRates(rate, pagination);
		Assert.assertNotNull(rates);
		logger.debug(jacksonObjectMapper.writeValueAsString(rates));
	}
	
	@Test
	public void test003GetListOfRateLevels() throws Exception {
		List<Integer> rateLevels = service.getListOfRateLevels();
		Assert.assertNotNull(rateLevels);
		logger.debug(jacksonObjectMapper.writeValueAsString(rateLevels));
	}
	
	@Test
	public void test004UpdateRate() throws Exception {
		Rate rate = service.getRateById(id);
		rate.setStageTime(1.5F);
		service.updateRate(rate);
		Rate result = service.getRateById(id);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test005Checklevel() throws Exception {
		Optional<String> optional = Optional.of(id);
		Boolean result = service.checkRateLevel(optional, 1);
		Assert.assertFalse(result);
	}
	
	@Test
	public void test006DeleteRate() throws Exception {
		Rate rate = service.getRateById(id);
		service.deleteRate(rate);
		Rate result = service.getRateById(id);
		Assert.assertNull(result);
	}

}
