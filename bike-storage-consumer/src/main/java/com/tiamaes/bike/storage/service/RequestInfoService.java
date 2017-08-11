package com.tiamaes.bike.storage.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiamaes.bike.common.bean.connector.RequestInfo;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.storage.persistence.RequestInfoMapper;

@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class RequestInfoService implements RequestInfoServiceInterface {
	
	@Resource
	private RequestInfoMapper requestInfoMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean saveRequestInfo(RequestInfo requestInfo) {
		requestInfo.setId(UUIDGenerator.getUUID());
		requestInfo.setRequestTime(new Date());
		int insertCount = requestInfoMapper.addRequestInfo(requestInfo);
		if(insertCount > 0) {
			return true;
		} else {
			return false;
		}
	}
}
