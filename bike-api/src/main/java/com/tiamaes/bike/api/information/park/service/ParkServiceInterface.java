package com.tiamaes.bike.api.information.park.service;

import java.util.List;
import java.util.Optional;

import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.system.PointVector.Center;
import com.tiamaes.mybatis.Pagination;

public interface ParkServiceInterface {
	
	/**
	 * 根据id获取集中停放区详细信息
	 * @param id
	 * @return
	 */
	Park getParkById(String id);
	
	/**
	 * 查询集中停放区列表信息
	 * @param park
	 * @param pagination
	 * @return
	 */
	List<Park> getListOfParks(Park park, Pagination<Park> pagination);
	
	/**
	 * 新增集中停放区信息
	 * @param park
	 * @return
	 */
	Park addPark(Park park);
	
	/**
	 * 更新集中停放区信息
	 * @param park
	 * @return
	 */
	Park updatePark(Park park);
	
	/**
	 * 删除集中停放区信息
	 * @param park
	 */
	void deletePark(Park park);
	
	/**
	 * 检查集中停放区名是否已经存在
	 * @param id 
	 * @param name
	 * @return
	 */
	boolean checkParkName(Optional<String> id, String name);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();

	/**
	 * 全部集中停放区列表信息
	 * @return
	 */
	List<Park> getAllParks();

	/**
	 * 获取坐标点附近停放区信息
	 * @param center
	 * @return
	 */
	List<Park> getAroundParks(Center center);

}
