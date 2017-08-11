package com.tiamaes.bike.storage.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;

/**
 * 远程调用api相关服务 
 * @author kangty
 */
@FeignClient("bike-api")
public interface ApiFeignSerivceInterface {

	@RequestMapping(method = RequestMethod.PUT, value = "/api/information/park/{id}")
	Park update(@RequestBody Park park, @PathVariable("id")String id);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/api/information/vehicle/{id}")
	Vehicle update(@RequestBody Vehicle vehicle, @PathVariable("id")String id);
	
}
