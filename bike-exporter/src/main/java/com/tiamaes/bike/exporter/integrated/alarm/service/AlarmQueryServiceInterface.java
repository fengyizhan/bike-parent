package com.tiamaes.bike.exporter.integrated.alarm.service;

import java.io.File;

import com.tiamaes.bike.common.bean.integrated.AlarmRecord;

/**
 * 车辆报警记录接口
 * @author kangty
 */
public interface AlarmQueryServiceInterface {
	
	/**
	 * 导出车辆报警记录列表
	 * @param alarmRecord
	 * @throws Exception
	 */
	File exportExcelOfAlarmRecords(AlarmRecord alarmRecord) throws Exception;
	
}
