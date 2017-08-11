package com.tiamaes.bike.wallet.information.deposit.controller;

import java.util.Date;
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

import com.tiamaes.bike.common.bean.wallet.Deposit;
import com.tiamaes.bike.common.bean.wallet.Deposit.Type;
import com.tiamaes.bike.wallet.information.deposit.service.DepositServiceInterface;
import com.tiamaes.mybatis.PageInfo;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/information/deposit")
@Api(tags = {"deposit"}, description = "用户押金信息管理")
public class DepositController {
	
	private static Logger logger = LogManager.getLogger(DepositController.class);
	@Resource
	private DepositServiceInterface depositService;
	
	/**
	 * 用户押金种类下拉列表
	 * @return
	 */
	@ApiOperation(value = "用户押金种类列表初始化", notes = "用户押金种类下拉列表初始化")
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> init() {
		Map<String,Object> result = new HashMap<>();
		result.put("types", Type.values());
		return result;
	}
	
	/**
	 * 根据参数获取用户押金列表信息
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "用户押金信息", notes = "根据用户名获取用户押金信息")
	@RequestMapping(value = {"query/page/{number:\\d+}","query/page/{number:\\d+}/{pageSize:\\d+}"}, method = RequestMethod.POST)
	public @ResponseBody PageInfo<Deposit> getListOfDeposit(@RequestBody Deposit deposit, @PathVariable Map<String,String> pathVariable, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){
			logger.debug(deposit);
		}
		int number = pathVariable.get("number") == null ? 1 : Integer.parseInt(pathVariable.get("number"));
		int pageSize = pathVariable.get("pageSize") == null ? 20 : Integer.parseInt(pathVariable.get("pageSize"));
		Pagination<Deposit> pagination = new Pagination<>(number, pageSize);
		List<Deposit> deposits = depositService.getListOfDeposits(deposit, pagination);
		return new PageInfo<Deposit>(deposits);
	}
	
	/**
	 * 新增用户押金信息
	 * @param deposit
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "新增用户押金信息", notes = "新增用户押金信息",response = Deposit.class)
	@ApiImplicitParam(name = "deposit", value = "用户押金实体", required = true, dataType = "Deposit")
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody Deposit add(@RequestBody Deposit deposit, @CurrentUser User operator){
		if(logger.isDebugEnabled()){
			logger.debug(deposit.toString());
		}
		deposit.setId(depositService.getId());
		deposit.setCreateDate(new Date());
		deposit = depositService.addDeposit(deposit);
		return deposit;
	}
	
	/**
	 * 更新用户押金信息
	 * @param deposit
	 * @param username
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "更新用户押金信息", notes = "更新用户押金信息",response = Deposit.class)
	@ApiImplicitParam(name = "deposit", value = "用户押金实体", required = true, dataType = "Deposit")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Deposit update(@RequestBody Deposit deposit, @PathVariable("id")Integer id, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){	
			logger.debug(deposit.toString());
		}
		deposit.setId(id);
		deposit = depositService.updateDeposit(deposit);
		return deposit;
	}
	
}
