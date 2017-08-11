package com.tiamaes.bike.reporter.integrated.vehicle.persistence;

import java.util.List;
import java.util.Map;

import com.tiamaes.bike.common.bean.system.PointVector.Center;
import com.tiamaes.bike.common.bean.integrated.LocationRecord;

/**
 * 车辆定位信息mapper文件
 * @author waibao001
 *
 */
public interface VehicleLocationMapper {
	
	/**
	 * 根据租借记录ID获取本次租借行程对应的历史轨迹
	 * @return 历史轨迹列表
	 */
	List<Center> getLocationByBorrow(String borrowId);
	/**
	 * 根据车辆id和时间段获取定位的经纬度
	 * @return
	 */
	List<Center> getLocationByTime(LocationRecord locationRecord);
	
	/**
	 * 增加车辆定位信息
	 * @param vehicleLocation
	 * @return
	 */
	void addVehicleLocation(LocationRecord locationRecord);
	/**
	 * 根据id获取车辆定位信息
	 * @param id
	 * @return
	 */
	LocationRecord getLocationById(String id);
	/**
	 * 获取车辆历史定位信息
	 * @param locationRecord
	 * @return
	 */
	List<LocationRecord> getListOfHistoryPositionInfo(LocationRecord locationRecord);
	/**
	 * 获取车辆实时定位信息
	 * @param param
	 * @return
	 */
	List<LocationRecord> getListOfRealtimePositionInfo(Map<String, Object> param);
}
