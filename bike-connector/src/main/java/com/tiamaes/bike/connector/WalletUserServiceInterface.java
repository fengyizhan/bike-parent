package com.tiamaes.bike.connector;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiamaes.bike.common.bean.wallet.Consume;
import com.tiamaes.bike.common.bean.wallet.WalletUser;

/**
 * 用户钱包webservice远程调用存根
 * @author fyz
 *
 */
@FeignClient("bike-wallet")
public interface WalletUserServiceInterface {
	
	/**
	 * 根据用户名查询用户钱包信息
	 * @param username 用户名
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/wallet/information/walletUser/{username}")
	WalletUser getWalletUserByUsername(@PathVariable("username")String username);
	
	/**
	 * 新增用户钱包信息
	 * @param walletUser 钱包信息
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/wallet/information/walletUser")
	WalletUser addWalletUser(@RequestBody WalletUser walletUser);
	
	/**
	 * 新增用户钱包信息
	 * @param walletUser 钱包信息
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/wallet/information/walletUser/updateWalletUserByPayConsume")
	boolean updateWalletUserByPayConsume(@RequestBody Consume consume);
}
