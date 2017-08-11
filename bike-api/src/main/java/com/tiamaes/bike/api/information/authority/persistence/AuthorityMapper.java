package com.tiamaes.bike.api.information.authority.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.system.Authority;
import com.tiamaes.bike.common.bean.system.DataAuthority;
import com.tiamaes.security.core.userdetails.User;

public interface AuthorityMapper {
	
	/**
	 * 查询实时监控停放区树
	 * @param user
	 * @return
	 */
	List<Authority> queryParkTree(User user);
	
	/**
	 * 查询实时监控车辆树
	 * @param user
	 * @return
	 */
	List<Authority> queryVehicleTree(User user);
	
	/**
	 * 查询所有公司和车队
	 * @param user
	 * @return
	 */
	List<Authority> queryAuthoritiesTree(String id);
	
	/**
	 * 增加数据权限
	 * @param dataAuthority
	 */
	void saveDataAuthority(DataAuthority dataAuthority);
	
	/**
	 * 删除数据权限
	 * @param dataAuthority
	 */
	void deleteDataAuthority(DataAuthority dataAuthority);
	/**
	 * 查询下级区域
	 * @param id
	 * @return
	 */
	List<Authority> getChildren(@Param("id") String id);
	/**
	 * 检验数据库中是否包含父id的子区域
	 * @param id
	 * @return
	 */
	boolean hasChildren(@Param("id") String id);
	/**
	 * 检验数据库中是否已经存在该数据
	 * @param userId
	 * @param targetId
	 * @param levels
	 * @return
	 */
	boolean hasExists(@Param("userId") String userId,@Param("targetId") String targetId,@Param("levels") int levels);
	/**
	 * 检验该用户是否有权限
	 * @param dataAuthority
	 * @return
	 */
	boolean hasAuth(@Param("username") String username);

	/**
	 * 查全部的车辆
	 * @param user 
	 * @return
	 */
	List<Authority> queryAllAuthorities(User user);

	/**
	 * 查询指定车辆状态
	 * @param param
	 * @return
	 */
	Authority queryVehicleStatus(Parameters<Vehicle> param);
}
