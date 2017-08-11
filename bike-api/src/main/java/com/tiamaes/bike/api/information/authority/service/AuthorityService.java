package com.tiamaes.bike.api.information.authority.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.api.information.authority.persistence.AuthorityMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.information.StatusVehicleInfo;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.system.Authority;
import com.tiamaes.bike.common.bean.system.DataAuthority;
import com.tiamaes.bike.common.bean.system.PointVector;
import com.tiamaes.bike.common.bean.system.PointVector.Info.RunState;
import com.tiamaes.bike.common.bean.system.PointVector.Info.State;

@Service
public class AuthorityService implements AuthorityServiceInterface {
 
	@Resource
	private AuthorityMapper authorityMapper;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, User> userOperator;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, Vehicle> vehicleOperator;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, PointVector> pointVectorOperator;
	
	private static String rootId = "af38f61578ef4a2ba837245a5386c9f5";
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Authority queryUserDataAuthoritiesTree(com.tiamaes.security.core.userdetails.User user) {
		
		//查询停放区树
		List<Authority> parks= authorityMapper.queryParkTree(user);
		
		Authority authority = new Authority();
		//增加根节点
		authority.setId(rootId);
		authority.setChecked(false);
		authority.setLevel(0);
		authority.setName("单车管理系统");
		authority.setOpen(true);
		
		List<Authority> cities = new ArrayList<Authority>();
		Authority city = new Authority();
		city.setId("410100");
		city.setName("郑州市");
		city.setLevel(1);
		
		if (parks != null && parks.size() > 0) {
			for (Authority park : parks) {
				List<Authority> vehicles = new ArrayList<>();
				List<Authority> childVehicles = (List<Authority>) park.getChildren();
				if (childVehicles != null && childVehicles.size() > 0) {
					for (Authority childVehicle : childVehicles) {
						if(StringUtils.isBlank(childVehicle.getId())){
							park.setIconSkin("close");
							continue;
						}
						Vehicle vehicle = new Vehicle();
						vehicle.setId(childVehicle.getId());
						vehicle.setName(childVehicle.getName());
						//获取车辆定位信息
						PointVector pointVector = pointVectorOperator.get(RedisKey.VEHICLES_POINTVECTORS, vehicle.getId());
						//车辆的定位信息
						if (pointVector != null) {
							//详细信息
							PointVector.Info info = pointVector.getInfo();
							//中心点不为空
							if(pointVector.getCenter()!=null){
								//中心点
								vehicle.setCenter(pointVector.getCenter());
							}
							if (info != null) {
								//当天趟次
								vehicle.setInfo(info);
								//车辆当前状态
								vehicle.setIconSkin(pointVector.getInfo().getState().getValue().toLowerCase());
							}
						}
						vehicles.add(vehicle);
					}
					park.setChildren(vehicles);
				}
			}
		}
		List<Authority> vehicles = new ArrayList<>();
		List<Authority> scatterVehicles= authorityMapper.queryVehicleTree(user);
		if (scatterVehicles != null && scatterVehicles.size() > 0) {
			for (Authority scatterVehicle : scatterVehicles) {
				if(StringUtils.isBlank(scatterVehicle.getId())){
					scatterVehicle.setIconSkin("close");
					continue;
				}
				Vehicle vehicle = new Vehicle();
				vehicle.setId(scatterVehicle.getId());
				vehicle.setName(scatterVehicle.getName());
				//获取车辆定位信息
				PointVector pointVector = pointVectorOperator.get(RedisKey.VEHICLES_POINTVECTORS, vehicle.getId());
				//车辆的定位信息
				if (pointVector != null) {
					//详细信息
					PointVector.Info info = pointVector.getInfo();
					//中心点不为空
					if(pointVector.getCenter()!=null){
						//中心点
						vehicle.setCenter(pointVector.getCenter());
					}
					if (info != null) {
						//当天趟次
						vehicle.setInfo(info);
						//车辆当前状态
						vehicle.setIconSkin(pointVector.getInfo().getState().getValue().toLowerCase());
					}
				}
				vehicles.add(vehicle);
			}
		}
		
		parks.addAll(vehicles);
		city.setChildren(parks);
		cities.add(city);
		authority.setChildren(cities);
		return authority;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void saveUserAuthorities(DataAuthority dataAuthority) {
		Authority authority = new Authority();
		//1.获取该区域对象
		//根节点
		if(0 == dataAuthority.getLevels()){
			authority.setId(rootId);
			authority.setLevel(0);
		}
		//公司节点
		if(1 == dataAuthority.getLevels()){
//			authority = companyMapper.getCompanyById(dataAuthority.getTargetId());
		}
		//车队节点
		if(2 == dataAuthority.getLevels()){
//			authority = departmentMapper.getDepartmentById(dataAuthority.getTargetId());
		}
		//2.获取该区域下是否有子区域集合
		List<Authority> children = getChildren(dataAuthority);
		children.add(authority);
		//4.判断该区域对象是否有父节点，如果有的话 查询出 并添加到 以上的子区域集合中
		List<Authority> parents = getParents(dataAuthority);
		if(parents!=null && parents.size()>0){
			children.addAll(parents);
		}
		//5.循环便利 区域集合，如果数据库中已经存在了  就不做处理，如果不存在就做插入操作
		if(children!=null && children.size()>0){
			for (Authority child : children) {
				if(child!=null){
					if(!authorityMapper.hasExists(dataAuthority.getUserId(),child.getId(),child.getLevel())){
						DataAuthority dataAuthorityTemp = new DataAuthority();
						dataAuthorityTemp.setLevels(child.getLevel());
						dataAuthorityTemp.setTargetId(child.getId());
						dataAuthorityTemp.setUserId(dataAuthority.getUserId());
						authorityMapper.saveDataAuthority(dataAuthorityTemp);
					}
				}
			}
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteUserAuthorities(DataAuthority dataAuthority) {
		Authority authority = new Authority();
		//1.获取该区域对象
		//根节点
		if(0 == dataAuthority.getLevels()){
			authority.setId(rootId);
			authority.setLevel(0);
		}
		//公司节点
		if(1 == dataAuthority.getLevels()){
//			authority = companyMapper.getCompanyById(dataAuthority.getTargetId());
		}
		//车队节点
		if(2 == dataAuthority.getLevels()){
//			authority = departmentMapper.getDepartmentById(dataAuthority.getTargetId());
		}
		//2.获取该区域下是否有子区域集合
		List<Authority> children = getChildren(dataAuthority);
		//3.将该区域对象加入子区域集合中
		children.add(authority);
		//3.循环执行删除
		for (Authority child : children) {
			DataAuthority dataAuthorityTemp = new DataAuthority();
			dataAuthorityTemp.setLevels(child.getLevel());
			dataAuthorityTemp.setTargetId(child.getId());
			dataAuthorityTemp.setUserId(dataAuthority.getUserId());
			authorityMapper.deleteDataAuthority(dataAuthorityTemp);
		}
		//4.如果删除的区域级别不是0，获取该区域的父区域id，根据父区域id查询该表中是否还有子区域，如果没有则删除父区域
		//如果是公司节点
		if(1 == dataAuthority.getLevels()){
			//查询数据库中是否存在级别为1的数据
			boolean exist = authorityMapper.hasExists(null, null, 1);
			
			if(!exist){
				DataAuthority dataAuthorityTemp = new DataAuthority();
				dataAuthorityTemp.setLevels(0);
				dataAuthorityTemp.setTargetId(rootId);
				dataAuthorityTemp.setUserId(dataAuthority.getUserId());
				authorityMapper.deleteDataAuthority(dataAuthorityTemp);
			}
		}
		//如果是车队节点
		if(2 == dataAuthority.getLevels()){
			//查询出父节点
			List<Authority> parents = getParents(dataAuthority);
			if(parents!=null && parents.size()>0){
				for (Authority parent : parents) {
					//遍历出父节点为公司的节点
					if(1==parent.getLevel()){
						//查询出该父节点下所有的车队
//						List<Department> departments = departmentMapper.getDepartmentsByCompanyId(parent.getId());
//						if(departments!=null && departments.size()>0){
//							int i = 0;
//							for (Department department : departments) {
//								boolean exist = authorityMapper.hasExists(dataAuthority.getUserId(), department.getId(), 2);
//								if(exist) 
//									break; 
//								else{
//									//该子节点全部不存在
//									i++;
//									if(i==departments.size()){
//										//删除父节点
//										DataAuthority dataAuthorityTemp = new DataAuthority();
//										dataAuthorityTemp.setLevels(parent.getLevel());
//										dataAuthorityTemp.setTargetId(parent.getId());
//										dataAuthorityTemp.setUserId(dataAuthority.getUserId());
//										authorityMapper.deleteDataAuthority(dataAuthorityTemp);
//										
//										//查询数据库中是否存在级别为1的数据
//										boolean existtemp = authorityMapper.hasExists(dataAuthority.getUserId(), null, 1);
//										
//										if(!existtemp){
//											DataAuthority dataAuthorityTemp2 = new DataAuthority();
//											dataAuthorityTemp2.setLevels(0);
//											dataAuthorityTemp2.setUserId(dataAuthority.getUserId());
//											dataAuthorityTemp2.setTargetId(rootId);
//											authorityMapper.deleteDataAuthority(dataAuthorityTemp2);
//										}
//									}
//								}
//							}
//						}
					}
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Vehicle getDetails(Vehicle vehicleTemp) {
		Assert.notNull(vehicleTemp,"车辆信息不能为空！");
		//获取车辆信息
		Vehicle vehicle = vehicleOperator.get(RedisKey.VEHICLES, vehicleTemp.getId());
		// 根据车辆id从缓存中获取车辆定位基本信息
		PointVector pointVector = pointVectorOperator.get(RedisKey.VEHICLES_POINTVECTORS, vehicleTemp.getId());
		vehicle.setCenter(pointVector.getCenter());
		vehicle.setInfo(pointVector.getInfo());
		
		//获取车辆定位信息
		User user = userOperator.get(RedisKey.VEHICLES_DRIVERS_VEHICLEID, vehicle.getId());
		
		String dayTripCount = (String) stringRedisTemplate.opsForHash().get(RedisKey.VEHICLES_TRIPS, vehicle.getId());
		//车辆的定位信息
		PointVector.Info info = vehicle.getInfo();
		//中心点不为空
		if(vehicle.getCenter()!=null){
			//中心点
			vehicle.setCenter(vehicle.getCenter());
		}
		if (info != null) {
			if(user!=null){
				//当前司机信息
				info.setUser(user);
			}
			if(StringUtils.isNotBlank(dayTripCount)){
				//当天趟次
				info.setDayTrip(Integer.valueOf(dayTripCount));
			}
			vehicle.setInfo(info);
			//车辆当前状态
			vehicle.setIconSkin(vehicleTemp.getInfo().getState().getValue().toLowerCase());
		}
		return vehicle;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Authority queryAuthoritiesTree(String username) {
		List<Authority> children =authorityMapper.queryAuthoritiesTree(username);
		//增加根节点
		Authority root = new Authority();
		root.setId(rootId);
		root.setChecked(authorityMapper.hasAuth(username));
		root.setChildren(children);
		root.setLevel(0);
		root.setName("渣土车监控平台");
		root.setOpen(true);
		return root;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Authority> getChildren(DataAuthority dataAuthority) {
		List<Authority> authoritys = new ArrayList<Authority>();
		//根节点
		if(0 == dataAuthority.getLevels()){
//			List<Company> companys = companyMapper.getCompanys();
//			if(companys!=null && companys.size()>0){
//				authoritys.addAll(companys);
//				for (Company company : companys) {
//					List<Department> departments = departmentMapper.getDepartmentsByCompanyId(company.getId());
//					if(departments!=null && departments.size()>0){
//						authoritys.addAll(departments);
//					}
//				}
//			}
		}
		//公司节点
		if(1 == dataAuthority.getLevels()){
//			List<Department> departments = departmentMapper.getDepartmentsByCompanyId(dataAuthority.getTargetId());
//			if(departments!=null && departments.size()>0){
//				authoritys.addAll(departments);
//			}
		}
		return authoritys;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Authority> getParents(DataAuthority dataAuthority) {
		List<Authority> authoritys = new ArrayList<Authority>();
		
		Authority root = new Authority();
		root.setId(rootId);
		root.setChecked(false);
		root.setLevel(0);
		root.setName("渣土车监控平台");
		root.setOpen(true); 
		authoritys.add(root);
		//公司节点
		if(1 == dataAuthority.getLevels()){
			authoritys.add(root);
		}
		//车队节点
		if(2 == dataAuthority.getLevels()){
//			Department department = departmentMapper.getDepartmentById(dataAuthority.getTargetId());
//			Company company = companyMapper.getCompanyById(department.getParentId());
//			authoritys.add(company);
//			authoritys.add(root);
		}
		return authoritys;
	}

	@Override
	public StatusVehicleInfo queryUserDataVehicleStatus(com.tiamaes.security.core.userdetails.User user) {
		List<Authority> allVehicles = authorityMapper.queryAllAuthorities(user);
		StatusVehicleInfo statusVehicleInfo = constructWithAllVehicles(allVehicles);
		return statusVehicleInfo;
	}

	private StatusVehicleInfo constructWithAllVehicles(List<Authority> allVehicles) {
		List<Vehicle> online = new ArrayList<>();
		List<Vehicle> offline = new ArrayList<>();
		List<Vehicle> lowPower = new ArrayList<>();
		List<Vehicle> using = new ArrayList<>();
		for (Authority authorityVehicle : allVehicles) {
			Vehicle vehicle = new Vehicle();
			vehicle.setId(authorityVehicle.getId());
			vehicle.setName(authorityVehicle.getName());
			PointVector pointVector = pointVectorOperator.get(RedisKey.VEHICLES_POINTVECTORS, vehicle.getId());
			if (pointVector != null) {
				PointVector.Info info = pointVector.getInfo();
				if(pointVector.getCenter()!=null){
					vehicle.setCenter(pointVector.getCenter());
				}
				if (info != null) {
					vehicle.setInfo(info);
					State state = info.getState();
					RunState runState = info.getRunState();
					vehicle.setIconSkin(state.getValue().toLowerCase());
					if (State.OFFLINE.equals(state)) {
						offline.add(vehicle);
					}
					if (State.ONLINE.equals(state) || State.LOWPOWER.equals(state)) {
						online.add(vehicle);
						if (RunState.BORROWED.equals(runState)) {
							using.add(vehicle);
						}
					}
					if (State.LOWPOWER.equals(state)) {
						lowPower.add(vehicle);
					}
				}
			}
		}
		return new StatusVehicleInfo(online, offline, lowPower, using);
	}

	@Override
	public Vehicle queryVehicleStatus(String vehicleName, com.tiamaes.security.core.userdetails.User user) {
		Parameters<Vehicle> param = new Parameters<>();
		Vehicle vehicle = new Vehicle();
		vehicle.setName(vehicleName);
		param.setUser(user);
		param.setModel(vehicle);
		Authority authority = authorityMapper.queryVehicleStatus(param);
		if (authority != null) {
			vehicle.setId(authority.getId());
			PointVector pointVector = pointVectorOperator.get(RedisKey.VEHICLES_POINTVECTORS, vehicle.getId());
			if (pointVector != null) {
				PointVector.Info info = pointVector.getInfo();
				if(pointVector.getCenter()!=null){
					vehicle.setCenter(pointVector.getCenter());
				}
				if (info != null) {
					vehicle.setInfo(info);
					State state = info.getState();
					vehicle.setIconSkin(state.getValue().toLowerCase());
				}
			}
		} else {
			vehicle = null;
		}
		return vehicle;
	}
}
