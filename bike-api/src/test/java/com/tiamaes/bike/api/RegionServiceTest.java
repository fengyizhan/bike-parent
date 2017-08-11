package com.tiamaes.bike.api;

import static org.junit.Assert.assertNotNull;

import java.util.List;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.api.information.region.service.RegionServiceInterface;
import com.tiamaes.bike.common.bean.information.Region;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegionServiceTest {
	
	private static Logger logger = LogManager.getLogger(RegionServiceTest.class);
	
	@Autowired
	private RegionServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}
	
	@Test
	public void test001getRegionsByParentId() throws Exception {
		List<Region> regions = service.getRegionsByParentId("410000");
		logger.debug(jacksonObjectMapper.writeValueAsString(regions));
	}
	
	@Test
	public void test002getRegionsByType() throws Exception {
		List<Region> regions = service.getRegionsByType(2);
		logger.debug(jacksonObjectMapper.writeValueAsString(regions));
	}

}
