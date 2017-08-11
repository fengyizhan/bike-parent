package com.tiamaes.bike.api.information.dictionary.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiamaes.bike.api.information.dictionary.persistence.DictionaryMapper;
import com.tiamaes.bike.common.bean.information.Region;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.information.VehicleType;

/**
 * 获取字典数据service
 * @author waibao001
 *
 */
@Service
public class DictionaryService implements DictionaryServiceInterface{

	@Resource
	private DictionaryMapper dictionaryMapper;
	
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Vehicle.Type> getListOfTypes() {
		return dictionaryMapper.getListOfTypes();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Vehicle.Factory> getListOfFactories() {
		return dictionaryMapper.getListOfFactories();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<VehicleType> getListOfVehicleTypes() {
		return dictionaryMapper.getListOfVehicleTypes();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Region> getListOfRegions(Region region) {
		return dictionaryMapper.getListOfRegions(region);
	}

}
