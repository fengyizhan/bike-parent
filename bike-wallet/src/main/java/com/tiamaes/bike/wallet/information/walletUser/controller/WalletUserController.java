package com.tiamaes.bike.wallet.information.walletUser.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.bike.common.bean.wallet.Consume;
import com.tiamaes.bike.common.bean.wallet.WalletUser;
import com.tiamaes.bike.wallet.information.walletUser.service.WalletUserServiceInterface;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/information/walletUser")
@Api(tags = {"walletUser"}, description = "用户钱包信息管理")
public class WalletUserController {
	
	private static Logger logger = LogManager.getLogger(WalletUserController.class);
	@Resource
	private WalletUserServiceInterface walletUserService;
	
	/**
	 * 根据用户名获取用户钱包信息
	 * @param username
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "用户钱包信息", notes = "根据用户名获取用户钱包信息")
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public @ResponseBody WalletUser get(@PathVariable("username")String username, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){
			logger.debug(username);
		}
		WalletUser walletUser = walletUserService.getWalletUserByUsername(username);
		if (walletUser == null) {
			walletUser = new WalletUser();
			walletUser.setUsername(username);
			walletUser.setBalance(0.0F);
			walletUser = walletUserService.addWalletUser(walletUser);
		}
		return walletUser;
	}
	
	/**
	 * 新增用户钱包信息
	 * @param walletUser
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "新增用户钱包信息", notes = "新增用户钱包信息",response = WalletUser.class)
	@ApiImplicitParam(name = "walletUser", value = "用户钱包实体", required = true, dataType = "WalletUser")
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody WalletUser add(@RequestBody WalletUser walletUser, @CurrentUser User operator){
		if(logger.isDebugEnabled()){
			logger.debug(walletUser.toString());
		}
		walletUser = walletUserService.addWalletUser(walletUser);
		return walletUser;
	}
	
	/**
	 * 更新用户钱包信息
	 * @param walletUser
	 * @param username
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "更新用户钱包信息", notes = "更新用户钱包信息",response = WalletUser.class)
	@ApiImplicitParam(name = "walletUser", value = "用户钱包实体", required = true, dataType = "WalletUser")
	@RequestMapping(value = "/{username}", method = RequestMethod.PUT)
	public @ResponseBody WalletUser update(@RequestBody WalletUser walletUser, @PathVariable("username")String username, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){	
			logger.debug(walletUser.toString());
		}
		walletUser.setUsername(username);
		walletUser = walletUserService.updateWalletUser(walletUser);
		return walletUser;
	}
	
	/**
	 * 删除用户钱包信息
	 * @param id
	 * @param operator
	 */
	@ApiOperation(value = "删除用户钱包信息", notes = "删除用户钱包信息")
	@RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable("username")String username, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){
			logger.debug(username);
		}
		WalletUser walletUser = walletUserService.getWalletUserByUsername(username);
		walletUserService.deleteWalletUser(walletUser);
	}
	
	/**
	 * 由【还车消费】引发的更新用户钱包信息
	 * @param consume
	 * @return
	 */
	@ApiOperation(value = "【还车消费】引发的更新用户钱包信息", notes = "【还车消费】引发的更新用户钱包信息",response = Boolean.class)
	@ApiImplicitParam(name = "consume", value = "用户消费记录实体", required = true, dataType = "Consume")
	@RequestMapping(value = "/updateWalletUserByPayConsume", method = RequestMethod.POST)
	public @ResponseBody boolean updateWalletUserByPayConsume(@RequestBody Consume consume) {
		if(logger.isDebugEnabled()){
			logger.debug(consume.toString());
		}
		boolean success=walletUserService.updateWalletUserByPayConsume(consume);
		return success;
	}
	
}
