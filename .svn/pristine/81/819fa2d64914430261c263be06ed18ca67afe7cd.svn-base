package com.tiamaes.bike.api.authority.core.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.bike.api.authority.core.persistence.AuthorityMapper;
import com.tiamaes.bike.common.bean.system.Authority;
import com.tiamaes.bike.common.bean.system.District;
import com.tiamaes.bike.common.bean.system.PointVector;

@Service
public class AuthorityService implements AuthorityServiceInterface {

	@Resource
	private AuthorityMapper authorityMapper;
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<PointVector> queryAllPointVector(User user) {
		return authorityMapper.queryAllPointVector(user);
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Authority> queryUserDataAuthorities(User user) {
		return authorityMapper.queryUserDataAuthorities(user);
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Authority> queryUserDataAuthoritiesWithTree(User user) {
		return authorityMapper.queryUserDataAuthoritiesWithTree(user);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void saveUserAuthorities(Authority dataAuthority, User user) {
		authorityMapper.saveUserAuthorities(dataAuthority, user);
		
		if(dataAuthority.getLevel() > 0){
			List<District> districts = authorityMapper.getAllHigherDistrict(dataAuthority);
			if(dataAuthority.getLevel() == 4){
				if(districts != null && districts.size() > 0){
					for(int i = 0; i < districts.size(); i++){
						District district = districts.get(i);
						boolean state = false;
						if(i == 0){
							state = authorityMapper.hasAllCheckedLowerStations(district, user);
						}else{
							state = authorityMapper.hasAllCheckedLowerDistricts(district, user);
						}
						if(state){
							authorityMapper.updateAllCheckedState(district, user);
						}else{
							break;
						}
					}
				}
			}else{
				for(int i = 0; i < districts.size(); i++){
					District district = districts.get(i);
					if(authorityMapper.hasAllCheckedLowerDistricts(district, user)){
						authorityMapper.updateAllCheckedState(district, user);
					}else{
						break;
					}
				}
			}
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteUserAuthorities(Authority dataAuthority, User user) {
		authorityMapper.deleteUserAuthorities(dataAuthority, user);
		
		if(dataAuthority.getLevel() > 0){
			List<District> districts = authorityMapper.getAllHigherDistrict(dataAuthority);
			if(dataAuthority.getLevel() == 4){
				for(int i = 0; i < districts.size(); i++){
					District district = districts.get(i);
					boolean state = false;
					if(i == 0){
						state = authorityMapper.hasAllUnCheckedLowerStations(district, user);
					}else{
						state = authorityMapper.hasAllUnCheckedLowerDistricts(district, user);
					}
					if(state){
						authorityMapper.deleteUnCheckedState(district, user);
					}else{
						authorityMapper.updateAllUnCheckedState(district, user);
					}
				}
			}else{
				for(int i = 0; i < districts.size(); i++){
					District district = districts.get(i);
					if(authorityMapper.hasAllUnCheckedLowerDistricts(district, user)){
						authorityMapper.deleteUnCheckedState(district, user);
					}else{
						authorityMapper.updateAllUnCheckedState(district, user);
					}
				}
			}
		}
	}

}
