package com.tiamaes.bike.api.authority.role.persistence;

import java.util.List;

import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.bike.common.bean.system.Role;
import com.tiamaes.bike.common.bean.system.RoleAuthority;

public interface RoleMapper {

	/**
	 * 根据查询条件查询记录总数
	 * 
	 * @param map
	 * @return
	 */
	int getTotalOfRoles(Role params);
	/**
	 * 根据条件分页查询用户角色记录
	 * 
	 * @param map
	 * @param rowBounds
	 * @return
	 */
	List<Role> getAllRolesByParams(Role params);
	/**
	 * 查询角色列表
	 * @return
	 */
	List<Role> getAllRolesByParams();
	/**
	 * 根据id查询角色信息
	 * @param id
	 * @return
	 */
	Role getRoleById(String id);
	/**
	 * 保存新角色
	 * @param role
	 */
	void insertRole(Role role);
	/**
	 * 更新角色信息
	 * @param role
	 */
	void updateRole(Role role);
	/**
	 * 根据id删除指定的角色信息
	 * @param id
	 */
	void deleteRoleById(String id);
	/**
	 * 根据id删除角色相关的用户关系
	 * @param id
	 */
	void deleteUserRoleById(String id);
	/**
	 * 根据id删除角色相关的资源关系
	 * @param id
	 */
	void deleteResourceRoleById(String id);
	/**
	 * 根据类型查询角色列表
	 * @param type
	 * @return
	 */
	List<Role> getAllRolesByType(String type);
	/**
	 * 查询角色权限
	 * @param id
	 * @return
	 */
	List<RoleAuthority> getAuthorization(String id);
	/**
	 * 根据角色查询所有涉及的用户
	 * @param authority
	 * @return
	 */
	List<User> getUsersByRole(String authority);
	/**
	 * 根据角色获取首页路径
	 * @param authority
	 * @return
	 */
	String getIndexByRole(String authority);
}
