package com.tiamaes.bike.storage.service;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiamaes.bike.common.bean.connector.ResponseInfo;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.storage.persistence.ResponseInfoMapper;

/**
 * 通信程序通用应答数据库服务接口
 * 
 * @author lsl
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ResponseInfoService implements ResponseInfoServiceInterface {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(ResponseInfoService.class);
	@Resource
	private ResponseInfoMapper responseInfoMapper;

	/**
	 * 需要入库的应答消息ID
	 */
	private Integer[] messageIds = { 0x8001, 0x8003, 0x8006, 0x8007, 0x8008, 
			0x8013, 0x8300, 0x8400 };

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveResponseInfo(ResponseInfo responseInfo) throws Exception {
		// 过滤需要保存结果的通用应答
		int messageId = responseInfo.getMessageId();
		if (Arrays.binarySearch(messageIds, messageId) >= 0) {
			responseInfo.setId(UUIDGenerator.getUUID());
			responseInfo.setResponseTime(new Date());
			responseInfoMapper.saveResponseInfo(responseInfo);
		}
	}

}
