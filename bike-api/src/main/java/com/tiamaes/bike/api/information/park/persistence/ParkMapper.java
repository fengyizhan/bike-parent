package com.tiamaes.bike.api.information.park.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.system.PointVector.Center;

public interface ParkMapper {
	
	/**
	 * 根据集中停放区编号查询集中停放区信息
	 * @param id
	 * @return
	 */
	Park getParkById(@Param("id")String id);
	
	/**
	 * 根据参数获取集中停放区列表
	 * @param park
	 * @return
	 */
	List<Park> getListOfParks(Park park);
	
	/**
	 * 新增集中停放区信息
	 * @param park
	 */
	void addPark(Park park);
	
	/**
	 * 更新集中停放区信息
	 * @param park
	 */
	void updatePark(Park park);
	
	/**
	 * 删除集中停放区信息
	 * @param park
	 */
	void deletePark(Park park);
	
	/**
	 * 检查集中停放区名是否已经存在
	 * @param param
	 * @return
	 */
	boolean hasExists(@Param("id")String id, @Param("name")String name);
	
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
