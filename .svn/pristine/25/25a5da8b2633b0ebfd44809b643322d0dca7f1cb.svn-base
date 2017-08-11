package com.tiamaes.bike.connector.protocol.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Message8001;
import com.tiamaes.bike.connector.protocol.message.Message8001.Result;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Received0011;

import io.netty.channel.Channel;

/**
 * 终端升级结果应答
 * 
 * @author Chen
 *
 */
@Component
public class Protocol0011Handler implements ProtocolHandler<Received0011> {
	private static Logger logger = LogManager.getLogger(Protocol0011Handler.class);
	
	@Override
	public Message execute(Channel channel, Received received) throws Exception {
		Header header = received.getHeader();
		int terminalId = header.getTerminalId();
//		Received0011 received0011 = new Received0011(header, received.getBytes());
		//TODO 升级结果处理
		Message8001 response = new Message8001(header, Result.SUCCESS);
		if(logger.isDebugEnabled()){
			logger.debug("Client [{}] has bean authorized.", terminalId);
		}
		return response;
	}

}
