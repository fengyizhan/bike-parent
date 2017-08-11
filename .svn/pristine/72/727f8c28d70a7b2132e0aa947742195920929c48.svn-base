package com.tiamaes.bike.api.information.station.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.information.Station;

public interface StationMapper {
	
	/**
	 * 根据电子围栏编号查询电子围栏信息
	 * @param id
	 * @return
	 */
	Station getStationById(@Param("id")int id);
	
	/**
	 * 根据参数获取电子围栏列表
	 * @param station
	 * @return
	 */
	List<Station> getListOfStations(Station station);
	
	/**
	 * 新增电子围栏信息
	 * @param station
	 */
	void addStation(Station station);
	
	/**
	 * 更新电子围栏信息
	 * @param station
	 */
	void updateStation(Station station);
	
	/**
	 * 删除电子围栏信息
	 * @param station
	 */
	void deleteStation(Station station);
	
	/**
	 * 检查电子围栏名是否已经存在
	 * @param param
	 * @return
	 */
	boolean hasExists(@Param("id")Integer id, @Param("name")String name);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();

}
