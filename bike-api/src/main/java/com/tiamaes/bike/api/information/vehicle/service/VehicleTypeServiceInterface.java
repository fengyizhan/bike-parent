package com.tiamaes.bike.api.information.vehicle.service;

import java.util.List;

import com.tiamaes.bike.common.bean.information.VehicleType;

public interface VehicleTypeServiceInterface {

	/**
	 * 获取所有车辆类型列表，用于下拉菜单生成
	 * @return
	 */
	List<VehicleType> getVehicleTypes();
}
