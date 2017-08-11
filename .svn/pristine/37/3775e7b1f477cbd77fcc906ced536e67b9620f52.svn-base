package com.tiamaes.bike.api.information.station.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.api.information.paths.persistence.PathsMapper;
import com.tiamaes.bike.api.information.station.persistence.StationMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.information.Pathpoint;
import com.tiamaes.bike.common.bean.information.Station;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

/**
 * @author kangty
 * 电子围栏信息管理接口实现类
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class StationService implements StationServiceInterface {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(StationService.class);
	
	@Resource
	private ObjectMapper objectMapper;
	@Resource
	private PathsMapper pathsMapper;
	@Resource
	private StationMapper stationMapper;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, Station> operator;
	
//	@Resource
//	private CommandServiceInterface commandService;
//	@Resource
//	private VehicleServiceInterface vehicleService;
//	@Autowired
//	@Qualifier("kafkaStringTemplate")
//	private KafkaTemplate<String, String> kafkaStringTemplate;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Station getStationById(int id) {
		Assert.notNull(id, "电子围栏编号不能为空!");
		Station station = operator.get(RedisKey.STATIONS, String.valueOf(id));
		if (station == null) {
			station = stationMapper.getStationById(id);
			if (station != null) {
				operator.putIfAbsent(RedisKey.STATIONS, String.valueOf(station.getId()), station);
			}
		}
		return station;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Station> getListOfStations(Station station, Pagination<Station> pagination) {
		Assert.notNull(pagination, "分页对象不能为空");
		PageHelper.startPage(pagination);
		return stationMapper.getListOfStations(station);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Station addStation(Station station) {
		Assert.notNull(station);
		Assert.notNull(station.getName(), "电子围栏名称不能为空");
		Assert.isTrue(!stationMapper.hasExists(null, station.getName()), "电子围栏名称已经存在");
		stationMapper.addStation(station);
		List<Pathpoint> pathpoints = station.getPaths();
		for (Pathpoint pp : pathpoints) {
			pp.setTargetid(station.getId());
		}
		pathsMapper.addPaths(pathpoints);
		Station result = stationMapper.getStationById(station.getId());
		if (result != null) {
			operator.putIfAbsent(RedisKey.STATIONS, String.valueOf(station.getId()), result);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Station updateStation(Station station) {
		Assert.notNull(station);
		Assert.notNull(station.getName(), "电子围栏名称不能为空");
		Assert.isTrue(!stationMapper.hasExists(station.getId(), station.getName()), "电子围栏名称已经存在");
		stationMapper.updateStation(station);
		pathsMapper.deletePaths(station.getId());
		List<Pathpoint> pathpoints = station.getPaths();
		for (Pathpoint pp : pathpoints) {
			pp.setTargetid(station.getId());
		}
		pathsMapper.addPaths(pathpoints);
		Station result = stationMapper.getStationById(station.getId());
		if (result != null) {
			operator.put(RedisKey.STATIONS, String.valueOf(station.getId()), station);
		}
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteStation(Station station) {
		Assert.notNull(station, "电子围栏信息不能为空!");
		stationMapper.deleteStation(station);
		pathsMapper.deletePaths(station.getId());
		Station result = stationMapper.getStationById(station.getId());
		if (result == null) {
			operator.delete(RedisKey.STATIONS, String.valueOf(station.getId()));
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean checkStationName(Optional<Integer> id, String name) {
		return stationMapper.hasExists(id.isPresent() ? id.get() : null, name);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getId() {
		return stationMapper.getId();
	}

}
