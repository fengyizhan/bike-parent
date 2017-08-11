package com.tiamaes.bike.api.information.rate.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.bike.api.information.rate.service.RateServiceInterface;
import com.tiamaes.bike.common.bean.information.Rate;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.logger.Operation;
import com.tiamaes.logger.TiamaesLogger;
import com.tiamaes.mybatis.PageInfo;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/information/rate")
@Api(tags = {"rate"}, description = "费率信息管理")
public class RateController {
	
	private static Logger logger = LogManager.getLogger(RateController.class);
	@Resource
	private RateServiceInterface rateService;
	
	
	/**
	 * 页面展示费率列表信息
	 * @param rate
	 * @param pathVariable
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "费率列表", notes = "分页获取全部费率信息")
	@ApiImplicitParam(name = "rate", value = "费率实体", required = true, dataType = "Rate")
	@RequestMapping(value = {"/page/{number:\\d+}","/page/{number:\\d+}/{pageSize:\\d+}"}, method = RequestMethod.POST)
	public @ResponseBody PageInfo<Rate> page(@RequestBody Rate rate, @PathVariable Map<String,String> pathVariable, @CurrentUser User operator) {
		int number = pathVariable.get("number") == null ? 1 : Integer.parseInt(pathVariable.get("number"));
		int pageSize = pathVariable.get("pageSize") == null ? 20 : Integer.parseInt(pathVariable.get("pageSize"));
		Pagination<Rate> pagination = new Pagination<>(number, pageSize);
		List<Rate> list = rateService.getListOfRates(rate, pagination);
		return new PageInfo<Rate>(list);
	}
	
	/**
	 * 验证费率名字是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	@ApiOperation(value = "验证费率级别", notes = " 验证费率级别是否存在")
	@RequestMapping(value = {"/checklevel/{level}", "/checklevel/{level}/{id}"}, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> checkname(@PathVariable Optional<String> id, @PathVariable("level")Integer level) {
		Map<String, Object> result = new HashMap<>();
		result.put("state", rateService.checkRateLevel(id, level));
		return result;
	}
	
	/**
	 * 根据id获取费率信息
	 * @param id
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "费率信息", notes = "根据id获取费率信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Rate get(@PathVariable("id")String id,@CurrentUser User operator) {
		if(logger.isDebugEnabled()){
			logger.debug(id);
		}
		Rate rate = rateService.getRateById(id);
		return rate;
	}
	
	/**
	 * 新增费率信息
	 * @param rate
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "新增费率", notes = "新增费率信息",response = Rate.class)
	@ApiImplicitParam(name = "rate", value = "费率实体", required = true, dataType = "Rate")
	@TiamaesLogger(operation = Operation.ADD)
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody Rate add(@RequestBody Rate rate, @CurrentUser User operator){
		if(logger.isDebugEnabled()){
			logger.debug(rate.toString());
		}
		String rateId = UUIDGenerator.getUUID();
		rate.setId(rateId);
		rate.setCreateTime(new Date());
		rate = rateService.addRate(rate);
		return rate;
	}
	
	/**
	 * 更新费率信息
	 * @param rate
	 * @param id
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "更新费率", notes = "更新费率信息",response = Rate.class)
	@ApiImplicitParam(name = "rate", value = "费率实体", required = true, dataType = "Rate")
	@TiamaesLogger(operation = Operation.UPDATE)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Rate update(@RequestBody Rate rate, @PathVariable("id")String id, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){	
			logger.debug(rate.toString());
		}
		rate.setId(id);
		rate = rateService.updateRate(rate);
		return rate;
	}
	
	/**
	 * 删除费率信息
	 * @param id
	 * @param operator
	 */
	@ApiOperation(value = "删除费率", notes = "删除费率信息",response = Rate.class)
	@TiamaesLogger(operation = Operation.DELETE)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Rate delete(@PathVariable("id")String id, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){
			logger.debug(id);
		}
		Rate rate = rateService.getRateById(id);
		rateService.deleteRate(rate);
		return rate;
	}
	
	/**
	 * 获取已经存在的费率级别
	 * @return
	 */
	@ApiOperation(value = "获取费率级别", notes = "获取已经存在的费率级别")
	@RequestMapping(value = "/getListOfRateLevels", method = RequestMethod.GET)
	public List<Integer> getListOfRateLevels() {
		return rateService.getListOfRateLevels();
	}
	
	
}
