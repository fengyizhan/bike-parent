package com.tiamaes.bike.connector.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class CustomizeProtocolDecoder extends ByteToMessageDecoder {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomizeProtocolDecoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		if (!in.isReadable() || in.readUnsignedByte() != 0x7e) {
			in.discardReadBytes();
			return;
		}
		if(in.isReadable() && in.readUnsignedByte() == 0x7e){
			in.resetReaderIndex();
			in.readUnsignedByte();
			in.discardReadBytes();
			return;
		}
		
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		while (in.isReadable()) {
			if(in.readUnsignedByte() == 0x7e){
				buffer.writeBytes(in, 1, in.readerIndex() - 2);
				in.discardReadBytes();
				break;
			}
		}
		
		if(buffer.writerIndex() > 0){
			buffer.resetReaderIndex();
			if(logger.isTraceEnabled()){
				logger.trace("RCVD: 7e{}7e", ByteBufUtil.hexDump(buffer));
			}
			out.add(buffer);
		}else{
			in.resetReaderIndex();
		}
	}
}