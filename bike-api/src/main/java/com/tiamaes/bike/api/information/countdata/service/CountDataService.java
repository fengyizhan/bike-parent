package com.tiamaes.bike.api.information.countdata.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiamaes.bike.api.information.countdata.bean.CountData;
import com.tiamaes.bike.api.information.countdata.persistence.CountDataMapper;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CountDataService implements CountDataServiceInterface {
	
	@Resource
	private CountDataMapper countDataMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public CountData getDataOfCountData() {
		int bikeNum = countDataMapper.getTotalOfBikes();
		int parkNum = countDataMapper.getTotalOfParks();
		int userNum = countDataMapper.getTotalOfUsers();
		return new CountData(bikeNum, parkNum, userNum);
	}

}
