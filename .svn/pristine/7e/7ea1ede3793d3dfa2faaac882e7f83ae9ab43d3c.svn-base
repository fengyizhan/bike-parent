package com.tiamaes.bike.reporter.integrated.vehicle.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.Result;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.integrated.BorrowRecord;
import com.tiamaes.bike.common.bean.integrated.LocationRecord;
import com.tiamaes.bike.common.bean.system.PointVector.Center;
import com.tiamaes.bike.common.utils.GPSUtils;
import com.tiamaes.bike.reporter.integrated.borrow.service.BorrowQueryServiceInterface;
import com.tiamaes.bike.reporter.integrated.vehicle.service.VehicleLocationServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/location")
@Api(tags = {"vehicleLocation"}, description = "车辆定位记录查询")
public class VehicleLocationController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(VehicleLocationController.class);
	
	@Resource
	private VehicleLocationServiceInterface vehicleLocationService;
	@Resource
	private BorrowQueryServiceInterface borrowQueryService;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, Park> parkOperator;
	/**
	 * 获取某一租借行程车辆的定位信息
	 * @param location
	 * @return
	 */
	@RequestMapping(value = "/borrowRecordLocations/{borrowId}", method = RequestMethod.GET,produces = {"application/json"})
	@ApiOperation(value = "租借记录对应的行程车辆的定位信息", notes = "租借记录对应的行程车辆的定位信息")
	public @ResponseBody Result getLocations(@PathVariable("borrowId") String borrowId) {
		Result result=new Result();
		result.setSuccess(true);
		Map<String,Object> dataMap=result.getData();
		try 
		{
			BorrowRecord borrowRecord=borrowQueryService.getBorrowRecord(borrowId);
			Park startPark=borrowRecord.getStartPark();
			startPark=parkOperator.get(RedisKey.PARKS,String.valueOf(startPark.getId()));
			Map<String,Object> startParkInfo=new HashMap<String,Object>();
			startParkInfo.put("name",startPark.getName());
			startParkInfo.put("address",startPark.getAddress());
			startParkInfo.put("lat",startPark.getLat());
			startParkInfo.put("lng",startPark.getLng());
			Park endPark=borrowRecord.getEndPark();
			endPark=parkOperator.get(RedisKey.PARKS,String.valueOf(endPark.getId()));
			Map<String,Object> endParkInfo=new HashMap<String,Object>();
			endParkInfo.put("name",endPark.getName());
			endParkInfo.put("address",endPark.getAddress());
			endParkInfo.put("lat",endPark.getLat());
			endParkInfo.put("lng",endPark.getLng());
			dataMap.put("startPark",startParkInfo);
			dataMap.put("endPark",endParkInfo);
			List<Center> list = vehicleLocationService.getLocationByBorrow(borrowId);
			List<Center> paths = new ArrayList<Center>();
			if(list != null && list.size() > 0){
				for(Center center : list){
					double[] bd09 = GPSUtils.fromWGS84ToBD09(center.getLat(), center.getLng());
					center.setLat(bd09[0]);
					center.setLng(bd09[1]);
					paths.add(center);
				}
			}
			dataMap.put("paths",paths);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("加载行程详情异常！");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 获取某一时间段内车辆的定位信息
	 * @param location
	 * @return
	 */
	@RequestMapping(value = "/locations", method = RequestMethod.POST,produces = {"application/json"})
	@ApiOperation(value = "某段时间内车辆的定位信息", notes = "获取某一时间段内车辆的定位信息")
	@ApiImplicitParam(name = "locationRecord", value = "车辆定位记录实体", required = true, dataType = "LocationRecord")
	public @ResponseBody List<Center> getLocations(LocationRecord locationRecord) {
		List<Center> list = vehicleLocationService.getLocationByTime(locationRecord);
		List<Center> result = new ArrayList<Center>();
		if(list != null && list.size() > 0){
			for(Center center : list){
				double[] bd09 = GPSUtils.fromWGS84ToBD09(center.getLat(), center.getLng());
				center.setLat(bd09[0]);
				center.setLng(bd09[1]);
				result.add(center);
			}
		}
		return result;
	}
	
	/**
	 * 获取车辆历史定位信息
	 * @param locationRecord
	 * @return
	 */
	@RequestMapping(value = "/historyPosition", method = RequestMethod.POST)
	@ApiOperation(value = "车辆的历史定位信息", notes = "获取车辆历史定位信息")
	@ApiImplicitParam(name = "locationRecord", value = "车辆定位记录实体", required = true, dataType = "LocationRecord")
	public List<LocationRecord> getListOfHistoryPositionInfo(@RequestBody LocationRecord locationRecord) {
		return vehicleLocationService.getListOfHistoryPositionInfo(locationRecord);
	}
	
	/**
	 * 获取车辆实时定位信息
	 * @param locationRecord
	 * @return
	 */
	@RequestMapping(value = "/realtimePosition", method = RequestMethod.POST)
	@ApiOperation(value = "车辆的实时定位信息", notes = "获取车辆实时定位信息")
	@ApiImplicitParam(name = "locationRecord", value = "车辆定位记录实体", required = true, dataType = "LocationRecord")
	public List<LocationRecord> getListOfRealtimePositionInfo(@RequestBody LocationRecord locationRecord) {
		return vehicleLocationService.getListOfRealtimePositionInfo(locationRecord);
	}
}
