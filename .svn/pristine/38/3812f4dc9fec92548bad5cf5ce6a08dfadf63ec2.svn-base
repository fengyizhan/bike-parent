package com.tiamaes.bike.api.information.park.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.api.information.park.persistence.ParkMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.system.PointVector.Center;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

/**
 * @author kangty
 * 集中停放区管理接口实现类
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ParkService implements ParkServiceInterface {
	
	private static final int IDLECOUNT = 300;
	
	@Resource
	private ParkMapper parkMapper;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, Park> operator;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Park getParkById(String id) {
		Assert.notNull(id, "集中停放区编号不能为空!");
		Park park = operator.get(RedisKey.PARKS, String.valueOf(id)); 
		if (park == null) {
			park = parkMapper.getParkById(id);
			if (park != null) {
				operator.putIfAbsent(RedisKey.PARKS, String.valueOf(park.getId()), park);
			}
		}
		return park;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Park> getListOfParks(Park park, Pagination<Park> pagination) {
		Assert.notNull(pagination, "分页对象不能为空");
		PageHelper.startPage(pagination);
		return parkMapper.getListOfParks(park);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Park addPark(Park park) {
		Assert.notNull(park);
		park.setAuthentication(UUID.randomUUID().toString());
		Assert.notNull(park.getName(), "集中停放区名称不能为空");
		Assert.isTrue(!parkMapper.hasExists(null, park.getName()), "集中停放区名称已经存在");
		parkMapper.addPark(park);
		Park result = parkMapper.getParkById(park.getId());
		if (result != null) {
			operator.putIfAbsent(RedisKey.PARKS, String.valueOf(park.getId()), result);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Park updatePark(Park park) {
		Assert.notNull(park);
		Assert.notNull(park.getName(), "集中停放区名称不能为空");
		Assert.isTrue(!parkMapper.hasExists(park.getId(), park.getName()), "集中停放区名称已经存在");
		parkMapper.updatePark(park);
		Park result = parkMapper.getParkById(park.getId());
		if (result != null) {
			operator.put(RedisKey.PARKS, String.valueOf(park.getId()), park);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletePark(Park park) {
		Assert.notNull(park, "集中停放区信息不能为空!");
		parkMapper.deletePark(park);
		Park result = parkMapper.getParkById(park.getId());
		if (result == null) {
			operator.delete(RedisKey.PARKS, String.valueOf(park.getId()));
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean checkParkName(Optional<String> id, String name) {
		return parkMapper.hasExists(id.isPresent() ? id.get() : null, name);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getId() {
		return parkMapper.getId();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Park> getAllParks() {
		return parkMapper.getAllParks();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Park> getAroundParks(Center center) {
		List<Park> parks = parkMapper.getAroundParks(center);
		for (Park park:parks) {
			park.setIdle(IDLECOUNT - park.getVehicles());
		}
		return parkMapper.getAroundParks(center);
	}

}
