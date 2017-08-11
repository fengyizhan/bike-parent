package com.tiamaes.bike.reporter.integrated.alarm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.integrated.AlarmRecord;
import com.tiamaes.bike.common.bean.integrated.WarnCode;
import com.tiamaes.bike.reporter.integrated.alarm.service.AlarmQueryServiceInterface;
import com.tiamaes.mybatis.PageInfo;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/integrated/alarm")
@Api(tags = {"alarmQuery"}, description = "车辆报警记录查询")
public class AlarmQueryController {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(AlarmQueryController.class);
	@Resource
	private AlarmQueryServiceInterface alarmQueryService;
	
	/**
	 * 页面初始化方法
	 * @return
	 */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	@ApiOperation(value = "报警类型", notes = "获取车辆报警类型值数据")
	public @ResponseBody Map<String,Object> init() {
		Map<String,Object> result = new HashMap<>();
		result.put("warnCodes", WarnCode.values());
		return result;
	}
	
	@ApiOperation(value = "车辆报警分页信息", notes = "获取当前页的车辆报警列表数据")
	@ApiImplicitParam(name = "alarmRecord", value = "车辆报警记录实体", required = true, dataType = "AlarmRecord")
	@RequestMapping(value = {"/page/{number:\\d+}","/page/{number:\\d+}/{pageSize:\\d+}"}, method = { RequestMethod.GET, RequestMethod.POST }, produces = {"application/json" })
	public @ResponseBody PageInfo<AlarmRecord> page(@RequestBody AlarmRecord alarmRecord, @PathVariable Map<String,String> pathVariable,@CurrentUser User operator) {
		int number = pathVariable.get("number") == null ? 1 : Integer.parseInt(pathVariable.get("number"));
		int pageSize = pathVariable.get("pageSize") == null ? 20 : Integer.parseInt(pathVariable.get("pageSize"));
		Pagination<AlarmRecord> pagination = new Pagination<>(number, pageSize);
		Parameters<AlarmRecord> parameters = new Parameters<>();
		parameters.setUser(operator);
		parameters.setModel(alarmRecord);
		
		List<AlarmRecord> list = alarmQueryService.getListOfAlarmRecords(parameters, pagination);
		return new PageInfo<>(list);
	}
	
}
