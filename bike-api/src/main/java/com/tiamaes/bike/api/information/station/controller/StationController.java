package com.tiamaes.bike.api.information.station.controller;

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

import com.tiamaes.bike.api.information.station.service.StationServiceInterface;
import com.tiamaes.bike.common.bean.information.Station;
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
@RequestMapping("/information/station")
@Api(tags = {"station"}, description = "电子围栏信息管理")
public class StationController {
	
	private static Logger logger = LogManager.getLogger(StationController.class);
	@Resource
	private StationServiceInterface stationService;
	
	
	/**
	 * 页面展示电子围栏列表信息
	 * @param station
	 * @param pathVariable
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "电子围栏列表", notes = "分页获取全部电子围栏信息")
	@ApiImplicitParam(name = "station", value = "电子围栏实体", required = true, dataType = "Station")
	@RequestMapping(value = {"/page/{number:\\d+}","/page/{number:\\d+}/{pageSize:\\d+}"}, method = RequestMethod.POST)
	public @ResponseBody PageInfo<Station> page(@RequestBody Station station, @PathVariable Map<String,String> pathVariable, @CurrentUser User operator) {
		int number = pathVariable.get("number") == null ? 1 : Integer.parseInt(pathVariable.get("number"));
		int pageSize = pathVariable.get("pageSize") == null ? 20 : Integer.parseInt(pathVariable.get("pageSize"));
		Pagination<Station> pagination = new Pagination<>(number, pageSize);
		List<Station> list = stationService.getListOfStations(station, pagination);
		return new PageInfo<Station>(list);
	}
	
	/**
	 * 验证电子围栏名字是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	@ApiOperation(value = "验证电子围栏", notes = " 验证电子围栏名字是否存在")
	@RequestMapping(value = {"/checkname/{name}", "/checkname/{name}/{id}"}, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> checkname(@PathVariable Optional<Integer> id, @PathVariable("name")String name) {
		Map<String, Object> result = new HashMap<>();
		result.put("state", stationService.checkStationName(id, name));
		return result;
	}
	
	/**
	 * 根据id获取电子围栏信息
	 * @param id
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "电子围栏信息", notes = "根据id获取电子围栏信息")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Station get(@PathVariable("id")Integer id, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){
			logger.debug(id);
		}
		Station station = stationService.getStationById(id);
		return station;
	}
	
	/**
	 * 新增电子围栏信息
	 * @param station
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "新增电子围栏", notes = "新增电子围栏信息", response = Station.class)
	@ApiImplicitParam(name = "station", value = "电子围栏实体", required = true, dataType = "Station")
	@TiamaesLogger(operation = Operation.ADD)
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody Station add(@RequestBody Station station, @CurrentUser User operator){
		if(logger.isDebugEnabled()){
			logger.debug(station.toString());
		}
		int stationId = stationService.getId();
		station.setId(stationId);
		station.setCreateDate(new Date());
		station = stationService.addStation(station);
		return station;
	}
	
	/**
	 * 更新电子围栏信息
	 * @param station
	 * @param id
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "更新电子围栏", notes = "更新电子围栏信息", response = Station.class)
	@ApiImplicitParam(name = "station", value = "电子围栏实体", required = true, dataType = "Station")
	@TiamaesLogger(operation = Operation.UPDATE)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Station update(@RequestBody Station station, @PathVariable("id")Integer id, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){	
			logger.debug(station.toString());
		}
		station.setId(id);
		station = stationService.updateStation(station);
		return station;
	}
	
	/**
	 * 删除电子围栏信息
	 * @param id
	 * @param operator
	 */
	@ApiOperation(value = "删除电子围栏", notes = "删除电子围栏信息", response = Station.class)
	@TiamaesLogger(operation = Operation.DELETE)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Station delete(@PathVariable("id")Integer id, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){	
			logger.debug(id);
		}
		Station station = stationService.getStationById(id);
		stationService.deleteStation(station);
		return station;
	}
	
}
