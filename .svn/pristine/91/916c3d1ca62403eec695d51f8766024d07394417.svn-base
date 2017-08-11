package com.tiamaes.bike.connector.service;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.command.Command;
import com.tiamaes.bike.common.bean.command.Completed;
import com.tiamaes.bike.config.ChannelRepository;
import com.tiamaes.bike.connector.persistence.CommandMapper;

@Service
@SuppressWarnings("unused")
public class CommandService implements CommandServiceInterface {
	private static Logger logger = LogManager.getLogger(CommandService.class);
	@Autowired
	private ChannelRepository channelRepository;
	
	@Resource(name = "stringRedisTemplate")
	private RedisTemplate<String, Long> longRedisTemplate;
	
	@Resource(name = "stringRedisTemplate")
	private RedisTemplate<String, String> stringRedisTemplate;
	
	@Resource(name = "stringRedisTemplate")
    private ValueOperations<String, Long> longOperator;
	
	@Resource
	private CommandMapper commandMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendCommand(Command command){
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteCommand(Command command) {
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void completed(Completed completed) {
		
	}
	/**
	 * 根据终端ID生成消息流水号
	 * @param terminalId 终端ID
	 * @return 消息流水号
	 */
	public int generateSerialNo(String terminalId)
	{
		String key = String.format(RedisKey.SERIALNO_SIMNO, terminalId);            
		Long serialNo = longOperator.increment(key, 1);
        if (serialNo >= Short.MAX_VALUE) {
           longOperator.increment(key, Math.negateExact(serialNo) + 1);
           return 1;
        }
        return serialNo.intValue();
	}
}
