package com.tiamaes.bike.wallet.information.consume.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.wallet.Consume;

public interface ConsumeMapper {
	
	/**
	 * 根据id查询用户消费信息
	 * @param id
	 * @return
	 */
	Consume getConsumeById(@Param("id")int id);
	
	/**
	 * 根据用户名查询用户消费信息
	 * @param consume
	 * @return
	 */
	List<Consume> getListOfConsumesByUsername(Consume consume);
	
	/**
	 * 新增用户消费信息
	 * @param consume
	 */
	void addConsume(Consume consume);
	
	/**
	 * 更新用户消费信息
	 * @param consume
	 */
	void updateConsume(Consume consume);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();
	
}
