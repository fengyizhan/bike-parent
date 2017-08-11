package com.tiamaes.bike.wallet.information.walletUser.persistence;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.wallet.WalletUser;

public interface WalletUserMapper {
	
	/**
	 * 根据用户名查询用户钱包信息
	 * @param username
	 * @return
	 */
	WalletUser getWalletUserByUsername(@Param("username")String username);
	
	/**
	 * 新增用户钱包信息
	 * @param walletUser
	 */
	void addWalletUser(WalletUser walletUser);
	
	/**
	 * 更新用户钱包信息
	 * @param walletUser
	 */
	void updateWalletUser(WalletUser walletUser);
	
	/**
	 * 删除用户钱包信息
	 * @param walletUser
	 */
	void deleteWalletUser(WalletUser walletUser);
	
}
