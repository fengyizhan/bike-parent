package com.tiamaes.bike.api.information.authority.service;

import java.util.List;

import com.tiamaes.bike.common.bean.information.StatusVehicleInfo;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.system.Authority;
import com.tiamaes.bike.common.bean.system.DataAuthority;
import com.tiamaes.security.core.userdetails.User;

public interface AuthorityServiceInterface{

	/**
	 * 查询该用户权限下的车辆树，并获取每辆车的实时信息
	 * @param user
	 * @return
	 */
	Authority queryUserDataAuthoritiesTree(User user);
	/**
	 * 增加数据权限
	 * @param dataAuthority
	 */
	void saveUserAuthorities(DataAuthority dataAuthority);
	/**
	 * 删除数据权限
	 * @param dataAuthority
	 */
	void deleteUserAuthorities(DataAuthority dataAuthority);

	/**
	 * 根据车辆id查询实时司机，车辆详情
	 * @param vehicleId
	 * @return
	 */
	Vehicle getDetails(Vehicle vehicle);
	/**
	 * 根据username查询所有公司下的车辆树，包括公司和车队
	 * @param user
	 * @return
	 */
	Authority queryAuthoritiesTree(String username);
	/**
	 * 获取子区域
	 * @return
	 */
	public abstract List<Authority> getChildren(DataAuthority dataAuthority);
	/**
	 * 获取父区域
	 * @return
	 */
	public abstract List<Authority> getParents(DataAuthority dataAuthority);
	
	/**
	 * 获取地图不同状态车的信息
	 * @param user
	 * @return
	 */
	StatusVehicleInfo queryUserDataVehicleStatus(User user);
	
	/**
	 * 根据车辆编号查找车辆状态
	 * @param vehicleName 
	 * @param user
	 * @return
	 */
	Vehicle queryVehicleStatus(String vehicleName, User user);
}
