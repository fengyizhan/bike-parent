package com.tiamaes.bike.api.authority.user.persistence;



import java.util.List;

import org.springframework.data.repository.query.Param;

import com.tiamaes.bike.common.bean.system.Role;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.security.provisioning.MutableUser;


/**
 * 用户数据持久层操作接口
 * @author chen
 *
 */
public interface UserMapper {
	
	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 * @throws Exception
	 */
	User loadUserDetailByUsername(@Param("username")String username);
	/**
	 * 根据用户编号查询用户信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	User getUserById(@Param("id")String id);
	/**
	 * 查询所有用户
	 * @return
	 */
	List<User> getAllUsers();
	/**
	 * 查询所有用户
	 * @return
	 */
	List<User> getAllUsers(User user);
	/**
	 * 查询指定角色的用户列表
	 * @param role
	 * @return
	 */
	List<User> getAllUsersByRole(Role role);
	/**
	 * 查询注册用户数
	 * @return
	 */
	int getTotalOfUsers(User user);
	/**
	 * 保存用户
	 * @param user
	 */
	void addUser(User user);
	/**
	 * 保存用户设置
	 * @param user
	 */
	void addUserSettings(User user);
	/**
	 * 保存用户角色
	 * @param authorities
	 */
	void addUserRoles(User user);
	/**
	 * 更新用户
	 * @param user
	 */
	void updateUser(User user);
	/**
	 * 更新用户设置
	 * @param user
	 */
	void updateUserSettings(User user);
	/**
	 * 修改指定用户的密码
	 * @param id
	 * @param newpwd
	 */
	void updatePassword(MutableUser user);
	/**
	 * 删除用户
	 * @param user
	 */
	void deleteUser(User user);
	/**
	 * 删除用户关联角色
	 * @param user
	 */
	void deleteUserRoles(User user);
	/**
	 * 删除用户设置
	 * @param user
	 */
	void deleteUserSettings(User user);
	/**
	 * 是否有用户设置
	 * @param source
	 * @return
	 */
	boolean existsUserInfo(User user);
	/**
	 * 检查用户名是否已经存在
	 * @param username
	 * @return
	 */
	boolean hasExists(String username);
	/**
	 * 根据角色判断有没有该用户的角色
	 * @param authority
	 * @return
	 */
	boolean hasRole(String authority);
}
