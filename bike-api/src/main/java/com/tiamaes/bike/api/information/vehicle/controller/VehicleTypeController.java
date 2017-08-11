package com.tiamaes.bike.api.information.vehicle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.bike.api.information.vehicle.service.VehicleTypeServiceInterface;
import com.tiamaes.bike.common.bean.information.VehicleType;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;

@RestController
@RequestMapping("/information/vehicleType")
public class VehicleTypeController {
	@Autowired
	private VehicleTypeServiceInterface vehicleTypeService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<VehicleType> list(@CurrentUser User operator) {
		return vehicleTypeService.getVehicleTypes();
	}
}
