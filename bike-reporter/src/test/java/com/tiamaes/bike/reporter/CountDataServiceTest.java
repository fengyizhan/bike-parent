package com.tiamaes.bike.reporter;

import static org.junit.Assert.assertNotNull;

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
import com.tiamaes.bike.reporter.information.countdatalog.bean.CountDataLog;
import com.tiamaes.bike.reporter.information.countdatalog.service.CountDataLogServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountDataServiceTest {

	private static Logger logger = LogManager.getLogger(CountDataServiceTest.class);
	
	@Autowired
	private CountDataLogServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}
	
	@Test
	public void test001GetDataOfCountDataLog() throws Exception {
		CountDataLog countDataLog = service.getDataOfCountDataLog();
		Assert.assertNotNull(countDataLog);
		logger.debug(jacksonObjectMapper.writeValueAsString(countDataLog));
	}
}
