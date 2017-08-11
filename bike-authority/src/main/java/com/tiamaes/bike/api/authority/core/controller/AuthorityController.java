package com.tiamaes.bike.api.authority.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.bike.api.authority.core.service.AuthorityServiceInterface;
import com.tiamaes.bike.api.authority.resource.service.ResourceServiceInterface;
import com.tiamaes.bike.common.bean.system.PointVector;
import com.tiamaes.bike.common.bean.system.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = {"authority"}, description = "权限管理")
public class AuthorityController {
	
	@Autowired
	private AuthorityServiceInterface authorityService;
	@Autowired
	private ResourceServiceInterface resourceService;
	/**
	 * 获取系统菜单数据
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	@ApiOperation(value = "系统菜单", notes = "获取系统菜单数据")
	public @ResponseBody Map<String,Object> menu(@CurrentUser User operator) {
		List<Resource> resources = resourceService.getNavigation(operator);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("menus", resources);
		result.put("operator", operator);
		return result;
	}
	
	@RequestMapping(value = "/getFeatures", method = RequestMethod.GET)
	@ApiOperation(value = "监控界面用户权限", notes = "获取监控模块左侧权限数据")
	public @ResponseBody List<PointVector> getFeatures(@CurrentUser User operator) {
		if(operator != null){
			List<PointVector> features = authorityService.queryAllPointVector(operator);
			return features;
		}
		return new ArrayList<PointVector>();
	}
}
