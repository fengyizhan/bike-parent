package com.tiamaes.bike.api.information.dictionary.persistence;

import java.util.List;

import com.tiamaes.bike.common.bean.information.Region;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.information.VehicleType;

public interface DictionaryMapper {
	
	/**
	 * 获取生产厂家下拉列表
	 * @return
	 */
	List<Vehicle.Factory> getListOfFactories();
	
	/**
	 * 获取终端类型下拉列表
	 * @return
	 */
	List<Vehicle.Type> getListOfTypes();
	
	/**
	 * 获取所有车辆类型列表，用于下拉菜单生成 
	 * @return
	 */
	List<VehicleType>  getListOfVehicleTypes();
	
	/**
	 * 根据地区级别或者所属上级查询地区信息
	 * @param region
	 * @return
	 */
	List<Region> getListOfRegions(Region region);
	
}
