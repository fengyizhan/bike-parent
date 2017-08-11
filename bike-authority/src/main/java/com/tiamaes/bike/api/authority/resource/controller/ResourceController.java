package com.tiamaes.bike.api.authority.resource.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.bike.api.authority.resource.service.ResourceServiceInterface;
import com.tiamaes.bike.common.bean.system.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/resource")
@Api(tags = {"resource"}, description = "资源管理")
public class ResourceController {
	@Autowired
	private ResourceServiceInterface resourceService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiOperation(value = "当前用户的资源列表", notes = "获取当前用户的系统资源列表数据")
	public @ResponseBody List<Resource> list(@CurrentUser User operator) {
		List<Resource> resources = resourceService.getChildren();
		return resources;
	}

	@RequestMapping(value = "/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "全部资源列表", notes = "获取系统全部资源数据")
	public @ResponseBody List<Resource> page(@CurrentUser User operator) {
		List<Resource> resources = resourceService.getAllResources();
		return resources;
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "增加资源", notes = "根据Resource实体增加资源", response = Resource.class)
	@ApiImplicitParam(name = "resource", value = "资源实体", required = true, dataType = "Resource")
	public @ResponseBody Resource add(@RequestBody Resource resource) {
		return resourceService.addResource(resource);
	}

	@RequestMapping(value = "/child/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "增加子资源", notes = "根据Resource实体增加子资源", response = Resource.class)
	@ApiImplicitParam(name = "resource", value = "资源实体", required = true, dataType = "Resource")
	public @ResponseBody Resource child(@RequestBody Resource resource, @PathVariable("id") String id) {
		resource.setParentId(id);
		return resourceService.addResource(resource);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除指定资源", notes = "根据资源id删除资源", response = Void.class)
	public @ResponseBody void delete(@PathVariable("id") String id) {
		resourceService.deleteResource(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "获取指定资源", notes = "根据资源id获取资源", response = Resource.class)
	public @ResponseBody Resource get(@PathVariable("id") String id) {
		if (StringUtils.isNotBlank(id)) {
			Resource resource = resourceService.getResourceById(id);
			if (resource != null) {
				return resource;
			}
		}
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "更新指定资源", notes = "根据资源id更新资源", response = Resource.class)
	@ApiImplicitParam(name = "resource", value = "资源实体", required = true, dataType = "Resource")
	public @ResponseBody Resource update(@RequestBody Resource resource, @PathVariable("id") String id) {
		resource.setId(id);
		return resourceService.updateResource(resource);
	}
}
