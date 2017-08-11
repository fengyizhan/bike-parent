package com.tiamaes.bike.storage.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.connector.ParkStatusInfo;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.storage.persistence.ParkStatusInfoMapper;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ParkStatusInfoService implements ParkStatusInfoServiceInterface {
	
	@Autowired
	private ParkStatusInfoMapper parkStatusInfoMapper;
	@Resource
	private ApiFeignSerivceInterface apiFeignSerivce;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addParkStatusInfo(ParkStatusInfo parkStatusInfo) {
		Assert.notNull(parkStatusInfo, "停放区状态信息不能为空");
		Park park = parkStatusInfo.getPark();
		String id = park.getId();
		Assert.notNull(id, "停放区id不能为空");
//		apiFeignSerivce.update(park, id);
		parkStatusInfo.setId(UUIDGenerator.getUUID());
		parkStatusInfo.setCreateDate(new Date());
		parkStatusInfoMapper.addParkStatusInfo(parkStatusInfo);
	}

}
