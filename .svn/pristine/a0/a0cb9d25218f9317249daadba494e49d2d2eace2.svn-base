package com.tiamaes.bike.connector.protocol.handler;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.tiamaes.bike.common.bean.command.Completed;
import com.tiamaes.bike.config.ChannelRepository;
import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Message8001;
import com.tiamaes.bike.connector.protocol.message.Message8001.Result;
import com.tiamaes.bike.connector.service.CommandServiceInterface;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Received0001;
import com.tiamaes.bike.connector.protocol.message.Received0002;


import io.netty.channel.Channel;

/**
 * 心跳包0x0002
 * 
 * @author Chen
 *
 */
@Component
public class Protocol0002Handler implements ProtocolHandler<Received0002> {
	@Autowired
	private CommandServiceInterface commandService;
	@Autowired
	private ChannelRepository channelRepository;
	@Override
	public Message execute(Channel channel, Received received) throws Exception {
		Header header = received.getHeader();
		Header.Type terminalType=header.getTerminalType();
		int terminalId=header.getTerminalId();
		int terminalNo=header.getTerminalNo();
		if(Header.Type.LOCK.equals(terminalType))
		{
			channelRepository.putLock(String.valueOf(terminalNo),channel);
		}
		if(Header.Type.STATION.equals(terminalType))
		{
			channelRepository.putStation(String.valueOf(terminalNo),channel);
		}
		return new Message8001(header, Result.SUCCESS);
	}

}
