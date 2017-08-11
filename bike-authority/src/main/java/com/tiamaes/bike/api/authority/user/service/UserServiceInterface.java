package com.tiamaes.bike.api.authority.user.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

import com.tiamaes.bike.common.bean.Result;
import com.tiamaes.bike.common.bean.system.LoginModel;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.provisioning.MutableUser;

public interface UserServiceInterface extends UserDetailsService{
	/**
	 * 根据Id查询用户详情
	 * @param id
	 * @return
	 */
	User getUserById(String id);
	/**
	 * 查询注册用户列表
	 * @param user
	 * @param pagination
	 * @return
	 */
	List<User> getAllUsers(User user, Pagination<?> pagination);
	/**
	 * 查询注册用户数
	 * @return
	 */
	int getTotalOfUsers(User user);
	/**
	 * 保存用户
	 * @param user
	 * @throws Exception 
	 */
	User addUser(User user);
	/**
	 * 更新用户
	 * @param user
	 * @throws Exception 
	 */
	User updateUser(User user);
	/**
	 * 删除用户
	 * @param username
	 */
	void deleteUser(String username);
	/**
	 * 修改用户密码
	 * @param user
	 */
	void upadatePassword(MutableUser user);
	/**
	 * 检查用户名是否已经存在
	 * @param username
	 * @return
	 */
	boolean checkUserName(String username);
	/**
	 * 根据角色判断有没有该用户的角色
	 * @param authority
	 * @return
	 */
	boolean hasRole(String authority);
	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	User loadUserDetailByUsername(@Param("username")String username);
	/**
	 * APP用户登陆认证
	 * @param loginModel 登陆信息
	 * @return 登陆校验
	 */
	Result userCheck(@RequestBody LoginModel loginModel);
}
