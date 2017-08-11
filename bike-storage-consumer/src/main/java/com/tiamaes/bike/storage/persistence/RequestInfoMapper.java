package com.tiamaes.bike.storage.persistence;

import com.tiamaes.bike.common.bean.connector.RequestInfo;

/**
 * 请求报文信息mapper
 * @author waibao001
 *
 */
public interface RequestInfoMapper {
	/**
	 * 增加请求报文信息
	 * @param requestInfo
	 */
	int addRequestInfo(RequestInfo requestInfo);
}
