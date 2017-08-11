/**
 * 
 */
package com.tiamaes.bike.api.authority.resource.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.bike.api.authority.resource.persistence.ResourceMapper;
import com.tiamaes.bike.common.bean.system.Resource;
import com.tiamaes.bike.common.utils.UUIDGenerator;



/**
 * @author Chen
 *
 */
@Service
public class ResourceService implements ResourceServiceInterface {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(ResourceServiceInterface.class);
	
	@javax.annotation.Resource
	private ResourceMapper resourceMapper;
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void loadSystemResources(){
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Resource> getAllResources() {
		return resourceMapper.getAllResources();
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Resource> getChildren() {
		return getChildren(null);
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Resource> getChildren(String id) {
		List<Resource> resources = resourceMapper.getChildren(id);
		if(resources != null && resources.size() > 0){
			for(Resource resource : resources){
				List<Resource> children = getChildren(resource.getId());
				if(children != null && children.size() > 0){
					resource.setChildren(children);
				}
			}
		}
		return resources;
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Resource> getParents(String id) {
		List<Resource> list = new ArrayList<Resource>();
		Resource resource = resourceMapper.getResourceById(id);
		list.add(resource);
		if(resource != null && resource.getParentId() != null){
			list.addAll(getParents(resource.getParentId()));
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<Resource> getNavigation(User user) {
		Assert.notNull(user, "parameter 'user' cannot be empty or null");
		List<Resource> resources = resourceMapper.getNavigation(user.getUsername());
		return resources;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public Resource addResource(Resource resource) {
		Assert.notNull(resource);
		Assert.notNull(resource.getName(), "资源名称不能为空");
		final String id = UUIDGenerator.getUUID();
		resource.setId(id);
		if(StringUtils.isBlank(resource.getParentId())){
			resource.setRank(0);
			resource.setParentId(null);
		}else{
			Resource parent = getResourceById(resource.getParentId());
			Assert.notNull(parent, "无效的上级资源编号");
			resource.setRank(parent.getRank() + 1);
		}
		
		if(StringUtils.isBlank(resource.getPath())){
			resource.setPath(null);
		}
		if(StringUtils.isBlank(resource.getIco())){
			resource.setIco(null);
		}
		resourceMapper.addResource(resource);
		return resource = resourceMapper.getResourceById(id);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteResource(String id){
		Assert.notNull(id, "要删除的资源编号不能为空");
		Resource resource = getResourceById(id);
		Assert.notNull(resource, "要删除的资源不存在或已经被删除");
		
		Assert.isTrue(resourceMapper.hasChildren(id) == false, "要删除的资源包含下级资源，不能删除!");
		
		resourceMapper.deleteResource(id);
		resourceMapper.deleteRolesResource(id);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public Resource updateResource(Resource resource){
		Assert.notNull(resource);
		Assert.notNull(resource.getId(), "资源编号不能为空");
		Assert.notNull(resource.getName(), "资源名称不能为空");
		Resource source = getResourceById(resource.getId());
		try {
			BeanUtils.copyProperties(source, resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resourceMapper.updateResource(resource);
		return resourceMapper.getResourceById(resource.getId());
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Resource getResourceById(String id) {
		Assert.notNull(id, "资源编号不能为空");
		Resource resource = resourceMapper.getResourceById(id);
		return resource;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void saveAuthorization(String roleId, String resourceId) {
		Assert.notNull(roleId, "角色编号不能为空");
		Assert.notNull(resourceId, "授权资源不能为空");
		
		Resource resource = getResourceById(resourceId);
		List<Resource> children = getChildren(resourceId);
		children.add(resource);
		if(resource.getParentId() != null){
			List<Resource> parents = getParents(resource.getParentId());
			children.addAll(parents);
		}
		for(Resource child : children){
			if(!resourceMapper.hasExists(roleId, child.getId())){
				resourceMapper.saveAuthorization(roleId, child.getId());
			}
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteAuthorization(String roleId, String resourceId) {
		Assert.notNull(roleId, "角色编号不能为空");
		Assert.notNull(resourceId, "授权资源不能为空");
		List<Resource> children = getChildren(resourceId);
		Resource resource = getResourceById(resourceId);
		children.add(resource);
		for(Resource child : children){
			resourceMapper.deleteAuthorization(roleId, child.getId());
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public String getResourceByURI(String uri) {
		Assert.notNull(uri, "uri不能为空");
		Resource resource = resourceMapper.getResourceByURI(uri);
		return resource != null ? resource.getId() : null;
	}
}
