package com.tiamaes.bike.connector;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.system.PointVector.Center;

import io.swagger.annotations.ApiOperation;

/**
 * 【停车区信息】 webservice远程调用存根
 * @author fyz
 *
 */
@FeignClient("bike-api")
public interface ParkServiceInterface {
	/**
	 * 停车区信息添加
	 * @param park
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/api/information/park")
	Park addPark(@RequestBody Park park);
	/**
	 * 停车区信息更新
	 * @param vehicle
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/api/information/park/{id}")
	Park updatePark(@RequestBody Park park, @PathVariable("id")String id);
	
	/**
	 * 停车区信息获取
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/information/park/{id}")
	Park getParkById(@PathVariable("id")String id);
	
	/**
	 * 获取坐标附近车辆或停放区信息
	 * @param center
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/api/information/park/aroundInfo")
	List<Park> getDataOfCountData(@RequestBody Center center);
}
