package com.tiamaes.bike.connector;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiamaes.bike.common.bean.information.Vehicle;

/**
 * 【车辆信息】 webservice远程调用存根
 * @author fyz
 *
 */
@FeignClient("bike-api")
public interface VehicleServiceInterface {
	
	/**
	 * 车辆信息添加
	 * @param vehicle
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/api/information/vehicle")
	Vehicle addVehicle(@RequestBody Vehicle vehicle);
	
	/**
	 * 车辆信息更新
	 * @param vehicle
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/api/information/vehicle/{id}")
	Vehicle updateVehicle(@RequestBody Vehicle vehicle, @PathVariable("id")String id);
	
	/**
	 * 车辆信息获取
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/information/vehicle/{id}")
	Vehicle getVehicleById(@PathVariable("id")String id);
}
