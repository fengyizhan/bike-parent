package com.tiamaes.bike.storage;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.ParkVehicleUseRecord;
import com.tiamaes.bike.common.bean.integrated.UseType;
import com.tiamaes.bike.storage.service.VehicleUseInfoServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleUseInfoServiceTest {
	
	private static Logger logger = LogManager.getLogger(VehicleUseInfoServiceTest.class);
	
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
	private VehicleUseInfoServiceInterface service;
	
	@Test
	public void addVehicleBorrowInfo() throws Exception {
		ParkVehicleUseRecord parkVehicleUseRecord = new ParkVehicleUseRecord();
		Park park = new Park();
		park.setId("10002");
		park.setName("莲花街红松路");
		Vehicle vehicle = new Vehicle();
		vehicle.setId("1000003");
		vehicle.setName("TM0001");
		vehicle.setSimNo("018538317749");
		parkVehicleUseRecord.setPark(park);
		parkVehicleUseRecord.setVehicle(vehicle);
		parkVehicleUseRecord.setUseType(UseType.BORROW);
		service.addVehicleUseInfo(parkVehicleUseRecord);
		if (logger.isDebugEnabled()) {
			logger.debug("保存停放区车辆借出信息" + objectMapper.writeValueAsString(parkVehicleUseRecord));
		}
	}
	
	@Test
	public void addVehicleReturnInfo() throws Exception {
		ParkVehicleUseRecord parkVehicleUseRecord = new ParkVehicleUseRecord();
		Park park = new Park();
		park.setId("10002");
		park.setName("莲花街红松路");
		Vehicle vehicle = new Vehicle();
		vehicle.setId("1000003");
		vehicle.setName("TM0001");
		vehicle.setSimNo("018538317749");
		parkVehicleUseRecord.setVehicle(vehicle);
		parkVehicleUseRecord.setUseType(UseType.RETURN);
		parkVehicleUseRecord.setPark(park);
		String id = service.addVehicleUseInfo(parkVehicleUseRecord);
		service.deleteVehicleUseInfo(id);
		if (logger.isDebugEnabled()) {
			logger.debug("保存停放区车辆还回信息" + objectMapper.writeValueAsString(parkVehicleUseRecord));
		}
	}

}
