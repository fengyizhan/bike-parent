package com.tiamaes.bike.reporter.information.countdatalog.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.bike.reporter.information.countdatalog.bean.CountDataLog;
import com.tiamaes.bike.reporter.information.countdatalog.service.CountDataLogServiceInterface;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/information/countdata")
@Api(tags = {"countdata"}, description = "首页单车日志数据总计记录")
public class CountDataLogController {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(CountDataLogController.class);
	
	@Resource
	private CountDataLogServiceInterface countDataService;
	
	/**
	 * 获取首页单车日志数据总计记录
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ApiOperation(value = "单车总计记录", notes = "获取首页单车日志数据总计记录")
	public @ResponseBody CountDataLog getDataOfCountDataLog(@CurrentUser User operator) {
		return countDataService.getDataOfCountDataLog();
	}
}
