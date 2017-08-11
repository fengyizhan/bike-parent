package com.tiamaes.bike.api.information.vehicle.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiamaes.bike.api.information.vehicle.persistence.VehicleTypeMapper;
import com.tiamaes.bike.common.bean.information.VehicleType;

@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class VehicleTypeService implements VehicleTypeServiceInterface {

	@Resource
	private VehicleTypeMapper vehicleTypeMapper;
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<VehicleType> getVehicleTypes() {
		return vehicleTypeMapper.getListOfVehicleTypes();
	}

	
}
