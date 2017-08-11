package com.tiamaes.bike.api.information.station.service;

import java.util.List;
import java.util.Optional;

import com.tiamaes.bike.common.bean.information.Station;
import com.tiamaes.mybatis.Pagination;

public interface StationServiceInterface {
	
	/**
	 * 根据Id查询电子围栏详情
	 * @param id
	 * @return
	 */
	Station getStationById(int id);
	
	/**
	 * 查询电子围栏列表
	 * @param station
	 * @param pagination 
	 * @return
	 */
	List<Station> getListOfStations(Station station, Pagination<Station> pagination);
	
	/**
	 * 新增电子围栏信息
	 * @param station
	 * @return
	 */
	Station addStation(Station station);
	
	/**
	 * 更新电子围栏信息
	 * @param station
	 * @return
	 */
	Station updateStation(Station station);
	
	/**
	 * 删除电子围栏信息
	 * @param station
	 */
	void deleteStation(Station station);
	
	/**
	 * 检查电子围栏名是否已经存在
	 * @param id 
	 * @param name
	 * @return
	 */
	boolean checkStationName(Optional<Integer> id, String name);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();
}
