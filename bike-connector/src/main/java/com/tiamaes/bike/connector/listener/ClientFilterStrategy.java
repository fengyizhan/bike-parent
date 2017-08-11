package com.tiamaes.bike.connector.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.command.Command;


@Component
public class ClientFilterStrategy implements RecordFilterStrategy<String, String> {
	
	@Resource(name = "stringRedisTemplate")
	private RedisTemplate<String, String> stringRedisTemplate;
	
	@Override
	public boolean filter(ConsumerRecord<String, String> consumerRecord){
		HashOperations<String, String, String> hashOperation = stringRedisTemplate.opsForHash();
		String ipAddress = hashOperation.get(RedisKey.CONNECTS, consumerRecord.key());
		//TODO error kafkaTemplate发送时要求设置KafkaHeaders.MESSAGE_KEY为当前IP地址
		/**
		 * kafkaStringTemplate.send(MessageBuilder.withPayload(payload)
						.setHeader(KafkaHeaders.TOPIC, Command.class.getName())
						.setHeader(KafkaHeaders.MESSAGE_KEY, String.valueOf(command.getTerminalId())).build());
		 */
		if(true)return false;
		try {
			return !InetAddress.getLocalHost().getHostAddress().equals(ipAddress);
		} catch (UnknownHostException e) {
			return true;
		}
	}
}