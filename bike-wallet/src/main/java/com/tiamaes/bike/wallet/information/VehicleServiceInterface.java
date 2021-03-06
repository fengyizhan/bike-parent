package com.tiamaes.bike.wallet.information;

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
	 * 车辆信息更新
	 * @param vehicle
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/api/information/vehicle/{id}")
	Vehicle updateVehicle(@RequestBody Vehicle vehicle, @PathVariable("id")String id);
}
