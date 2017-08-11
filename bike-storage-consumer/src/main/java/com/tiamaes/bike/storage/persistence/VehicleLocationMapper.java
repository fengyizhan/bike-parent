package com.tiamaes.bike.storage.persistence;

import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.LocationRecord;

/**
 * 车辆定位信息mapper文件
 * @author waibao001
 *
 */
public interface VehicleLocationMapper {
	
	/**
	 * 增加车辆定位信息
	 * @param vehicle
	 */
	void addVehicleLocation(Vehicle vehicle);
	
	/**
	 * 根据id查询定位信息
	 * @param id
	 * @return
	 */
	LocationRecord getLocationById(String id);
}
