package com.tiamaes.bike.api.authority.role.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.mybatis.PageInfo;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.core.DefaultGrantedAuthority;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.bike.api.authority.resource.service.ResourceServiceInterface;
import com.tiamaes.bike.api.authority.role.service.RoleServiceInterface;
import com.tiamaes.bike.common.bean.system.Role;
import com.tiamaes.bike.common.bean.system.RoleAuthority;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/role")
@Api(tags = {"role"}, description = "角色管理")
public class RoleController {
	private static Logger logger = LogManager.getLogger(RoleController.class);
	@Autowired
	private RoleServiceInterface roleService;
	@Autowired
	private ResourceServiceInterface resourceService;

	@RequestMapping(value = "/types", method = RequestMethod.GET)
	@ApiOperation(value = "角色类型", notes = "获取角色的全部类型值数据")
	public @ResponseBody Role.Type[] types() {
		return Role.Type.values();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiOperation(value = "当前用户的验证信息", notes = "获取当前用户的验证信息数据")
	public @ResponseBody List<DefaultGrantedAuthority> list(@CurrentUser User operator) {
		return operator.getAuthorities();
	}

	@RequestMapping(value = { "/page/{number:\\d+}", "/page/{number:\\d+}/{pageSize:\\d+}" }, method = RequestMethod.POST)
	@ApiOperation(value = "角色分页信息", notes = "获取当前页的角色列表数据")
	@ApiImplicitParam(name = "parameters", value = "角色实体", required = true, dataType = "Role")
	public @ResponseBody PageInfo<Role> page(@RequestBody Role parameters, @PathVariable Map<String, String> pathVariable, @CurrentUser User operator) {
		int number = pathVariable.get("number") == null ? 1 : Integer.parseInt(pathVariable.get("number"));
		int pageSize = pathVariable.get("pageSize") == null ? 20 : Integer.parseInt(pathVariable.get("pageSize"));
		Pagination<Role> pagination = new Pagination<Role>(number, pageSize);
		List<Role> roles = roleService.getAllRoles(parameters, pagination);
		return new PageInfo<Role>(roles);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "获取指定角色", notes = "根据角色id获取角色", response = Role.class)
	public @ResponseBody Role getRoleById(@PathVariable("id") String id) {
		Role role = roleService.getRoleById(id);
		return role;
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "新增角色", notes = "增加新的角色", response = Role.class)
	@ApiImplicitParam(name = "parameters", value = "角色实体", required = true, dataType = "Role")
	public @ResponseBody Role saveRole(@RequestBody Role role, @CurrentUser User operator) {
		return roleService.saveRole(role);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "更新指定角色", notes = "根据角色id更新角色", response = Role.class)
	@ApiImplicitParam(name = "role", value = "角色实体", required = true, dataType = "Role")
	public @ResponseBody Role updateRole(@RequestBody Role role, @PathVariable("id") String id, @CurrentUser User operator) {
		return roleService.updateRole(role);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ApiOperation(value = "删除指定角色", notes = "根据角色id删除角色", response = Void.class)
	public @ResponseBody void deleteRole(@CurrentUser User operator, @PathVariable("id") String id) {
		if(logger.isDebugEnabled()){	
			logger.debug(id);
		}
		if(StringUtils.isNotBlank(id)){
			roleService.deleteRoleById(id);
		}
	}

	@RequestMapping(value = "/auth/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "获取指定角色的权限", notes = "根据角色id获取角色的权限信息数据")
	public @ResponseBody List<RoleAuthority> auth(@PathVariable("id") String id) {
		List<RoleAuthority> auths = roleService.getAuthorization(id);
		return auths;
	}

	@RequestMapping(value = "/auth/{id}/{resourceid}", method = RequestMethod.PUT)
	@ApiOperation(value = "保存指定角色的权限", notes = "根据角色id保存角色的权限信息数据", response = Void.class)
	public @ResponseBody void saveAuth(@PathVariable("id") String id, @PathVariable("resourceid") String resourceid,
			@CurrentUser User operator) {
		resourceService.saveAuthorization(id, resourceid);
	}

	@RequestMapping(value = "/auth/{id}/{resourceid}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除指定角色的权限", notes = "根据角色id删除角色的权限信息数据", response = Void.class)
	public @ResponseBody void deleteAuth(@PathVariable("id") String id, @PathVariable("resourceid") String resourceid,
			@CurrentUser User operator) {
		resourceService.deleteAuthorization(id, resourceid);
	}
	
	/**
	 * 根据角色获取首页路径
	 * 
	 * @param authority
	 * @return
	 */
	@RequestMapping(value = "/index/{authority}", method = RequestMethod.GET)
	@ApiOperation(value = "根据角色获取首页路径", notes = "根据角色获取首页路径", response = Role.class)
	public @ResponseBody Object getIndex(@PathVariable("authority") String authority) {
		return roleService.getIndexByRole(authority);
	}
}
