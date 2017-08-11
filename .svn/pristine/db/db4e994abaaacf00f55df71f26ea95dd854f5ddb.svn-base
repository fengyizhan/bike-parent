package com.tiamaes.bike.reporter;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
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
import com.tiamaes.bike.common.bean.integrated.BorrowRecord;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.reporter.integrated.borrow.service.BorrowQueryServiceInterface;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.core.DefaultGrantedAuthority;
import com.tiamaes.security.core.userdetails.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BorrowQueryServiceTest {

	private static Logger logger = LogManager.getLogger(BorrowQueryServiceTest.class);
	
	@Autowired
	private BorrowQueryServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}
	
	@Test
	public void test001GetListOfBorrowRecords() throws Exception {
		BorrowRecord borrowRecord = new BorrowRecord();
		Parameters<BorrowRecord> parameters = new Parameters<BorrowRecord>();
		User user = setUser();
//		user.setId("849aa67d60aa438a83e7024f1ec2d93f");
		parameters.setUser(user);
		parameters.setModel(borrowRecord);
		Pagination<BorrowRecord> pagination = new Pagination<BorrowRecord>(1, 20);
		List<BorrowRecord> borrowRecords = service.getListOfBorrowRecords(parameters, pagination);
		Assert.assertNotNull(borrowRecords);
		logger.debug(jacksonObjectMapper.writeValueAsString(borrowRecords));
	}
	
	@Test
	public void test002GetListOfBikeBorrowRecords() throws Exception {
		List<BorrowRecord> borrowRecords = service.getListOfBikeBorrowRecord(1000003);
		Assert.assertNotNull(borrowRecords);
		logger.debug(jacksonObjectMapper.writeValueAsString(borrowRecords));
	}
	
//	@Test
//	public void getListOfVehicleBorrowTest() throws Exception {
//		BorrowRecord borrowRecord = new BorrowRecord();
//		Region region = new Region();
//		region.setId("440303");
//		Vehicle.Property property = new Vehicle.Property();
////		property.setRegion(region);
//		Vehicle vehicle = new Vehicle();
//		vehicle.setProperty(property);
//		borrowRecord.setVehicle(vehicle);
//		Date now = new Date();
//		Date before = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(now);
//		calendar.add(Calendar.MONTH, -2);
//		before = calendar.getTime();
//		borrowRecord.setStartTime(before);
//		List<BorrowRecord> borrowRecords = service.getListOfVehicleBorrow(borrowRecord);
//		logger.debug(jacksonObjectMapper.writeValueAsString(borrowRecords));
//	}
	
	public static void main(String[] args) {
		System.out.println(UUIDGenerator.getUUID());
	}
	
	
	public static User setUser() {
		List<DefaultGrantedAuthority> authorites = new ArrayList<DefaultGrantedAuthority>();
		DefaultGrantedAuthority grantedAuthority = new DefaultGrantedAuthority("ROLE_DEVELOPER", "ROLE_DEVELOPER");
		authorites.add(grantedAuthority);
		User user = new User("18538317749", "23731d539a1ae3271657ed50820d68d49a130d35851e14733fda7155342308522bb9b3c480c54cc8",
				authorites);
		return user;
	}
}
