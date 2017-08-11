package com.tiamaes.bike.api.information.vehicle.persistence;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.information.Vehicle;

public interface VehicleMapper {
	
	/**
	 * 根据车辆编号查询车辆信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Vehicle getVehicleById(@Param("id")String id);

	/**
	 * 查询所有车辆
	 * @return
	 */
	List<Vehicle> getListOfVehicles(Parameters<Vehicle> parameters);

	/**
	 * 保存车辆
	 * @param vehicle
	 */
	void addVehicle(Vehicle vehicle);
	
	/**
	 * 更新车辆
	 * @param vehicle
	 */
	void updateVehicle(Vehicle vehicle);
	
	/**
	 * 删除车辆
	 * @param vehicle
	 */
	void deleteVehicle(Vehicle vehicle);
	
	/**
	 * 检查车牌号是否已经存在
	 * @param plateNo
	 * @return
	 */
	boolean hasExists(String plateNo);
	
	/**
	 * 通过车牌号模糊查询所有车辆
	 * @return
	 */
	List<Vehicle> getAllVehiclesWithPlateNo(String plateNo);
	
	/**
	 * 通过simNo精确查找车辆信息
	 * @param simNo
	 * @return
	 */
	Vehicle getVehicleBySimNo(String simNo);
	
	/**
	 * 统计已注册的车辆数
	 * @return
	 */
	int getRegisteredCount();

	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();

	/**
	 * 获取指定车辆的投放时间
	 * @param bikeId
	 * @return
	 */
	String getBikePutInTime(@Param("bikeId")int bikeId);
}
