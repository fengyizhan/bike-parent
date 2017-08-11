package com.tiamaes.bike.wallet.information.deposit.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.wallet.Deposit;

public interface DepositMapper {
	
	/**
	 * 根据id查询用户押金信息
	 * @param id
	 * @return
	 */
	Deposit getDepositById(@Param("id")int id);
	
	/**
	 * 根据用户名查询用户押金信息
	 * @param deposit
	 * @return
	 */
	List<Deposit> getListOfDeposits(Deposit deposit);
	
	/**
	 * 新增用户押金信息
	 * @param deposit
	 */
	void addDeposit(Deposit deposit);
	
	/**
	 * 更新用户押金信息
	 * @param deposit
	 */
	void updateDeposit(Deposit deposit);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();
	
}
