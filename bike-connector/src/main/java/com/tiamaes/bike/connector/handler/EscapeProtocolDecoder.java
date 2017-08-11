package com.tiamaes.bike.connector.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.utils.Xor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class EscapeProtocolDecoder extends ByteToMessageDecoder {
	private static final Logger logger = LoggerFactory.getLogger(EscapeProtocolDecoder.class);
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		// 将转义字符还原
		while (in.isReadable()) {
			short i = in.readUnsignedByte();
			if (i == 0x7d) {
				buffer.writeByte(in.readUnsignedByte() == 0x01 ? 0x7d : 0x7e);
			} else {
				buffer.writeByte(i);
			}
		}
		buffer.resetReaderIndex();
		byte[] messageBytes = new byte[buffer.readableBytes()];
		
		buffer.readBytes(messageBytes);
		buffer.resetReaderIndex();
		if (Xor.check(messageBytes)) {
			Header header = new Header();
			//1消息ID
			header.setId(buffer.readUnsignedShort());
			/**
			 * 2消息体长度
			 */
			int bodyProperties = buffer.readUnsignedShort();
			header.setChildPackage((bodyProperties >> 13 & 1) == 1);
			header.setEnableRSA((bodyProperties >> 14 & 1) == 1);
			header.setLength(bodyProperties & 0x03FF);
			//3终端出厂编号
			int terminalNo = buffer.readInt();
			header.setTerminalNo(terminalNo);
			//4终端数据库编号
			int terminalId = buffer.readInt();
			header.setTerminalId(terminalId);
			//5终端类型
			header.setTerminalType(Header.Type.values()[buffer.readUnsignedByte()]);
			//6消息流水号
			header.setSerialNo(buffer.readUnsignedShort());
			if(header.isChildPackage()){
				header.setPacketTotal(buffer.readUnsignedShort());
				header.setPacketNo(buffer.readUnsignedShort());
			}
			byte[] bytes2 = new byte[buffer.readableBytes() - 1];
			buffer.readBytes(bytes2);
			Received received = new Received(header, bytes2);
			out.add(received);
		}else
		{
			logger.error(" Xor.check is false:");
		}
	}

}