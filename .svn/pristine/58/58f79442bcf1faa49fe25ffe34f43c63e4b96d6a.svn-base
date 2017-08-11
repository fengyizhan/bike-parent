package com.tiamaes.bike.wallet.information.recharge.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.wallet.Recharge;

public interface RechargeMapper {
	
	/**
	 * 根据id查询用户充值信息
	 * @param id
	 * @return
	 */
	Recharge getRechargeById(@Param("id")int id);
	
	/**
	 * 根据用户名查询用户充值信息
	 * @param recharge
	 * @return
	 */
	List<Recharge> getListOfRecharges(Recharge recharge);
	
	/**
	 * 根据参数查询用户充值信息
	 * @param recharge
	 * @return
	 */
	List<Recharge> getListOfRechargesByUsername(Recharge recharge);
	
	/**
	 * 新增用户充值信息
	 * @param recharge
	 */
	void addRecharge(Recharge recharge);
	
	/**
	 * 更新用户充值信息
	 * @param recharge
	 */
	void updateRecharge(Recharge recharge);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();
	
}
