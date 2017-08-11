package com.tiamaes.bike.reporter.integrated.alarm.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.integrated.AlarmRecord;
import com.tiamaes.bike.reporter.integrated.alarm.persistence.AlarmQueryMapper;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AlarmQueryService implements AlarmQueryServiceInterface {
	
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(AlarmQueryService.class);
	
	@Resource
	private AlarmQueryMapper alarmQueryMapper;
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<AlarmRecord> getListOfAlarmRecords(Parameters<AlarmRecord> parameters, Pagination<AlarmRecord> pagination) {
		Assert.notNull(pagination, "分页对象不能为空");
		PageHelper.startPage(pagination);
		return alarmQueryMapper.getListOfAlarmRecords(parameters);
	}

}
