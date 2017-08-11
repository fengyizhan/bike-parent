package com.tiamaes.bike.connector.protocol.message;

import java.util.Date;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class received0005Test {
	@Test
	public void test() throws DecoderException{
		Header header = new Header();
		header.setId(0x0003);
		header.setLength(0);
		header.setChildPackage(false);
		header.setEnableRSA(false);
		header.setTerminalId(100000);
		header.setTerminalType(Header.Type.LOCK);
		header.setSerialNo(10);
	//   received0005   	
		String s="31 39 32 2e 31 36 38 2e 31 30 30 2e 31 38";
		//String s="0a0a0a0a";
		//received0008
	//	String s="0001 0102030405 01 02 01 03 01 04";
		s=s.replaceAll(" ","");
		byte[] bytes=Hex.decodeHex(s.toCharArray());
//		Received0008 received0008=new Received0008(header,bytes);
//		System.out.println((received0008.getBody().getTerminalFirmwareVer()));
		Received0005 received0005=new Received0005(header,bytes);
		System.out.println((received0005.getBody().getAcode()));
	}
}
