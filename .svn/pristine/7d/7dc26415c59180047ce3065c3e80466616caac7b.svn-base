package com.tiamaes.bike.api.information.driver.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.api.information.driver.persistence.DriverMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.bike.common.bean.system.User.Stage;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class DriverService implements DriverServiceInterface {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(DriverService.class);
	@Resource
	private DriverMapper driverMapper;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, User> operator;
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public User getDriverByIccardNumber(String icCardNumber) {
		Assert.notNull(icCardNumber, "用户IC卡号不能为空!");
		User user = driverMapper.getDriverByIccardNumber(icCardNumber);
		return user;
	}
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public User getDriverByUsername(String username) {
		Assert.notNull(username, "用户名不能为空!");
		User user = operator.get(RedisKey.DRIVERS, username);
		if (user == null) {
			user = driverMapper.getDriverByUsername(username);
			if (user != null) {
				operator.putIfAbsent(RedisKey.DRIVERS, user.getUsername(), user);
			}
		}
		return user;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<User> getListOfDrivers(User user, Pagination<User> pagination) {
		Assert.notNull(pagination, "分页对象不能为空");
		PageHelper.startPage(pagination);
		return driverMapper.getListOfDrivers(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User addDriver(User user) {
		Assert.notNull(user);
		//Assert.notNull(user.getIdentityCard(), "用户身份证号信息不能为空");
		User existed=driverMapper.getDriverByUsername(user.getUsername());
		if(existed==null)
		{
			driverMapper.addDriver(user);
		}
		User result = driverMapper.getDriverByUsername(user.getUsername());
		if (result != null) {
			operator.putIfAbsent(RedisKey.DRIVERS, user.getUsername(), user);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User updateDriverNickname(User user) {
		Assert.notNull(user);
		driverMapper.updateDriverNickname(user);
		User result = driverMapper.getDriverByUsername(user.getUsername());
		if (result != null) {
			operator.put(RedisKey.DRIVERS, user.getUsername(), user);
		}
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User updateDriverState(String username) {
		driverMapper.updateDriverState(username);
		User result = driverMapper.getDriverByUsername(username);
		if (result != null) {
			operator.put(RedisKey.DRIVERS, username, result);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteDriver(User user) {
		Assert.notNull(user, "用户信息不能为空!");
		driverMapper.deleteDriver(user);
		User result = driverMapper.getDriverByUsername(user.getUsername());
		if (result == null) {
			operator.delete(RedisKey.DRIVERS, user.getUsername());
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean checkIdentityCard(String identityCard) {
		int result = driverMapper.checkIdentityCard(identityCard); 
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User updateDriverStage(User user) {
		Assert.notNull(user, "用户信息不能为空!");
		driverMapper.updateDriverStage(user);
		User result = driverMapper.getDriverByUsername(user.getUsername());
		if (result != null) {
			operator.put(RedisKey.DRIVERS, user.getUsername(), result);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User certification(User user) {
		Assert.notNull(user, "用户信息不能为空!");
		user.setStage(Stage.CERTIFICATED);//已认证状态
		driverMapper.certification(user);
		User result = driverMapper.getDriverByUsername(user.getUsername());
		if (result != null) {
			operator.put(RedisKey.DRIVERS, user.getUsername(), result);
		}
		return result;
	}

}
