package com.tiamaes.bike.connector.protocol.message;

import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.tiamaes.bike.connector.protocol.utils.Xor;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class Received0002Test {

	
	@Test
	public void test() {
		Header header = new Header();
		header.setId(0x0002);
		header.setLength(0);
		header.setChildPackage(false);
		header.setEnableRSA(false);
		header.setTerminalId(100000);
		header.setTerminalType(Header.Type.LOCK);
		header.setSerialNo(10);
		Received0002 received0002 = new Received0002(header, null);
		
		byte byte0 = Xor.xorValue(received0002.toBytes());
		byte[] bytes = ArrayUtils.add(received0002.toBytes(), byte0);
		System.out.println(Hex.encodeHexString(received0002.toBytes()));
		System.out.println(byte0);
		System.out.println(Hex.encodeHexString(bytes));
		System.out.println(Xor.check(bytes));
		
		
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		
		header = new Header();
		header.setId(buffer.readUnsignedShort());
		int bodyProperties = buffer.readUnsignedShort();
		header.setChildPackage((bodyProperties & 0x2000) == 0x2000);
		header.setEnableRSA((bodyProperties & 0x0400) == 0x0400);
		header.setLength(bodyProperties & 0x03FF);
		int terminalId = buffer.readInt();
		header.setTerminalId(terminalId);
		header.setTerminalType(Header.Type.values()[buffer.readUnsignedByte()]);
		header.setSerialNo(buffer.readUnsignedShort());
		if(header.isChildPackage()){
			header.setPacketTotal(buffer.readUnsignedShort());
			header.setPacketNo(buffer.readUnsignedShort());
		}
		byte[] bytes2 = new byte[buffer.readableBytes() - 1];
		buffer.readBytes(bytes2);
		received0002 = new Received0002(header, bytes2);
		
		System.out.println(received0002);
		
		System.out.println(Integer.toUnsignedString(0x2000, 2));
		System.out.println(Integer.toUnsignedString(0x2000 >> 13 & 0x01, 2));
		System.out.println(0x2000 >> 13 & 1);
		
		byte[] data = new byte[5];
		System.out.println(Hex.encodeHexString(data));
	}
}
