package com.tiamaes.bike.exporter.integrated.alarm.persistence;

import java.util.List;

import com.tiamaes.bike.common.bean.integrated.AlarmRecord;

public interface AlarmQueryMapper {
	
	/**
	 * 获取车辆报警记录数据
	 * @param alarmRecord
	 * @return
	 */
	List<AlarmRecord> getListOfAlarmRecords(AlarmRecord alarmRecord);
	
}
