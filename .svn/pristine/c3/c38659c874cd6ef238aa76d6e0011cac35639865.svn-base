package com.tiamaes.bike.connector.protocol.message;

import java.util.Date;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.connector.protocol.utils.Xor;

public class Received0200Test {
	
	@Test
	public void test() throws DecoderException{
		
		Header header = new Header();
		header.setId(0x0200);
		header.setLength(11);
		header.setChildPackage(false);
		header.setEnableRSA(false);
		header.setTerminalId(10006);
		header.setTerminalNo(10006);
		header.setTerminalType(Header.Type.STATION);
		header.setSerialNo(10);
		String s="0003000003050001020304";
		s.replaceAll(" ", "");
		byte[] bytes=Hex.decodeHex(s.toCharArray());
		Received0200 received0200=new Received0200(header,bytes);
		byte byte0 = Xor.xorValue(received0200.toBytes());
		byte[] bytes1 = ArrayUtils.add(received0200.toBytes(), byte0);
		System.out.println(byte0);
		System.out.println(bytes.length+"-------"+Hex.encodeHexString(bytes1));
		System.out.println(received0200.getHeader().getId());
		System.out.println(received0200.getHeader().getLength());
		System.out.println(received0200.getHeader().getPacketNo());
		System.out.println(received0200.getHeader().getPacketTotal());
		System.out.println(received0200.getHeader().getSerialNo());
		System.out.println(received0200.getHeader().getTerminalId());
		System.out.println(received0200.getHeader().getTerminalType());
		System.out.println(received0200.getBody().getWarning());
		System.out.println(received0200.getBody().getRunState());
	}
}
