package com.tiamaes.bike.storage.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.system.PointVector.Info;
import com.tiamaes.bike.common.bean.system.PointVector.Info.RunState;
import com.tiamaes.bike.common.bean.wallet.Consume;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.bike.common.bean.information.Station;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.ParkVehicleUseRecord;
import com.tiamaes.bike.common.bean.integrated.UseType;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.storage.persistence.VehicleUseInfoMapper;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class VehicleUseInfoService implements VehicleUseInfoServiceInterface {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(VehicleUseInfoService.class);
	
	@Resource
	private VehicleServiceInterface vehicleService;
	@Resource
	private ApiFeignSerivceInterface apiFeignSerivce;
	@Resource
	private WalletFeignSerivceInterface walletFeignService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private VehicleUseInfoMapper vehicleUseInfoMapper;
	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> stringOperator;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, Station> stationOperator;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, User> userOperator;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String addVehicleUseInfo(ParkVehicleUseRecord parkVehicleUseRecord) throws Exception {
		Assert.notNull(parkVehicleUseRecord, "停车区车辆使用记录不能为空");
		Assert.notNull(parkVehicleUseRecord.getVehicle(), "车辆信息不能为空");
		String vehicleId = parkVehicleUseRecord.getVehicle().getId();
		Vehicle target = vehicleService.getVehicleById(vehicleId);
		parkVehicleUseRecord.setVehicle(target);
		String id = null;
		if (UseType.BORROW.equals(parkVehicleUseRecord.getUseType())) {
			id = UUIDGenerator.getUUID();
			stringOperator.put(RedisKey.VEHICLE_BORROWVEHICLE_ID, vehicleId, id);
			Info info = target.getInfo();
			info.setRunState(RunState.BORROWED);
			target.setInfo(info);
			target.setPark(null);
			apiFeignSerivce.update(target, vehicleId);
		} else if (UseType.RETURN.equals(parkVehicleUseRecord.getUseType())) {
			id = (String) stringOperator.get(RedisKey.VEHICLE_BORROWVEHICLE_ID, vehicleId);
			if (StringUtils.isBlank(id)) {
				id = UUIDGenerator.getUUID();
			}
			stringOperator.delete(RedisKey.VEHICLE_BORROWVEHICLE_ID, vehicleId);
			String distence = stringOperator.get(RedisKey.VEHICLE_KM, vehicleId);
			parkVehicleUseRecord.setKilometers(StringUtils.isNotBlank(distence) ? Double.valueOf(distence) : 0.0);
			parkVehicleUseRecord.setCost(1.0);
			stringOperator.delete(RedisKey.VEHICLE_KM, vehicleId);
			Info info = target.getInfo();
			info.setRunState(RunState.CANBORROW);
			target.setInfo(info);
			apiFeignSerivce.update(target, vehicleId);
			/* 车辆当天趟次自增1 */
			String dayTripCount = (String) stringRedisTemplate.opsForHash().get(RedisKey.VEHICLES_TRIPS, vehicleId);
			if (StringUtils.isBlank(dayTripCount)) {
				dayTripCount = "0";
			}
			stringRedisTemplate.opsForHash().put(RedisKey.VEHICLES_TRIPS, vehicleId, String.valueOf(Integer.valueOf(dayTripCount)+1));
		}
		User user = userOperator.get(RedisKey.VEHICLES_DRIVERS_VEHICLEID, vehicleId);
		parkVehicleUseRecord.setUser(user);
		parkVehicleUseRecord.setId(id);
		Date now = new Date();
		parkVehicleUseRecord.setCreateDate(now);
		vehicleUseInfoMapper.addVehicleUseInfo(parkVehicleUseRecord);
		Consume consume = new Consume();
		consume.setMoney((float) 1.0);
		consume.setCreateTime(now);
		consume.setUsername(user.getUsername());
		walletFeignService.updateWalletUserByPayConsume(consume);
		return id;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteVehicleUseInfo(String id) {
		vehicleUseInfoMapper.deleteVehicleUseInfo(id);
	}
}
