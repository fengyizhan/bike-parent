package com.tiamaes.bike.connector;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiamaes.bike.common.bean.system.User;

/**
 * 用户【扩展信息】webservice远程调用存根
 * @author fyz
 *
 */
@FeignClient("bike-api")
public interface DriverServiceInterface {
	/**
	 * 根据用户Ic卡号获取用户信息
	 * @param username
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/information/driver/iccard/{icCardNumber}")
	User getDriverByIcCard(@PathVariable("icCardNumber") String icCardNumber);
	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/information/driver/{username}")
	User getDriverByUsername(@PathVariable("username") String username);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/api/information/driver")
	public User addDriver(@RequestBody User user);
	/**
	 * 更新用户状态  0：已注册；1：已交押金；2：已实名认证
	 * @param user
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/api/information/driver/updateDriverStage")
	User updateDriverStage(@RequestBody User user);
	
	/**
	 * 用户实名认证
	 * @param user
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/api/information/driver/certification")
	User certification(@RequestBody User user);
}
