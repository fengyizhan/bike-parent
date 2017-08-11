package com.tiamaes.bike.storage.service;

import com.tiamaes.bike.common.bean.information.Vehicle;

/**
 * 车辆信息处理服务
 * @author lsl
 *
 */
public interface VehicleServiceInterface {
	
	/**
	 * 通过车辆id获取车辆信息(公司编号，公司名称，车队编号，车队名称，车辆编号，车牌号，SIM卡号)
	 * @param simNo
	 * @return
	 */
	Vehicle getVehicleById(String id);
	
	
}
