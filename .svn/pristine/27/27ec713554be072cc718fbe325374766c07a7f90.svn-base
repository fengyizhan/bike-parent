package com.tiamaes.bike.connector.protocol.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Message8001;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Message8001.Result;
import com.tiamaes.bike.connector.protocol.message.Received0010;

import io.netty.channel.Channel;

/**
 * 升级数据包消息应答
 * 
 * @author Chen
 *
 */
@Component
public class Protocol0010Handler implements ProtocolHandler<Received0010> {
	private static Logger logger = LogManager.getLogger(Protocol0010Handler.class);
	
	@Override
	public Message execute(Channel channel, Received received) throws Exception {
		Header header = received.getHeader();
		int terminalId = header.getTerminalId();
//		Received0010 received0010 = new Received0010(header, received.getBytes());
		//TODO 升级数据包结果处理
		Message8001 response = new Message8001(header, Result.SUCCESS);
		if(logger.isDebugEnabled()){
			logger.debug("Client [{}] has bean authorized.", terminalId);
		}
		return response;
	}

}
