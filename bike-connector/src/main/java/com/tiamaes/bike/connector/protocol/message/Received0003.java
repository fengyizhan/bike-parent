package com.tiamaes.bike.connector.protocol.message;

import org.bouncycastle.util.encoders.Hex;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 终端注册包
 * 
 * @author Chen
 */
public class Received0003 extends Received {
	private Body body;
	public Received0003(Header header, byte[] bytes) {
		super(header, bytes);
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		byte[] bytes1=new byte[5];
		buffer.readBytes(bytes1);
		String producerId=Hex.toHexString(bytes1);
		byte[] bytes2=new byte[20];
		buffer.readBytes(bytes2);
		String terminalType=Hex.toHexString(bytes2);
		byte[] bytes3=new byte[7];
		buffer.readBytes(bytes3);
		String terminalId=Hex.toHexString(bytes3);
		byte[] bytes4=new byte[20];
		buffer.readBytes(bytes4);
		String simNo=Hex.toHexString(bytes4);
		Body body=new Body();

		body.setProducerId(producerId);
		body.setTerminalType(terminalType);
		body.setTerminalId(terminalId);
		body.setSimNo(simNo);
		this.body=body;
	}
	
public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

public class Body{
	/**
	 * 制造商ID
	 */
	private String producerId;
	/**
	 * 终端型号
	 */
	private String terminalType;
	/**
	 * 终端编号
	 */
	private String terminalId;
	/**
	 * sim卡号
	 */
	private String simNo;
	public String getSimNo() {
		return simNo;
	}
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	
}
}
