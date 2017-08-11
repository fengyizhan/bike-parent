package com.tiamaes.bike.storage.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.system.PointVector;
import com.tiamaes.bike.common.bean.connector.VehicleWarningInfo;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.utils.MapDistence;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.storage.persistence.VehicleLocationMapper;
import com.tiamaes.bike.storage.persistence.VehicleStatusInfoMapper;
import com.tiamaes.bike.storage.persistence.VehicleWarningInfoMapper;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class LocatioInfoService implements LocatioInfoServiceInterface {
	private static Logger logger = LogManager.getLogger(LocatioInfoService.class);
	@Resource
	private VehicleLocationMapper  vehicleLocationMapper;
	@Resource
	private VehicleStatusInfoMapper vehicleStatusInfoMapper;
	@Resource
	private VehicleWarningInfoMapper vehicleWarningInfoMapper;
	@Resource
	private VehicleServiceInterface vehicleService;
	@Resource
	private ParkServiceInterface parkService;
	@Resource
	private ApiFeignSerivceInterface apiFeignSerivce;
	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> stringOperator;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, User> userOperator;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, PointVector> pointVectorOperator;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveVehicleWarningInfo(VehicleWarningInfo vehicleWarningInfo) throws Exception {
		Assert.notNull(vehicleWarningInfo, "车辆报警记录不能为空");
		Assert.notNull(vehicleWarningInfo.getVehicle(), "车辆信息不能为空");
		String id = vehicleWarningInfo.getVehicle().getId();
		Assert.notNull(id, "车辆id不能为空");
		Vehicle vehicle = vehicleService.getVehicleById(id);
		vehicleWarningInfo.setVehicle(vehicle);
		vehicleWarningInfo.setId(UUIDGenerator.getUUID());
		vehicleWarningInfo.setCreateTime(new Date());
		vehicleWarningInfoMapper.addWarningInfo(vehicleWarningInfo);
		if(logger.isDebugEnabled()){
			logger.debug("批量保存车辆报警信息 : ", vehicleWarningInfo);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void savePointVector(Vehicle vehicle) throws Exception {
		Assert.notNull(vehicle,"车辆信息不能为空");
		String vehicleId = vehicle.getId();
		Assert.notNull(vehicleId, "车辆id不能为空");
		Vehicle vehicleTemp = vehicleService.getVehicleById(vehicleId);
		if (vehicle.getPark() != null && StringUtils.isNotBlank(vehicle.getPark().getId())) {
			Park park = parkService.getParkById(vehicle.getPark().getId());
			vehicleTemp.setPark(park);
			apiFeignSerivce.update(vehicleTemp, vehicleId);
		}
		PointVector pointVector = pointVectorOperator.get(RedisKey.VEHICLES_POINTVECTORS, vehicleId);
		PointVector.Info info;
		PointVector.Center center;
		if(pointVector == null){
			pointVector = new PointVector();
			info = new PointVector.Info();
			center = new PointVector.Center();
		}else{
			info = pointVector.getInfo();
			center = pointVector.getCenter();
		}
		if(center == null){
			center = new PointVector.Center();
		}
		if(info == null){
			info = new PointVector.Info();
		}
		String oldLng = String.valueOf(center.getLng());
		String oldLat = String.valueOf(center.getLat());
		center = vehicle.getCenter();
		info = vehicle.getInfo();
		String oldDistence = stringOperator.get(RedisKey.VEHICLE_KM, vehicleId);
		if (StringUtils.isBlank(oldDistence)) {
			oldDistence = "0.0";
		}
		String distence = MapDistence.getDistance(String.valueOf(center.getLat()), 
				String.valueOf(center.getLng()), oldLat, oldLng);
		String newDistence = String.valueOf(Double.valueOf(oldDistence) + Double.valueOf(distence));
		stringOperator.put(RedisKey.VEHICLE_KM, vehicleId, newDistence);
		User user = userOperator.get(RedisKey.VEHICLES_DRIVERS_VEHICLEID, vehicleId);
		if (user != null) {
			info.setUser(user);
		}
		pointVector.setCenter(center);
		pointVector.setInfo(info);
		pointVectorOperator.put(RedisKey.VEHICLES_POINTVECTORS, vehicleId, pointVector);
		vehicleTemp.setInfo(info);
		vehicleTemp.setCenter(center);
		String id = UUIDGenerator.getUUID();
		vehicleTemp.setLocationInfoId(id);
		vehicleTemp.setStatusInfoId(id);
		Date now = new Date();
		vehicleTemp.setLocationInfoCreateDate(now);
		vehicleTemp.setStatusInfoCreateDate(now);
		vehicleLocationMapper.addVehicleLocation(vehicleTemp);
		vehicleStatusInfoMapper.addStatusInfo(vehicleTemp);
	}
}
