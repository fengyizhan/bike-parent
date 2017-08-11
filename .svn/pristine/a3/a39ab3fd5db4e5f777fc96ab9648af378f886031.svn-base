package com.tiamaes.bike.api.authority.role.service;

import java.util.List;

import com.tiamaes.mybatis.Pagination;
import com.tiamaes.bike.common.bean.system.Role;
import com.tiamaes.bike.common.bean.system.RoleAuthority;

public interface RoleServiceInterface {
	/**
	 * 根据条件查询角色总数
	 * @param parameters
	 * @return
	 */
	public abstract int getTotalOfRoles(Role params);
	/**
	 * 根据条件分页查询角色列表
	 * @param params
	 * @param page 
	 * @return
	 */
	public abstract List<Role> getAllRoles(Role params, Pagination<?> pagination);
	/**
	 * 查询角色列表
	 * @return
	 */
	public abstract List<Role> getAllRoles();
	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 * @throws Exception 
	 */
	public abstract Role updateRole(Role role);

	/**
	 * 增加角色
	 * 
	 * @param role
	 * @return
	 * @throws Exception 
	 */
	public Role saveRole(Role role);

	/**
	 * 根据角色编号查询角色信息
	 * 
	 * @param roleId
	 * @return
	 */
	public abstract Role getRoleById(String id);
	/**
	 * 根据id删除指定的角色
	 * 
	 * @param roleId
	 * @return
	 */
	public abstract void deleteRoleById(String roleId);
	/**
	 * 根据角色类型查询角色
	 * @param type
	 * @return
	 */
	public List<Role> getAllRolesByType(String type);
	/**
	 * 查询角色权限
	 * @param id
	 * @return
	 */
	public abstract List<RoleAuthority> getAuthorization(String id);
	
	/**
	 * 根据角色获取首页路径
	 * @param authority
	 * @return
	 */
	String getIndexByRole(String authority);
}
