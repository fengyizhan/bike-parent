package com.tiamaes.bike.api.authority.role.service;


import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.bike.api.authority.role.persistence.RoleMapper;
import com.tiamaes.bike.common.bean.system.Role;
import com.tiamaes.bike.common.bean.system.RoleAuthority;

@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class RoleService implements RoleServiceInterface {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(RoleServiceInterface.class);
	
	private static final String PREFIX = "ROLE_";

	@Autowired
	private RoleMapper roleMapper;
	

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public int getTotalOfRoles(Role params) {
		return roleMapper.getTotalOfRoles(params);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<Role> getAllRoles(Role params, Pagination<?> pagination) {
		PageHelper.startPage(pagination);
		return roleMapper.getAllRolesByParams(params);
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<Role> getAllRoles() {
		return roleMapper.getAllRolesByParams();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Role updateRole(Role role) {
		Assert.notNull(role, "角色不能为空");
		Assert.notNull(role.getAuthority(), "角色名称不能为空");
		Assert.notNull(role.getAlias(), "角色别名不能为空");
		Assert.notNull(role.getType(), "角色类别不能为空");
		Role source = getRoleById(role.getAuthority());
		try {
			BeanUtils.copyProperties(source, role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		roleMapper.updateRole(source);
		return source;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Role saveRole(Role role){
		Assert.notNull(role, "角色不能为空");
		Assert.notNull(role.getAuthority(), "角色名称不能为空");
		Assert.notNull(role.getAlias(), "角色别名不能为空");
		Assert.notNull(role.getType(), "角色类别不能为空");
		role.setCreatetime(new Date());
		String authority = role.getAuthority().toUpperCase();
		if(!authority.startsWith(PREFIX)){
			authority = PREFIX + authority;
		}
		role.setAuthority(authority);
		roleMapper.insertRole(role);
		role = getRoleById(role.getAuthority());
		return role;
	}
	

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Role getRoleById(String id) {
		Assert.notNull(id, "角色编号不能为空");
		return roleMapper.getRoleById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteRoleById(String id){
		Role role = getRoleById(id);
		Assert.notNull(id, "角色不存在或已经被删除");
		if (role != null) {
			roleMapper.deleteUserRoleById(id);
			roleMapper.deleteResourceRoleById(id);
			roleMapper.deleteRoleById(id);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<Role> getAllRolesByType(String type) {
		return roleMapper.getAllRolesByType(type);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<RoleAuthority> getAuthorization(String id) {
		return roleMapper.getAuthorization(id);
	}

	@Override
	public String getIndexByRole(String authority) {
		return roleMapper.getIndexByRole(authority);
	}

}
