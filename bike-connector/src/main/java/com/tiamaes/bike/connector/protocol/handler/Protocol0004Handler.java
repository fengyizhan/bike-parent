package com.tiamaes.bike.connector.protocol.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Message8100;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Message8100.Result;
import com.tiamaes.bike.connector.protocol.message.Received0004;

import io.netty.channel.Channel;

/**
 * 终端注销0x0004
 * 
 * @author Chen
 *
 */
@Component
public class Protocol0004Handler implements ProtocolHandler<Received0004> {
	private static Logger logger = LogManager.getLogger(Protocol0004Handler.class);

	@Override
	public Message execute(Channel channel, Received received) throws Exception {
		Header header = received.getHeader();
		int terminalId = header.getTerminalId();
//		Received0004 received0004 = new Received0004(header, received.getBytes());
		String address = channel.remoteAddress().toString();
		//TODO 注销业务流程
		if(logger.isDebugEnabled()){
			logger.debug(String.format("Auto [%s] has registed, the remote address is [%s].", terminalId, address));
		}
		
		Message8100 response = new Message8100(header, Result.SUCCESS);
		return response;
	}

	
}
