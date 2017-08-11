package com.tiamaes.bike.api;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.bean.connector.command.LineDelCommand;
import com.tiamaes.bike.common.bean.connector.command.PolygonDelCommand;
import com.tiamaes.bike.common.bean.connector.command.TextCommand;
import com.tiamaes.bike.common.utils.UUIDGenerator;



@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaDemoTest {
//	private static Logger logger = LogManager.getLogger(KafkaDemoTest.class);
	
	@Autowired
	@Qualifier("kafkaStringTemplate")
	private KafkaTemplate<String, String> kafkaStringTemplate;
	@Resource
	private ObjectMapper jsonObjectMapper;
	
	@Before
	public void before() {
		assertNotNull("KafkaTemplate<String, String> not found...", kafkaStringTemplate);
	}
	
	@Test
	public void test() throws Exception {
		String simNo = "13783698404";
		TextCommand command = new TextCommand();
		command.setId(UUIDGenerator.getUUID());
		command.setSimNo(simNo);
		
		String body = jsonObjectMapper.writeValueAsString(command);
		
		kafkaStringTemplate.send(MessageBuilder.withPayload(body)
					.setHeader(KafkaHeaders.TOPIC, command.getClass().getName())
					.setHeader(KafkaHeaders.MESSAGE_KEY, command.getSimNo()).build());
	}

	
	@Test
	public void test3() throws Exception {
		String simNo = "015800020022";
		//删除多边形区域的命令
		PolygonDelCommand  polygonDelCommand = new PolygonDelCommand();
		polygonDelCommand.setSimNo(simNo);
		//删除线路的命令
		LineDelCommand lineDelCommand = new LineDelCommand();
		lineDelCommand.setSimNo(simNo);
		//根据工程获取所有厂区
		List<Integer> stationIds = new ArrayList<Integer>();
		stationIds.add(1);
		stationIds.add(2);
		//根据工程获取所有线路id
		List<Integer> lineIds = new ArrayList<Integer>();
		lineIds.add(1);
		lineIds.add(2);
		polygonDelCommand.setId(UUIDGenerator.getUUID());
		//多边形区域的id集合
		polygonDelCommand.setPolygonIds(stationIds);
		lineDelCommand.setId(UUIDGenerator.getUUID());
		//线路的id集合
		lineDelCommand.setLineIds(lineIds);
		String body = jsonObjectMapper.writeValueAsString(polygonDelCommand);
		//推送到kafka
		kafkaStringTemplate.send(MessageBuilder.withPayload(body)
				.setHeader(KafkaHeaders.TOPIC, polygonDelCommand.getClass().getName())
				.setHeader(KafkaHeaders.MESSAGE_KEY, polygonDelCommand.getSimNo()).build());
		body = jsonObjectMapper.writeValueAsString(lineDelCommand);
		//推送到kafka
		kafkaStringTemplate.send(MessageBuilder.withPayload(body)
				.setHeader(KafkaHeaders.TOPIC, lineDelCommand.getClass().getName())
				.setHeader(KafkaHeaders.MESSAGE_KEY, lineDelCommand.getSimNo()).build());
	}
}

