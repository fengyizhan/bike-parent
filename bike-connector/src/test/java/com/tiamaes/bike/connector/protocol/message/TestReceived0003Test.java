package com.tiamaes.bike.connector.protocol.message;

import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.tiamaes.bike.connector.protocol.utils.Xor;

public class TestReceived0003Test {

	
	@Test
	public void test() throws Exception{
		String a="31 37 30 33 30 32 31 37 33 30 31 30".replaceAll(" ","");
		System.out.println(new String(Hex.decodeHex(a.toCharArray())));
		if(1==1)return;
		Header header = new Header();
		header.setId(0x0003);
		header.setLength(36);
		header.setChildPackage(false);
		header.setEnableRSA(false);
		header.setTerminalId(100000);
		header.setTerminalType(Header.Type.LOCK);
		header.setSerialNo(10);
		String sendStr="0000010c 0000000100 000000000000000000000000000000000000000b 0000000d";
		sendStr=sendStr.replaceAll(" ","");
		byte[] contents=Hex.decodeHex(sendStr.toCharArray());
		TestReceived received0003 = new TestReceived(header, contents);
		byte byte0 = Xor.xorValue(received0003.toBytes());
		System.out.println("xor加密位："+byte0);
		byte[] total_bytes = ArrayUtils.add(received0003.toBytes(), byte0);
		System.out.println("内容体："+Hex.encodeHexString(received0003.toBytes()));
		System.out.println("带鉴权码的消息体："+Hex.encodeHexString(total_bytes));
		System.out.println("鉴权码有效性："+Xor.check(total_bytes));
		System.out.println("10进制表示的messageId:"+received0003.getBody().getMessageId());
		System.out.println("10进制表示的productId:"+received0003.getBody().getProductId());		
		System.out.println("10进制表示的terminalType:"+received0003.getBody().getTerminalType());		
		System.out.println("10进制表示的terminalId:"+received0003.getBody().getTerminalId());		
	}
}
