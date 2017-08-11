package com.tiamaes.bike.api.information.rate.service;

import java.util.List;
import java.util.Optional;

import com.tiamaes.bike.common.bean.information.Rate;
import com.tiamaes.mybatis.Pagination;

public interface RateServiceInterface {
	
	/**
	 * 根据id获取费率详细信息
	 * @param id
	 * @return
	 */
	Rate getRateById(String id);
	
	/**
	 * 查询费率列表信息
	 * @param rate
	 * @param pagination
	 * @return
	 */
	List<Rate> getListOfRates(Rate rate, Pagination<Rate> pagination);
	
	/**
	 * 新增费率信息
	 * @param rate
	 * @return
	 */
	Rate addRate(Rate rate);
	
	/**
	 * 更新费率信息
	 * @param rate
	 * @return
	 */
	Rate updateRate(Rate rate);
	
	/**
	 * 删除费率信息
	 * @param rate
	 */
	void deleteRate(Rate rate);
	
	/**
	 * 检查费率级别是否已经存在
	 * @param id 
	 * @param stageLevel
	 * @return
	 */
	boolean checkRateLevel(Optional<String> id, Integer stageLevel);
	
	/**
	 * 获取已存在的费率级别
	 * @return
	 */
	List<Integer> getListOfRateLevels();

}
