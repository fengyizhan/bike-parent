package com.tiamaes.bike.wallet.information.deposit.service;

import java.util.List;

import com.tiamaes.bike.common.bean.wallet.Deposit;
import com.tiamaes.mybatis.Pagination;

public interface DepositServiceInterface {
	
	/**
	 * 根据主键id查询用户押金信息
	 * @param id
	 * @return
	 */
	Deposit getDepositById(int id);
	
	/**
	 * 根据参数查询用户押金信息
	 * @param username
	 * @return
	 */
	List<Deposit> getListOfDeposits(Deposit deposit, Pagination<Deposit> pagination);
	
	/**
	 * 新增用户押金信息
	 * @param deposit
	 * @return
	 */
	Deposit addDeposit(Deposit deposit);
	
	/**
	 * 更新用户押金信息
	 * @param deposit
	 * @return
	 */
	Deposit updateDeposit(Deposit deposit);
	
	/**
	 * 获取主键id
	 * @return
	 */
	Integer getId();
	
}
