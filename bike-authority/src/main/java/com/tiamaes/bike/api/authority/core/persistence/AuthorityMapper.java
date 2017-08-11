package com.tiamaes.bike.api.authority.core.persistence;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.bike.common.bean.system.Authority;
import com.tiamaes.bike.common.bean.system.District;
import com.tiamaes.bike.common.bean.system.PointVector;

public interface AuthorityMapper {
	
	List<Authority> queryUserDataAuthorities(User user);
	
	List<Authority> queryUserDataAuthoritiesWithTree(User user);
	
	void saveUserAuthorities(@Param("dataAuthority")Authority dataAuthority, @Param("user")User user);

	void deleteUserAuthorities(@Param("dataAuthority")Authority dataAuthority, @Param("user")User user);

	List<District> getAllHigherDistrict(Authority dataAuthority);

	boolean hasAllCheckedLowerStations(@Param("district")District district, @Param("user")User user);

	boolean hasAllCheckedLowerDistricts(@Param("district")District district, @Param("user")User user);

	void updateAllCheckedState(@Param("district")District district, @Param("user")User user);

	void deleteUnCheckedState(@Param("district")District district, @Param("user")User user);

	void updateAllUnCheckedState(@Param("district")District district, @Param("user")User user);

	boolean hasAllUnCheckedLowerStations(@Param("district")District district, @Param("user")User user);

	boolean hasAllUnCheckedLowerDistricts(@Param("district")District district, @Param("user")User user);
	
	List<PointVector> queryAllPointVector(User user);
}