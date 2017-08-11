package com.tiamaes.bike.reporter.information.countdatalog.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiamaes.bike.reporter.information.countdatalog.bean.CountDataLog;
import com.tiamaes.bike.reporter.information.countdatalog.persistence.CountDataLogMapper;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CountDataLogService implements CountDataLogServiceInterface {
	
	@Resource
	private CountDataLogMapper countDataLogMapper;

	@Override
	public CountDataLog getDataOfCountDataLog() {
		int orderNum = countDataLogMapper.getTotalOfOrders();
		double money = countDataLogMapper.getTotalOfMoneys();
		double avgTime = orderNum == 0 ? 0 : countDataLogMapper.getTotalOfTime()/orderNum;
		return new CountDataLog(orderNum, money, avgTime);
	}

}
