package com.tiamaes.bike.api.information.region.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiamaes.bike.api.information.region.persistence.RegionMapper;
import com.tiamaes.bike.common.bean.information.Region;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class RegionService implements RegionServiceInterface {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(RegionService.class);
	@Resource
	private RegionMapper regionMapper;
	
	@Override
	public List<Region> getRegionsByParentId(String parentId) {
		return regionMapper.getRegionsByParentId(parentId);
	}
	@Override
	public List<Region> getRegionsByType(Integer type) {
		return regionMapper.getRegionsByType(type);
	}
	
	
}
