package com.tiamaes.bike.api;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
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
import com.tiamaes.bike.api.information.vehicle.service.VehicleServiceInterface;
import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.information.Vehicle.Factory;
import com.tiamaes.bike.common.bean.information.Vehicle.State;
import com.tiamaes.bike.common.bean.information.Vehicle.Type;
import com.tiamaes.bike.common.bean.system.PointVector.Info;
import com.tiamaes.bike.common.bean.system.PointVector.Info.RunState;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.core.userdetails.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleServiceTest {
	private static Logger logger = LogManager.getLogger(VehicleServiceTest.class);
	
	private String id = "1000000";

	@Autowired
	private VehicleServiceInterface service;
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}

	@Test
	public void test001AddVehicle() throws Exception {
		Vehicle vehicle = new Vehicle();
		vehicle.setId(id);
		vehicle.setState(State.NORMAL);
		Type type = new Type();
		type.setId("TM-00-00");
		vehicle.setType(type);
		Factory factory = new Factory();
		factory.setId("00001");
		vehicle.setFactory(factory);
		Info info = new Info();
		info.setRunState(RunState.CANBORROW);
		vehicle.setCreateDate(new Date());
		vehicle.setRemark("备注");
		vehicle.setInfo(info);
		vehicle.setRegistered(true);
		vehicle.setSimNo("8317749");
		Park park = new Park();
		park.setId("10000");
		vehicle.setPark(park);
		service.addVehicle(vehicle);
		Vehicle actual = service.getVehicleById(id);
		Assert.assertNotNull(actual);
		Assert.assertEquals(id, actual.getId());
		logger.debug(jacksonObjectMapper.writeValueAsString(actual));
	}
	
	@Test
	public void test002GetListOfVehicles() throws Exception {
		Vehicle vehicle = new Vehicle();
		Parameters<Vehicle> parameters = new Parameters<>();
		User user = AuthorityServiceTest.setUser();
		parameters.setUser(user);
		parameters.setModel(vehicle);
		Pagination<Vehicle> pagination = new Pagination<>(1, 20);
		List<Vehicle> vehicles = service.getListOfVehicles(parameters, pagination);
		Assert.assertNotNull(vehicles);
		logger.debug(jacksonObjectMapper.writeValueAsString(vehicles));
	}

	@Test
	public void test003UpdateVehicle() throws Exception {
		String name = "豫A45678";
		Vehicle vehicle = service.getVehicleById(id);
		vehicle.setName(name);
		service.updateVehicle(vehicle);
		Vehicle result = service.getVehicleById(id);
		Assert.assertEquals(name, result.getName());
	}
	
	@Test
	public void test004GetVehicleBySimNoTest() throws Exception {
		Vehicle expected = service.getVehicleBySimNo("8317749");
		Assert.assertEquals(id, expected.getId());
	}
	
	@Test
	public void test005GetAllVehiclesWithPlateNo() throws Exception {
		List<Vehicle> vehicles = service.getAllVehiclesWithPlateNo("豫A");
		Assert.assertNotNull(vehicles);
		logger.debug(jacksonObjectMapper.writeValueAsString(vehicles));
	}

	@Test
	public void test006DeleteVehicle() throws Exception {
		Vehicle vehicle = service.getVehicleById(id);
		service.deleteVehicle(vehicle);
		Vehicle result = service.getVehicleById(id);
		Assert.assertNull(result);
	}
	
	@Test
	public void test007GetRegisteredCount() throws Exception{
		int count = service.getRegisteredCount();
		logger.debug(jacksonObjectMapper.writeValueAsString(count));
	}
	
	@Test
	public void test007GetBikePutInTime() throws Exception{
		String putInTime = service.getBikePutInTime(1000003);
		logger.debug(jacksonObjectMapper.writeValueAsString(putInTime));
	}
}