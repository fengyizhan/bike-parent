package com.tiamaes.bike.storage;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.bean.connector.ParkStatusInfo;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.storage.service.ParkStatusInfoServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkStatusInfoServiceTest {
	
	private static Logger logger = LogManager.getLogger(ParkStatusInfoServiceTest.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void before() {
		assertNotNull("KafkaTemplate not found...");
	}
	
	@Resource
	private ParkStatusInfoServiceInterface service;
	
	@Test
	public void addParkStatusInfoTest() throws Exception{
		ParkStatusInfo parkStatusInfo = new ParkStatusInfo();
		Park park = new Park();
		park.setId("10002");
		park.setName("莲花街红松路");
		park.setVehicles(200);
		parkStatusInfo.setPark(park);
		service.addParkStatusInfo(parkStatusInfo);
		if (logger.isDebugEnabled()) {
			logger.debug("保存停放区状态信息" + objectMapper.writeValueAsString(parkStatusInfo));
		}
	}

}
