package com.tiamaes.bike.wallet.information.recharge.service;

import java.util.List;

import com.tiamaes.bike.common.bean.wallet.Recharge;
import com.tiamaes.mybatis.Pagination;

public interface RechargeServiceInterface {
	
	/**
	 * 根据主键id查询用户充值信息
	 * @param id
	 * @return
	 */
	Recharge getRechargeById(int id);
	
	/**
	 * 根据用户名查询用户充值信息
	 * @param username
	 * @return
	 */
	List<Recharge> getListOfRechargesByUsername(Recharge recharge, Pagination<Recharge> pagination);
	
	/**
	 * 根据参数查询用户充值信息
	 * @param username
	 * @return
	 */
	List<Recharge> getListOfRecharges(Recharge recharge, Pagination<Recharge> pagination);
	
	/**
	 * 新增用户充值信息
	 * @param recharge
	 * @return
	 */
	Recharge addRecharge(Recharge recharge);
	
	/**
	 * 更新用户充值信息
	 * @param recharge
	 * @return
	 */
	Recharge updateRecharge(Recharge recharge);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();
	
}
