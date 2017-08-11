package com.tiamaes.bike.api.information.vehicle.persistence;

import java.util.List;

import com.tiamaes.bike.common.bean.information.VehicleType;

public interface VehicleTypeMapper {
	
	/**
	 * 获取所有车辆类型列表，用于下拉菜单生成
	 * @return
	 */
	List<VehicleType> getListOfVehicleTypes();
	
}
