package com.tiamaes.bike.api.authority.core.service;

import java.util.List;

import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.bike.common.bean.system.Authority;
import com.tiamaes.bike.common.bean.system.PointVector;

public interface AuthorityServiceInterface {

	List<Authority> queryUserDataAuthorities(User user);

	List<Authority> queryUserDataAuthoritiesWithTree(User user);

	void saveUserAuthorities(Authority dataAuthority, User user);

	void deleteUserAuthorities(Authority dataAuthority, User user);

	List<PointVector> queryAllPointVector(User user);
}
