package com.tiamaes.bike.storage;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tiamaes.bike.common.bean.connector.RequestInfo;
import com.tiamaes.bike.common.bean.connector.RequestInfo.RequestState;
import com.tiamaes.bike.storage.service.RequestInfoServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestInfoServiceTest {

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(RequestInfoServiceTest.class);
	
	@Resource
	private RequestInfoServiceInterface requestInfoService;
	
	@Before
	public void before() {
		assertNotNull("KafkaTemplate not found...");
	}
	
	@Test
	public void saveRequestInfo() throws Exception{
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setSimNo("018538317749");
		requestInfo.setMessageId(11000);
		requestInfo.setMessageSeqNo(112200);
		requestInfo.setRequestContent("请确认关锁");
		requestInfo.setRequestState(RequestState.SUCCESS);
		boolean result = requestInfoService.saveRequestInfo(requestInfo);
		Assert.assertEquals(true, result);
	}
}
