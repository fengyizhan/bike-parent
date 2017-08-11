package com.tiamaes.bike.wallet.information.pay.service;

import java.util.List;

import com.tiamaes.bike.common.bean.pay.PayRecharge;
import com.tiamaes.mybatis.Pagination;

public interface PayRechargeServiceInterface {
	
	/**
	 * 根据主键id查询用户充值信息
	 * @param id
	 * @return
	 */
	PayRecharge getPayRechargeById(int id);
	
	/**
	 * 根据用户名查询用户充值信息
	 * @param username
	 * @return
	 */
	List<PayRecharge> getListOfPayRechargesByUsername(PayRecharge payRecharge, Pagination<PayRecharge> pagination);
	
	/**
	 * 新增用户充值信息
	 * @param payRecharge
	 * @return
	 */
	PayRecharge addPayRecharge(PayRecharge payRecharge);
	
	/**
	 * 更新用户充值信息
	 * @param payRecharge
	 * @return
	 */
	PayRecharge updatePayRecharge(PayRecharge payRecharge);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();
	
}
