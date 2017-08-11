package com.tiamaes.bike.api.information.countdata.persistence;

/**
 * 总计数据持久层接口
 * @author kangty
 */
public interface CountDataMapper {
	
	/**
	 * 获取车辆总数
	 * @return
	 */
	int getTotalOfBikes();
	
	/**
	 * 获取站点总数
	 * @return
	 */
	int getTotalOfParks();
	
	/**
	 * 获取用户总数
	 * @return
	 */
	int getTotalOfUsers();
}
