package com.tiamaes.bike.connector.protocol.message;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 终端心跳包
 * 
 * @author Chen
 */
public class Received0002 extends Received {

	public Received0002(Header header, byte[] bytes) {
		super(header, bytes);
	}
	
}
