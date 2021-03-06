package com.tiamaes.bike.wallet.information.pay.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.pay.PayRecharge;

public interface PayRechargeMapper {
	
	/**
	 * 根据id查询用户充值信息
	 * @param id
	 * @return
	 */
	PayRecharge getPayRechargeById(@Param("id")int id);
	
	/**
	 * 根据用户名查询用户充值信息
	 * @param recharge
	 * @return
	 */
	List<PayRecharge> getListOfPayRechargesByUsername(PayRecharge payRecharge);
	
	/**
	 * 新增用户充值信息
	 * @param payRecharge
	 */
	void addPayRecharge(PayRecharge payRecharge);
	
	/**
	 * 更新用户充值信息
	 * @param payRecharge
	 */
	void updatePayRecharge(PayRecharge payRecharge);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();
	
}
