package com.tiamaes.bike.storage;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tiamaes.bike.common.bean.connector.ResponseInfo;
import com.tiamaes.bike.common.bean.connector.ResponseInfo.ResponseState;
import com.tiamaes.bike.storage.service.ResponseInfoServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResponseInfoServiceTest {

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(ResponseInfoServiceTest.class);
	
	@Resource
	private ResponseInfoServiceInterface responseInfoService;
	
	@Before
	public void before() {
		assertNotNull("KafkaTemplate not found...");
	}
	
	@Test
	public void saveResponseInfo() throws Exception{
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setSimNo("018538317749");
		responseInfo.setMessageId(11000);
		responseInfo.setMessageSeqNo(112200);
		responseInfo.setResponseContent("成功");
		responseInfo.setResponseState(ResponseState.SUCCESS);
		responseInfoService.saveResponseInfo(responseInfo);
	}
}
