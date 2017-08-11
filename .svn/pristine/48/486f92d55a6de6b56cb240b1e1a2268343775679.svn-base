package com.tiamaes.bike.reporter;

import static org.junit.Assert.assertNotNull;

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
import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.integrated.PileRecord;
import com.tiamaes.bike.reporter.integrated.pile.service.PileQueryServiceInterface;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.core.userdetails.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PileQueryServiceTest {

	private static Logger logger = LogManager.getLogger(PileQueryServiceTest.class);
	
	@Autowired
	private PileQueryServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}
	
	@Test
	public void test001GetListOfPileRecords() throws Exception {
		PileRecord pileRecord = new PileRecord();
		Parameters<PileRecord> parameters = new Parameters<PileRecord>();
		User user = AlarmQueryServiceTest.setUser();
		parameters.setUser(user);
		parameters.setModel(pileRecord);
		Pagination<PileRecord> pagination = new Pagination<PileRecord>(1, 20);
		List<PileRecord> pileRecords = service.getListOfPileRecords(parameters, pagination);
		Assert.assertNotNull(pileRecords);
		logger.debug(jacksonObjectMapper.writeValueAsString(pileRecords));
	}
}
