package com.tiamaes.bike.storage.service;

import com.tiamaes.bike.common.bean.connector.RequestInfo;

/**
 * 请求报文信息服务
 * @author lsl
 *
 */
public interface RequestInfoServiceInterface {
	/**
	 * 保存请求报文信息
	 * @param requestInfo
	 */
	boolean saveRequestInfo(RequestInfo requestInfo);
}
