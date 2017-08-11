package com.tiamaes.bike.api.information.region.service;

import java.util.List;

import com.tiamaes.bike.common.bean.information.Region;

public interface RegionServiceInterface {
	
	/**
	 * 根据父级行政区划id获取子级行政区划列表
	 * @param parentId
	 * @return
	 */
	List<Region> getRegionsByParentId(String parentId);
	
	/**
	 * 根据等级获取符合级别的行政区划列表
	 * @param type
	 * @return
	 */
	List<Region> getRegionsByType(Integer type);
	
}
