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
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.integrated.PileRecord;
import com.tiamaes.bike.storage.service.ParkWarningInfoServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkWarningInfoServiceTest {
	
	private static Logger logger = LogManager.getLogger(ParkWarningInfoServiceTest.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void before() {
		assertNotNull("KafkaTemplate not found...");
	}
	
	@Resource
	private ParkWarningInfoServiceInterface service;
	
	@Test
	public void addParkWarningInfoTest() throws Exception{
		PileRecord pileRecord = new PileRecord();
		Park park = new Park();
		park.setId("10002");
		park.setName("莲花街红松路");
		park.setLng(113.50028499146389);
		park.setLat(34.834031126171375);
		pileRecord.setPark(park);
		service.addParkWarningInfo(pileRecord);
		if (logger.isDebugEnabled()) {
			logger.debug("保存停放区报警信息" + objectMapper.writeValueAsString(pileRecord));
		}
	}

}
