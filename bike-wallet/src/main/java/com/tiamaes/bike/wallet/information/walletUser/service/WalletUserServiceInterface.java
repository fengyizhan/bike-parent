package com.tiamaes.bike.wallet.information.walletUser.service;

import com.tiamaes.bike.common.bean.wallet.Consume;
import com.tiamaes.bike.common.bean.wallet.Deposit;
import com.tiamaes.bike.common.bean.wallet.Recharge;
import com.tiamaes.bike.common.bean.wallet.WalletUser;

public interface WalletUserServiceInterface {
	
	/**
	 * 根据用户名查询用户钱包信息
	 * @param username
	 * @return
	 */
	WalletUser getWalletUserByUsername(String username);
	
	/**
	 * 新增用户钱包信息
	 * @param walletUser
	 * @return
	 */
	WalletUser addWalletUser(WalletUser walletUser);
	
	/**
	 * 更新用户钱包信息
	 * @param walletUser
	 * @return
	 */
	WalletUser updateWalletUser(WalletUser walletUser);
	
	/**
	 * 删除用户钱包信息
	 * @param walletUser
	 */
	void deleteWalletUser(WalletUser walletUser);
	
	/**
	 * 由充值，引起【用户钱包】实体数据的原子性变化
	 * 涉及到金额变化，必须保证强一致性事务
	 * @param recharge 充值变化实体
	 * @return 执行是否成功
	 */
	boolean updateWalletUserByPayRecharge(Recharge recharge);
	
	/**
	 * 由结账付款，引起【用户钱包】实体数据的原子性变化
	 * 涉及到金额变化，必须保证强一致性事务
	 * @param Consume 充值变化实体
	 * @return 执行是否成功
	 */
	boolean updateWalletUserByPayConsume(Consume consume);
	/**
	 * 由押金充值、退款、扣款，引起【用户钱包】实体数据的原子性变化
	 * 涉及到金额变化，必须保证强一致性事务
	 * @param Deposit 押金变化实体
	 * @return 执行是否成功
	 */
	boolean updateWalletUserByDeposit(Deposit deposit);
	
}
