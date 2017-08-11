package com.tiamaes.bike.reporter.integrated.vehicle.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.system.PointVector.Center;
import com.tiamaes.bike.common.bean.integrated.LocationRecord;
import com.tiamaes.bike.reporter.integrated.vehicle.persistence.VehicleLocationMapper;

/**
 * 车辆定位service
 * @author waibao001
 *
 */
@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class VehicleLocationService implements VehicleLocationServiceInterface {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(VehicleLocationService.class);

	@Resource
	private VehicleLocationMapper vehicleLocationMapper;
	
	/*@Resource
	private RedisTemplate<String, VehicleLocation> vehicleLocationTemplate;*/
	
	/**
	 * 根据租借记录ID获取本次租借行程对应的历史轨迹
	 * @return 历史轨迹列表
	 */
	@Override
	public List<Center> getLocationByBorrow(String borrowId)
	{
		return vehicleLocationMapper.getLocationByBorrow(borrowId);
	}
	@Override
	public List<Center> getLocationByTime(LocationRecord locationRecord) {
		return vehicleLocationMapper.getLocationByTime(locationRecord);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public LocationRecord addVehicleLocation(LocationRecord locationRecord) {
		Assert.notNull(locationRecord,"定位信息不能为空");
		vehicleLocationMapper.addVehicleLocation(locationRecord);
		
		/*HashOperations<String, String, VehicleLocation> operator = vehicleLocationTemplate.opsForHash();
		operator.put(RedisKey.VEHICLES_LOCATIONS, vehicleLocation.getId(), vehicleLocation);*/
		
		return locationRecord;
	}


	@Override
	public LocationRecord getLocationById(String id) {
		return vehicleLocationMapper.getLocationById(id);
	}


	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<LocationRecord> getListOfHistoryPositionInfo(LocationRecord locationRecord) {
		return vehicleLocationMapper.getListOfHistoryPositionInfo(locationRecord);
	}


	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<LocationRecord> getListOfRealtimePositionInfo(LocationRecord locationRecord) {
//		List<LocationRecord> locationRecords = new ArrayList<LocationRecord>();
//		String carNo = null;//locationRecord.getVehicle().getCarNo();
//		Map<String, Object> param = new HashMap<String, Object>();
//		if (StringUtils.isBlank(carNo)) {
//			List<String> vehicleIds = null;
//			vehicleMapper.getListOfVehicleIds(
//					locationRecord.getVehicle()
//					.getProperty()
//					.getRegion()
//					.getId());
//			if (vehicleIds.size() < 1) {
//				return locationRecords;
//			} else {
//				param.put("vehicleIds", vehicleIds);
//			}
//		} else {
//			String[] carNos = null;
//			if (carNo.contains(",")) {
//				carNos = carNo.split(",");
//			} else {
//				carNos = new String[]{carNo};
//			}
//			param.put("carNos", carNos);
//		}
//		locationRecords = vehicleLocationMapper.getListOfRealtimePositionInfo(param);
//		return locationRecords;
		return null;
	}

}
