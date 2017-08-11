package com.tiamaes.bike.connector.protocol.handler;


import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Received;

import io.netty.channel.Channel;

public interface ProtocolHandler<T extends Received> {

	public Message execute(Channel channel, Received received) throws Exception;

}
