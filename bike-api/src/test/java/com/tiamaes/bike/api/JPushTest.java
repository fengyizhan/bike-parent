package com.tiamaes.bike.api;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.Result;
import com.tiamaes.bike.common.bean.integrated.BorrowRecord;
import com.tiamaes.bike.messages.Push;



@RunWith(SpringRunner.class)
@SpringBootTest
public class JPushTest {
//	private static Logger logger = LogManager.getLogger(KafkaDemoTest.class);
	
	@Autowired
	@Qualifier("pushUtil")
	private Push push;
	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper objectMapper;
	
	@Resource(name = "jsonRedisTemplate")
	private RedisTemplate<String,BorrowRecord> borrowRecordRedisTemplate;
	@Before
	public void before() {
	}
	
	@Test
	public void test1() throws Exception {
		//群发
		push.send("极光推送消息测试");
	}
	@Test
	public void test2() throws Exception {
		/**
		 * 定点发送给指定终端APP用户
		 * 前提是，APP用户注册后，需要调用jpush sdk按照指定规则，如手机号码，绑定jpush中的alias
		 * 参数1：通知内容
		 * 参数2：APP终端别名
		 */
		push.sendNotice("自定义消息，感谢您使用共享单车APP","13921543765");
	}
	
	@Test
	public void test3() throws Exception {
		/**
		 * 与android端 定制的 自定义通知内容体格式
		 */
		String username="13720043629";
		Result result1=new Result();
		result1.setSuccess(true);
		result1.setMessage("纯文本消息");
		result1.setType(Result.TEXT_TYPE);
		String sendLog1=push.sendMsg(objectMapper.writeValueAsString(result1),username);
		System.out.println("极光推送结果1："+sendLog1);
		//=========================================
		Result result2=new Result();
		result2.setSuccess(true);
		result2.setMessage("json格式消息测试");
		result1.setType(Result.JSON_TYPE);
		BorrowRecord borrowRecord=(BorrowRecord)borrowRecordRedisTemplate.opsForHash().get(RedisKey.DRIVERS_BORROW_RECORDS,username);
		result2.getData().put("borrow",borrowRecord);
		String sendLog2=push.sendMsg(objectMapper.writeValueAsString(result2),username);
		System.out.println("极光推送结果2："+sendLog2);
	}
	
}

