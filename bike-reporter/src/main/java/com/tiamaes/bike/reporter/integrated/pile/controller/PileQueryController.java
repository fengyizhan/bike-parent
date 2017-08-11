package com.tiamaes.bike.reporter.integrated.pile.controller;

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
import com.tiamaes.bike.common.bean.integrated.PileRecord;
import com.tiamaes.bike.reporter.integrated.pile.service.PileQueryServiceInterface;
import com.tiamaes.mybatis.PageInfo;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/integrated/pile")
@Api(tags = {"pileQuery"}, description = "锁桩报警记录查询")
public class PileQueryController {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(PileQueryController.class);
	@Resource
	private PileQueryServiceInterface pileQueryService;
	
	@ApiOperation(value = "锁桩报警分页信息", notes = "获取当前页的锁桩报警列表数据")
	@ApiImplicitParam(name = "pileRecord", value = "锁桩报警记录实体", required = true, dataType = "PileRecord")
	@RequestMapping(value = {"/page/{number:\\d+}","/page/{number:\\d+}/{totalItems:\\d+}"}, method = { RequestMethod.GET, RequestMethod.POST }, produces = {"application/json" })
	public @ResponseBody PageInfo<PileRecord> page(@RequestBody PileRecord pileRecord, @PathVariable Map<String,String> pathVariable, @CurrentUser User operator) {
		int number = pathVariable.get("number") == null ? 1 : Integer.parseInt(pathVariable.get("number"));
		int pageSize = pathVariable.get("pageSize") == null ? 20 : Integer.parseInt(pathVariable.get("pageSize"));
		Pagination<PileRecord> pagination = new Pagination<>(number, pageSize);
		Parameters<PileRecord> parameters = new Parameters<>();
		parameters.setUser(operator);
		parameters.setModel(pileRecord);
		
		List<PileRecord> list = pileQueryService.getListOfPileRecords(parameters, pagination);
		return new PageInfo<>(list);
	}
	
}
