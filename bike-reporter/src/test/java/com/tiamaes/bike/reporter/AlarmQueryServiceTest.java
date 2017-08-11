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
import com.tiamaes.bike.common.bean.integrated.AlarmRecord;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.reporter.integrated.alarm.service.AlarmQueryServiceInterface;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.core.DefaultGrantedAuthority;
import com.tiamaes.security.core.userdetails.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlarmQueryServiceTest {

	private static Logger logger = LogManager.getLogger(AlarmQueryServiceTest.class);
	
	@Autowired
	private AlarmQueryServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}
	
	@Test
	public void test001GetListOfAlarmRecords() throws Exception {
		AlarmRecord alarmRecord = new AlarmRecord();
		Parameters<AlarmRecord> parameters = new Parameters<>();
		User user = setUser();
//		user.setId("849aa67d60aa438a83e7024f1ec2d93f");
		parameters.setUser(user);
		parameters.setModel(alarmRecord);
		Pagination<AlarmRecord> pagination = new Pagination<>(1, 20);
		List<AlarmRecord> alarmRecords = service.getListOfAlarmRecords(parameters, pagination);
		Assert.assertNotNull(alarmRecords);
		logger.debug(jacksonObjectMapper.writeValueAsString(alarmRecords));
	}
	
//	@Test
//	public void getListOfVehicleAlarmTest() throws Exception {
//		AlarmRecord alarmRecord = new AlarmRecord();
//		Region region = new Region();
//		region.setId("440303");
//		Vehicle.Property property = new Vehicle.Property();
////		property.setRegion(region);
//		Vehicle vehicle = new Vehicle();
//		vehicle.setProperty(property);
//		alarmRecord.setVehicle(vehicle);
//		Date now = new Date();
//		Date before = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(now);
//		calendar.add(Calendar.MONTH, -2);
//		before = calendar.getTime();
//		alarmRecord.setStartTime(before);
//		List<AlarmRecord> alarmRecords = service.getListOfVehicleAlarm(alarmRecord);
//		logger.debug(jacksonObjectMapper.writeValueAsString(alarmRecords));
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
