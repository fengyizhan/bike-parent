package com.tiamaes.bike.api.information.rate.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.information.Rate;

public interface RateMapper {
	
	/**
	 * 根据费率编号查询费率信息
	 * @param id
	 * @return
	 */
	Rate getRateById(@Param("id")String id);
	
	/**
	 * 根据参数获取费率列表
	 * @param rate
	 * @return
	 */
	List<Rate> getListOfRates(Rate rate);
	
	/**
	 * 新增费率信息
	 * @param rate
	 */
	void addRate(Rate rate);
	
	/**
	 * 更新费率信息
	 * @param rate
	 */
	void updateRate(Rate rate);
	
	/**
	 * 删除费率信息
	 * @param rate
	 */
	void deleteRate(Rate rate);
	
	/**
	 * 检查费率名等级已经存在
	 * @param id
	 * @param stageLevel
	 * @return
	 */
	boolean hasExists(@Param("id")String id, @Param("stageLevel")Integer stageLevel);
	
	/**
	 * 获取费率级别列表
	 * @return
	 */
	List<Integer> getListOfRateLevels();
	
}
