package com.tiamaes.bike.connector.protocol.message;

import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.tiamaes.bike.connector.protocol.utils.Xor;
public class Received0012Test {
	@Test
	public void open() throws Exception{
		Header header = new Header();
		header.setId(0x0012);
		header.setLength(0);
		header.setChildPackage(false);
		header.setEnableRSA(false);
		header.setTerminalId(10003);
		header.setTerminalType(Header.Type.STATION);
		header.setSerialNo(10);
		String s="0003 00 01 01 00000001 00000000 000f4243";
		s=s.replaceAll(" ", "");
		byte[] bytes=Hex.decodeHex(s.toCharArray());
		Received0012 received0012=new Received0012(header,bytes);
		byte byte0 = Xor.xorValue(received0012.toBytes());
		byte[] bytes1 = ArrayUtils.add(received0012.toBytes(), byte0);
		System.out.println("open-------"+"7e"+Hex.encodeHexString(bytes1)+"7e");
	}
	@Test
	public void close() throws Exception{
		Header header = new Header();
		header.setId(0x0012);
		header.setLength(0);
		header.setChildPackage(false);
		header.setEnableRSA(false);
		header.setTerminalId(10003);
		header.setTerminalType(Header.Type.STATION);
		header.setSerialNo(10);
		String s="0003 00 00 01 00000001 00000000 000f4243";
		s=s.replaceAll(" ", "");
		byte[] bytes=Hex.decodeHex(s.toCharArray());
		Received0012 received0012=new Received0012(header,bytes);
		byte byte0 = Xor.xorValue(received0012.toBytes());
		byte[] bytes1 = ArrayUtils.add(received0012.toBytes(), byte0);
		System.out.println("close-------"+"7e"+Hex.encodeHexString(bytes1)+"7e");
	}
}
