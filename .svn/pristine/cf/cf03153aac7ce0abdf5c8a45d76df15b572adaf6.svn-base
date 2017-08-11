package com.tiamaes.bike.connector.protocol.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.common.bean.command.Completed;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Received0001;
import com.tiamaes.bike.connector.protocol.message.Received0001.Body;
import com.tiamaes.bike.connector.service.CommandServiceInterface;

import io.netty.channel.Channel;

/**
 * 终端通用应答0x0001
 * 
 * @author Chen
 *
 */
@Component
public class Protocol0001Handler implements ProtocolHandler<Received0001> {

	@Autowired
	private CommandServiceInterface commandService;
	
	@Override
	public Message execute(Channel channel, Received received) throws Exception {
		Received0001 received0001 = new Received0001(received.getHeader(), received.getBytes());
		int terminalId = received0001.getHeader().getTerminalId();
		Body body = received0001.getBody();
		Completed completed = new Completed();
		completed.setTime(new Date());
		completed.setTerminalId(terminalId);
		completed.setSerialNo(body.getSerialNo());
		completed.setMessageId(body.getMessageId());
		completed.setState(Completed.State.valueOf(body.getResultValue().ordinal()));
		commandService.completed(completed);
		return null;
	}
}
