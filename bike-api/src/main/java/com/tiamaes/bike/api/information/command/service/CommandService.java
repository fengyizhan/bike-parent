package com.tiamaes.bike.api.information.command.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.api.information.command.persistence.CommandMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.command.Command;


@Service
public class CommandService implements CommandServiceInterface {

	@Autowired
	@Qualifier("kafkaStringTemplate")
	private KafkaTemplate<String, String> kafkaStringTemplate;
	@Resource
	private CommandMapper commandMapper;
	@Resource
	private ObjectMapper objectMapper;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Command sendCommand(Command command) {
		try {
			String payload = objectMapper.writeValueAsString(command);
			command.setPayload(payload);
			//保存到数据库中
			commandMapper.saveCommand(command);
			SetOperations<String, String> setOperator = stringRedisTemplate.opsForSet();
			//如果该车为在线状态
			/**
			 * TODO monitor项目Listener中需要修改RedisKey.VEHICLES_ONLINE的操作
			 */
//			if(setOperator.isMember(RedisKey.VEHICLES_ONLINE, String.valueOf(command.getTerminalId())))
			{
				kafkaStringTemplate.send(MessageBuilder.withPayload(payload)
						.setHeader(KafkaHeaders.TOPIC, Command.class.getName())
						.setHeader(KafkaHeaders.MESSAGE_KEY, String.valueOf(command.getTerminalId())).build());
			}
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("命令转换失败!");
		}
		return command;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendCommands(List<? extends Command> commands) {
		for (Command command : commands) {
			sendCommand(command);
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Command> getListOfCommandById(String simNo) {
		return commandMapper.getListOfCommandBySimNo(simNo);
	}

	@Override
	public Command getCommandBy(int id, long sid) {
		return null;
	}

	@Override
	public void completed(Command command) {
		
	}
}
