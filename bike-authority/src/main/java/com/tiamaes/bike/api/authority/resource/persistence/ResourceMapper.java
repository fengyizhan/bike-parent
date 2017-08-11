package com.tiamaes.bike.api.authority.resource.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.system.Resource;
import com.tiamaes.bike.common.bean.system.SecurityConfigs;
	
/**
 * @author Chen
 *
 */
public interface ResourceMapper {
	/**
	 * 查询系统所有资源
	 * @return
	 */
	List<SecurityConfigs> loadSystemResources();
	/**
	 * 查询系统资源列表
	 * @return
	 */
	List<Resource> getAllResources();
	/**
	 * 查询下级资源
	 * @param id
	 * @return
	 */
	List<Resource> getChildren(@Param("id")String id);
	/**
	 * 根据用户角色获取系统菜单
	 * @param userId
	 * @return
	 */
	List<Resource> getNavigation(String userId);
	/**
	 * 根据编号查询资源信息
	 * @param id
	 * @return
	 */
	Resource getResourceById(String id);
	/**
	 * 新建资源
	 * @param resource
	 */
	void addResource(Resource resource);
	/**
	 * 删除资源
	 * @param id
	 */
	void deleteResource(String id);
	/**
	 * 删除资源关系的角色列表
	 * @param id
	 */
	void deleteRolesResource(String id);
	/**
	 * 是否包含下级资源
	 * @param id
	 * @return
	 */
	boolean hasChildren(String id);
	/**
	 * 修改资源
	 * @param resource
	 */
	void updateResource(Resource resource);
	/**
	 * 根据角色编号获取授权信息
	 * @param roleId
	 * @return
	 */
	List<String> getAuthorization(String roleId);
	/**
	 * 保存角色授权
	 * @param authority
	 * @param resources
	 */
	void saveAuthorization(@Param("authority")String authority, @Param("resourceid")String resourceid);
	/**
	 * 删除角色授权
	 * @param authority
	 * @param resources
	 */
	void deleteAuthorization(@Param("authority")String authority, @Param("resourceid")String resourceid);
	/**
	 * 根据URI查询资源
	 * @param uRI
	 * @return
	 */
	Resource getResourceByURI(String URI);
	
	boolean hasExists(@Param("authority")String authority, @Param("resourceid")String resourceid);
}
