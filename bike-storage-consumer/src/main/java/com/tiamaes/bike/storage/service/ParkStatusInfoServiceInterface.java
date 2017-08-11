package com.tiamaes.bike.storage.service;

import com.tiamaes.bike.common.bean.connector.ParkStatusInfo;

public interface ParkStatusInfoServiceInterface {

	/**
	 * 增加停车区状态信息
	 * @param parkStatusInfo
	 */
	void addParkStatusInfo(ParkStatusInfo parkStatusInfo);

}
