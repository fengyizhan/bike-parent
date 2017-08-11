package com.tiamaes.bike.connector.protocol.message;

import java.util.Date;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.tiamaes.bike.connector.protocol.utils.Xor;

public class Received0100Test {
	@Test
	public void test() throws DecoderException{
		Header header = new Header();
		header.setId(0x0100);
		header.setLength(0);
		header.setChildPackage(false);
		header.setEnableRSA(false);
		header.setTerminalId(1000003);
		header.setTerminalType(Header.Type.LOCK);
		header.setSerialNo(10);
		String s="000305030606c6989c021308d40010001000201703031127003032";
		s.replaceAll(" ", "");
		byte[] bytes=Hex.decodeHex(s.toCharArray());
		Received0100 received0100=new Received0100(header,bytes);
		byte byte0 = Xor.xorValue(received0100.toBytes());
		byte[] bytes1 = ArrayUtils.add(received0100.toBytes(), byte0);
		System.out.println(bytes.length+"-------"+Hex.encodeHexString(bytes1));
		System.out.println(received0100.getBody().getWarnFlag());
		System.out.println(received0100.getBody().getRunState());
		System.out.println(received0100.getBody().getAtitude());
		System.out.println(received0100.getBody().getLongitude());
	}
}
