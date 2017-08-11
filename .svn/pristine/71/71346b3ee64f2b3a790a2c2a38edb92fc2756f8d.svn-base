package com.tiamaes.bike.storage;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.bean.system.PointVector;
import com.tiamaes.bike.common.bean.system.PointVector.Info.RunState;
import com.tiamaes.bike.common.bean.connector.VehicleWarningInfo;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.DirectionType;
import com.tiamaes.bike.common.bean.integrated.WarnCode;
import com.tiamaes.bike.storage.service.LocatioInfoServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocatioInfoServiceTest {
	
	private static Logger logger = LogManager.getLogger(LocatioInfoServiceTest.class);
	
	@Resource
	private LocatioInfoServiceInterface locatioInfoService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void before() {
		assertNotNull("KafkaTemplate not found...");
	}
	
	@Test
	public void saveVehicleWarningInfoTest() throws Exception{
		VehicleWarningInfo vehicleWarningInfo = new VehicleWarningInfo(); 
		Vehicle vehicle = new Vehicle();
		vehicle.setId("1000003");
		vehicle.setName("TM0001");
		
		vehicleWarningInfo.setVehicle(vehicle);
		vehicleWarningInfo.setWarnCode(WarnCode.ILLEGALMOVEMENT);
		vehicleWarningInfo.setWarnContent(WarnCode.ILLEGALMOVEMENT.getName());
		vehicleWarningInfo.setDirectionType(DirectionType.CHANGE);
		vehicleWarningInfo.setLat(34.838606);
		vehicleWarningInfo.setLng(113.51589);
		
		locatioInfoService.saveVehicleWarningInfo(vehicleWarningInfo);
		
		logger.debug("保存车辆定位信息" + objectMapper.writeValueAsString(vehicleWarningInfo));
	}
	
	@Test
	@Ignore
	public void locationInfoServiceKafkaTest() throws Exception {
		for (int val = 100; val <= 100; val++) {
			Vehicle vehicle = new Vehicle();
			vehicle.setId("1000"+(val+2));
			vehicle.setName("TM0"+val);
			PointVector.Center center = new PointVector.Center();
			PointVector.Info info = new PointVector.Info();
			setVehicle(vehicle, center, info);
			
			locatioInfoService.savePointVector(vehicle);
		}
	}

	private Vehicle setVehicle(Vehicle vehicle, PointVector.Center center, PointVector.Info info) {
		double lng = Double.valueOf("113.648"+String.valueOf((int)((Math.random()*9+1)*100000)));
		center.setLng(lng);
		double lat = Double.valueOf("34.746"+String.valueOf((int)((Math.random()*9+1)*100000)));
		center.setLat(lat);
		vehicle.setCenter(center);
		
		info.setDate(new Date());
		info.setSpeed(0);
		info.setElectricity((int)(Math.random()*100+1));
		info.setLocationState(true);
		info.setLngState(true);
		info.setLatState(true);
		int val = new Random().nextInt(4);
		if (val == 0) {
			info.setRunState(RunState.CANBORROW);
		} else if (val == 1) {
			info.setRunState(RunState.RESERVED);
		} else if (val == 2) {
			info.setRunState(RunState.BORROWED);
		} else {
			info.setRunState(RunState.CANNOTBORROW);
		}
		vehicle.setInfo(info);
		return vehicle;
	}
}
