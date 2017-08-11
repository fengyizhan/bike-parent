package com.tiamaes.bike.reporter;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.bean.information.Region;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.LocationRecord;
import com.tiamaes.bike.reporter.integrated.vehicle.service.VehicleLocationServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleLocationServiceTest {
	private static Logger logger = LogManager.getLogger(VehicleLocationServiceTest.class);
	
	@Autowired
	private VehicleLocationServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}
	
	@Test
	public void addVehicleLocationTest() throws Exception {
		String id = "0e55cfa80bd9490fb0e2dde10619145a";
		LocationRecord vehicleLocation = new LocationRecord();
		vehicleLocation.setId(id);
		Vehicle vehicle = new  Vehicle();
		vehicle.setId("dd5778703a59436699e6c0050c95af53");
//		vehicle.setCarNo("豫A45678");
//		vehicle.setCompany(company);
//		vehicle.setDepartment(department);
//		vehicle.setSimCode("13845600011");
		vehicleLocation.setVehicle(vehicle);
		vehicleLocation.setCreateDate(new Date());
		vehicleLocation.setLng(113.562319);
		vehicleLocation.setLat(34.813563);
		vehicleLocation.setElevation(10);
		vehicleLocation.setSpeed(50);
		vehicleLocation.setDirection(2);
		
		service.addVehicleLocation(vehicleLocation);
		
		LocationRecord actual = service.getLocationById(id);
		Assert.assertNotNull(actual);
		Assert.assertEquals(id, actual.getId());
		
		logger.debug(jacksonObjectMapper.writeValueAsString(actual));
	}
	
	@Test
	public void getLocationByIdTest() throws Exception {
		String id = "79e904d007e948a68a042266ce528889";
		
		LocationRecord expected = service.getLocationById(id);
		
		Assert.assertEquals(id, expected.getId());
	}
	
	@Test
	public void getListOfHistoryPositionInfoTest() throws Exception {
		LocationRecord locationRecord = new LocationRecord();
		Vehicle vehicle = new Vehicle();
//		vehicle.setCarNo("粤B88888");
		locationRecord.setVehicle(vehicle);
		Date now = new Date();
		Date before = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, -2);
		before = calendar.getTime();
		locationRecord.setStartTime(before);
		List<LocationRecord> locationRecords = service.getListOfHistoryPositionInfo(locationRecord);
		logger.debug(jacksonObjectMapper.writeValueAsString(locationRecords));
	}
	
	@Test
	public void getListOfRealtimePositionInfoTest() throws Exception {
		LocationRecord locationRecord = new LocationRecord();
		Vehicle vehicle = new Vehicle();
//		vehicle.setCarNo("粤B88888");
		Region region = new Region();
		region.setId("440303");
//		property.setRegion(region);
		locationRecord.setVehicle(vehicle);
		List<LocationRecord> locationRecords = service.getListOfRealtimePositionInfo(locationRecord);
		logger.debug(jacksonObjectMapper.writeValueAsString(locationRecords));
	}
	
}
