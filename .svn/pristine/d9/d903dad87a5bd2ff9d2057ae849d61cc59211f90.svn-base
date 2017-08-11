package com.tiamaes.bike.api.information.driver.service;

import java.util.List;

import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.mybatis.Pagination;

public interface DriverServiceInterface {
	
	/**
	 * 根据IC卡号获取用户信息
	 * @param icCardNumber
	 * @return
	 */
	User getDriverByIccardNumber(String icCardNumber);
	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	User getDriverByUsername(String username);
	
	/**
	 * 分页查询用户列表
	 * @param user
	 * @param pagination
	 * @return
	 */
	List<User> getListOfDrivers(User user, Pagination<User> pagination);
	
	/**
	 * 保存用户信息
	 * @param user
	 */
	User addDriver(User user);
	
	/**
	 * 更新用户昵称信息
	 * @param user
	 * @return
	 */
	User updateDriverNickname(User user);
	
	/**
	 * 更新用户黑白名单信息
	 * @param username
	 * @return
	 */
	User updateDriverState(String username);
	
	/**
	 * 删除用户信息
	 * @param driver
	 */
	void deleteDriver(User user);
	
	/**
	 * 验证用户身份证号是否存在
	 * @param identityCard
	 * @return
	 */
	boolean checkIdentityCard(String identityCard);
	/**
	 * 更新用户状态  0：已注册；1：已交押金；2：已实名认证
	 * @param user
	 */
	User updateDriverStage(User user);
	
	/**
	 * 用户实名认证
	 * @param user
	 */
	User certification(User user);
}
