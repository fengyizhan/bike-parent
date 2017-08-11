package com.tiamaes.bike.api;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.tiamaes.bike.api.information.station.service.StationServiceInterface;
import com.tiamaes.bike.common.bean.information.Pathpoint;
import com.tiamaes.bike.common.bean.information.Region;
import com.tiamaes.bike.common.bean.information.Station;
import com.tiamaes.mybatis.Pagination;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StationServiceTest {
	
	private static Logger logger = LogManager.getLogger(StationServiceTest.class);
	
	private Integer id = 10000;
	
	@Autowired
	private StationServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}
	
	@Test
	public void test001AddStation() throws Exception {
		Station station = new Station();
		station.setId(id);
		station.setName("丰庆路电子围栏");
		Region region = new Region();
		region.setId("410000");
		station.setRegion(region);
		List<Pathpoint> pathpoints = new ArrayList<Pathpoint>();
		Pathpoint pp1 = new Pathpoint();
		pp1.setLng(113.663221);
		pp1.setLat(34.7568711);
		pathpoints.add(pp1);
		Pathpoint pp2 = new Pathpoint();
		pp2.setLng(112.663221);
		pp2.setLat(34.7568711);
		pathpoints.add(pp2);
		Pathpoint pp3 = new Pathpoint();
		pp3.setLng(113.663221);
		pp3.setLat(33.7568711);
		pathpoints.add(pp3);
		Pathpoint pp4 = new Pathpoint();
		pp4.setLng(112.663221);
		pp4.setLat(33.7568711);
		pathpoints.add(pp4);
		station.setPaths(pathpoints);
		station.setCreateDate(new Date());
		
		station = service.addStation(station);
		
		Station result = service.getStationById(station.getId());
		
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test002GetListOfStation() throws Exception {
		Station station = new Station();
		Pagination<Station> pagination = new Pagination<Station>(1, 20);
		List<Station> stationList = service.getListOfStations(station, pagination);
		Assert.assertNotNull(stationList);
		logger.debug(jacksonObjectMapper.writeValueAsString(stationList));
	}
	
	@Test
	public void test003UpdateStation() throws Exception {
		Station station = service.getStationById(id);
		String name = "郑州市电子围栏";
		station.setName(name);
		List<Pathpoint> pathpoints = new ArrayList<Pathpoint>();
		Pathpoint pp1 = new Pathpoint();
		pp1.setLng(113.663221);
		pp1.setLat(34.7568711);
		pathpoints.add(pp1);
		Pathpoint pp2 = new Pathpoint();
		pp2.setLng(112.663221);
		pp2.setLat(34.7568711);
		pathpoints.add(pp2);
		Pathpoint pp3 = new Pathpoint();
		pp3.setLng(113.663221);
		pp3.setLat(33.7568711);
		pathpoints.add(pp3);
		Pathpoint pp4 = new Pathpoint();
		pp4.setLng(112.663221);
		pp4.setLat(33.7568711);
		pathpoints.add(pp4);
		station.setPaths(pathpoints);
		service.updateStation(station);
		
		Station result = service.getStationById(id);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test004Checkname() throws Exception {
//		Optional<Long> optional = Optional.ofNullable(null);
		Optional<Integer> optional = Optional.of(id);
		Boolean result = service.checkStationName(optional, "郑州市电子围栏");
//		Assert.assertTrue(result);
		Assert.assertFalse(result);
	}
	
	@Test
	public void test005DeleteStation() throws Exception {
		Station result = service.getStationById(id);
		service.deleteStation(result);
		result = service.getStationById(id);
		Assert.assertNull(result);
	}
	
	@Test
	public void test006GetId() throws Exception {
		logger.debug(jacksonObjectMapper.writeValueAsString(service.getId()));
	}

}
