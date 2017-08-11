package com.tiamaes.bike.api.information.vehicle.service;

import java.util.List;

import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.mybatis.Pagination;

public interface VehicleServiceInterface {
	
	/**
	 * 根据车辆编号查询车辆信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Vehicle getVehicleById(String id);
	
	/**
	 * 查询所有车辆
	 * @return
	 */
	List<Vehicle> getListOfVehicles(Parameters<Vehicle> parameters);
	
	/**
	 * 查询注册车辆列表
	 * @param page 
	 * @return
	 */
	List<Vehicle> getListOfVehicles(Parameters<Vehicle> parameters, Pagination<Vehicle> pagination);
	
	/**
	 * 保存车辆信息
	 * @param vehicle
	 * @return
	 */
	Vehicle addVehicle(Vehicle vehicle);
	
	/**
	 * 更新车辆信息
	 * @param vehicle
	 * @return
	 */
	Vehicle updateVehicle(Vehicle vehicle);
	
	/**
	 * 删除车辆信息
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
	String getBikePutInTime(int bikeId);
}
