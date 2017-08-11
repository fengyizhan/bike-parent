package com.tiamaes.bike;

import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import com.tiamaes.bike.common.bean.connector.ParkStatusInfo;
import com.tiamaes.bike.common.bean.information.Park;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaDemoTest {
	private static Logger logger = LogManager.getLogger(KafkaDemoTest.class);
	
	@Autowired
	@Qualifier("kafkaTemplate")
	private KafkaTemplate<String, String> kafkaTemplate;

	@Before
	public void before() {
		assertNotNull("KafkaTemplate<String, String> not found...", kafkaTemplate);
	}
	
	@Test
	public void test() throws Exception {
		ParkStatusInfo parkStatusInfo = new ParkStatusInfo();
		parkStatusInfo.setVehicles(200);
		Park park = new Park();
		park.setId("10006");
		parkStatusInfo.setPark(park);
		kafkaTemplate.send(MessageBuilder.withPayload(parkStatusInfo).setHeader(KafkaHeaders.TOPIC, parkStatusInfo.getClass().getName()).build());
	}

}

