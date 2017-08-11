/**
 * 
 */
package com.tiamaes.bike.api.authority.resource.service;

import java.util.List;

import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.bike.common.bean.system.Resource;



/**
 * @author Chen
 *
 */
public interface ResourceServiceInterface {
	/**
	 * Spring security启动时,加载系统所有资源
	 * @return
	 */
	public abstract void loadSystemResources();
	/**
	 * 查询资源列表
	 * @return
	 */
	public abstract List<Resource> getAllResources();
	/**
	 * 获取系统资源
	 * @return
	 */
	public abstract List<Resource> getChildren();
	/**
	 * 获取系统资源
	 * @return
	 */
	public abstract List<Resource> getChildren(String id);
	/**
	 * 获取系统资源
	 * @return
	 */
	public abstract List<Resource> getParents(String id);
	/**
	 * 根据用户角色获取用户门户菜单
	 * @param user
	 * @return
	 */
	public abstract List<Resource> getNavigation(User user);
	/**
	 * 根据编号查询资源信息
	 * @param id
	 * @return
	 */
	public abstract Resource getResourceById(String id);
	/**
	 * 根据URI查询资源编号
	 * @param URI
	 * @return
	 */
	public abstract String getResourceByURI(String URI);
	/**
	 * 创建新的资源
	 */
	public abstract Resource addResource(Resource resource);
	/**
	 * 删除资源
	 * @param id
	 * @throws Exception
	 */
	public abstract void deleteResource(String id);
	/**
	 * 更新资源
	 * @param resource
	 * @throws Exception
	 */
	public abstract Resource updateResource(Resource resource);
	/**
	 * 保存角色授权
	 * @param roleId
	 * @param auths
	 */
	public abstract void saveAuthorization(String roleId, String resourceId);
	/**
	 * 删除角色授权
	 * @param roleId
	 * @param auths
	 */
	public abstract void deleteAuthorization(String roleId, String resourceId);
}
