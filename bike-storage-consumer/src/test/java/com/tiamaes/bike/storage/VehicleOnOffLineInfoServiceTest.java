package com.tiamaes.bike.storage;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.bean.connector.VehicleOnOffLineInfo;
import com.tiamaes.bike.common.bean.connector.VehicleOnOffLineInfo.State;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.storage.service.VehicleOnOffLineInfoServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleOnOffLineInfoServiceTest {
	
	private static Logger logger = LogManager.getLogger(VehicleOnOffLineInfoServiceTest.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	@Qualifier("kafkaTemplate")
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Before
	public void before() {
		assertNotNull("KafkaTemplate not found...");
	}
	
	@Resource
	private VehicleOnOffLineInfoServiceInterface service;
	
	@Test
	public void addVehicleOnLineInfo() throws Exception {
		VehicleOnOffLineInfo vehicleOnOffLineInfo = new VehicleOnOffLineInfo();
		Vehicle vehicle = new Vehicle();
		vehicle.setId("1000003");
		vehicle.setName("TM0001");
		vehicle.setSimNo("018538317749");
		vehicleOnOffLineInfo.setVehicle(vehicle);
		vehicleOnOffLineInfo.setState(State.ONLINE);
		service.addVehicleOnOffLineInfo(vehicleOnOffLineInfo);
		if (logger.isDebugEnabled()) {
			logger.debug("保存车辆上线信息" + objectMapper.writeValueAsString(vehicleOnOffLineInfo));
		}
	}
	
	@Test
	public void addVehicleOffLineInfo() throws Exception {
		VehicleOnOffLineInfo vehicleOnOffLineInfo = new VehicleOnOffLineInfo();
		Vehicle vehicle = new Vehicle();
		vehicle.setId("1000003");
		vehicle.setName("TM0001");
		vehicle.setSimNo("018538317749");
		vehicleOnOffLineInfo.setVehicle(vehicle);
		vehicleOnOffLineInfo.setState(State.OFFLINE);
		String id = service.addVehicleOnOffLineInfo(vehicleOnOffLineInfo);
		service.deleteVehicleOnOffLineInfo(id);
		if (logger.isDebugEnabled()) {
			logger.debug("保存车辆上线信息" + objectMapper.writeValueAsString(vehicleOnOffLineInfo));
		}
	}
	
	@Test
	@Ignore
	public void vehicleOnOffLineInfoKafkaTest() {
		for (int i = 0; i < 56; i++) {
			VehicleOnOffLineInfo vehicleOnOffLineInfo = new VehicleOnOffLineInfo();
			if (i % 2 == 0) {
				vehicleOnOffLineInfo.setState(State.ONLINE);
			} else {
				vehicleOnOffLineInfo.setState(State.OFFLINE);
			}
			Vehicle vehicle = new Vehicle();
			vehicle.setId("d196b09f835842d79c993d961d94849f");
//			vehicle.setCarNo("京Q23456");
//			vehicle.setSimCode("015800020022");
//			vehicle.setCompany(company);
//			vehicle.setDepartment(department);
			User user = new User();
			user.setIdentityCard("4101064484231");
//			user.setName("张三");
//			user.setTelephone("18513974682");
			vehicleOnOffLineInfo.setVehicle(vehicle);
			kafkaTemplate.send(MessageBuilder.withPayload(vehicleOnOffLineInfo).setHeader(KafkaHeaders.TOPIC, vehicleOnOffLineInfo.getClass().getName()).build());
		}
		
	}

}
