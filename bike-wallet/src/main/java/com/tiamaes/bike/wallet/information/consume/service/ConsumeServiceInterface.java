package com.tiamaes.bike.wallet.information.consume.service;

import java.util.List;

import com.tiamaes.bike.common.bean.wallet.Consume;
import com.tiamaes.mybatis.Pagination;

public interface ConsumeServiceInterface {
	
	/**
	 * 根据主键id查询用户消费信息
	 * @param id
	 * @return
	 */
	Consume getConsumeById(int id);
	
	/**
	 * 根据用户名查询用户消费信息
	 * @param username
	 * @return
	 */
	List<Consume> getListOfConsumesByUsername(Consume consume, Pagination<Consume> pagination);
	
	/**
	 * 新增用户消费信息
	 * @param consume
	 * @return
	 */
	Consume addConsume(Consume consume);
	
	/**
	 * 更新用户消费信息
	 * @param consume
	 * @return
	 */
	Consume updateConsume(Consume consume);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();
	
}
