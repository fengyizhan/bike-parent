package com.tiamaes.bike.storage.persistence;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.integrated.ParkVehicleUseRecord;

public interface VehicleUseInfoMapper {

	/**
	 * 增加车辆使用记录
	 * @param parkVehicleUseRecord
	 */
	void addVehicleUseInfo(ParkVehicleUseRecord parkVehicleUseRecord);
	
	/**
	 * 根据id删除车辆使用记录
	 * @param id
	 */
	void deleteVehicleUseInfo(@Param("id")String id);
}
