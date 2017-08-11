package com.tiamaes.bike.storage.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiamaes.bike.common.bean.wallet.Consume;

/**
 * 远程调用wallet相关服务 
 * @author kangty
 */
@FeignClient("bike-wallet")
public interface WalletFeignSerivceInterface {

	@RequestMapping(method = RequestMethod.POST, value = "/wallet/information/walletUser/updateWalletUserByPayConsume")
	boolean updateWalletUserByPayConsume(@RequestBody Consume consume);
	
}
