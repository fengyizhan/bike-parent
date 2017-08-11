package com.tiamaes.bike.api.information.authority.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.bike.api.information.authority.service.AuthorityServiceInterface;
import com.tiamaes.bike.common.bean.system.Authority;
import com.tiamaes.bike.common.bean.system.DataAuthority;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/information/authority")
@Api(tags = {"authority"}, description = "用户数据权限")
public class AuthorityController {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(AuthorityController.class);
	@Resource
	private AuthorityServiceInterface authorityService;
	
	/**
	 * 根据用户查询所有公司和车队并形成树 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = {"/authoritiesTree/{username}"}, method = {RequestMethod.GET})
	@ApiOperation(value = "查询集中停放区或车", notes = "根据用户查询所有集中停放区或车并形成树 ")
	public Authority queryAuthoritiesTree(@PathVariable String username){
		Assert.notNull(username,"用户名不能为空");
		return authorityService.queryAuthoritiesTree(username);
	}
	
	/**
	 * 根据用户保存区域
	 * @param id
	 * @param resourceid
	 * @param operator
	 */
	@RequestMapping(value = "/authoritiesTree/{username}/{areaId}/{areaLevel}", method = RequestMethod.PUT, produces = { "application/json" })
	@ApiOperation(value = "保存用户区域", notes = "根据用户保存区域") 
	public @ResponseBody void saveArea(@PathVariable("username") String username, 
			@PathVariable("areaId") String areaId, @PathVariable("areaLevel") int areaLevel,
			@CurrentUser User operator) {
		Assert.notNull(username,"用户名称不能为空");
		Assert.notNull(areaId,"区域id不能为空");
		Assert.notNull(areaLevel,"区域级别不能为空");
		
		DataAuthority dataAuthority = new DataAuthority();
		dataAuthority.setUserId(username);
		dataAuthority.setTargetId(areaId);
		dataAuthority.setLevels(areaLevel);
		
		authorityService.saveUserAuthorities(dataAuthority);
	}
	
	/**
	 * 根据用户删除区域
	 * @param id
	 * @param resourceid
	 * @param operator
	 */
	@RequestMapping(value = "/authoritiesTree/{username}/{areaId}/{areaLevel}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ApiOperation(value = "删除用户区域", notes = "根据用户删除区域")
	public @ResponseBody void deleteArea(@PathVariable("username") String username, 
			@PathVariable("areaId") String areaId, @PathVariable("areaLevel") int areaLevel, 
			@CurrentUser User operator) {
		Assert.notNull(username,"用户名不能为空");
		Assert.notNull(areaId,"区域id不能为空");
		Assert.notNull(areaLevel,"区域级别不能为空");
		
		DataAuthority dataAuthority = new DataAuthority();
		dataAuthority.setUserId(username);
		dataAuthority.setTargetId(areaId);
		dataAuthority.setLevels(areaLevel);
		authorityService.deleteUserAuthorities(dataAuthority);
	}
	
}
