package com.tiamaes.bike.storage.service;

import com.tiamaes.bike.common.bean.connector.VehicleOnOffLineInfo;

public interface VehicleOnOffLineInfoServiceInterface {
	
	/**
	 * 增加车辆上下线记录
	 * @param vehicleOnOffLineInfo
	 * @return 
	 */
	String addVehicleOnOffLineInfo(VehicleOnOffLineInfo vehicleOnOffLineInfo) throws Exception;
	
	/**
	 * 根据id删除车辆上下线信息
	 * @param id
	 */
	void deleteVehicleOnOffLineInfo(String id);

}
