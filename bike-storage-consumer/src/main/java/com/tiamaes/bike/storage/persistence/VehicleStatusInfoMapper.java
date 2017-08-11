package com.tiamaes.bike.storage.persistence;

import com.tiamaes.bike.common.bean.information.Vehicle;

/**
 * 车辆状态信息mapper
 * @author waibao001
 *
 */
public interface VehicleStatusInfoMapper {
	
	/**
	 * 增加车辆定位信息
	 * @param vehicle
	 */
	void addStatusInfo(Vehicle vehicle);
}
