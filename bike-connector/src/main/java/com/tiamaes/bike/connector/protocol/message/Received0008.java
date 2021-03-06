package com.tiamaes.bike.connector.protocol.message;

import org.bouncycastle.util.encoders.Hex;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 查询终端属性应答包
 * 
 * @author Chen
 */
public class Received0008 extends Received {
	private Body body;
	public Received0008(Header header, byte[] bytes) {
		super(header, bytes);
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		int terminalType=buffer.readUnsignedShort();
		byte[] bytes1=new byte[5];
		buffer.readBytes(bytes1);
		String producerId=Hex.toHexString(bytes1);
		int terminalModelLength=buffer.readByte();
		byte[] bytes2=new byte[terminalModelLength];
		buffer.readBytes(bytes2);
		String terminalModel=Hex.toHexString(bytes2);
		int terminalverLength=buffer.readByte();
		byte[] bytes3= new byte[terminalverLength];
		buffer.readBytes(bytes3);
		String terminalver=Hex.toHexString(bytes3);
		int terminalFirmwareVerLength=buffer.readByte();
		byte[] bytes4=new byte[terminalFirmwareVerLength];
		buffer.readBytes(bytes4);
		String terminalFirmwareVer=Hex.toHexString(bytes4);
		Body body=new Body();
		body.setTerminalType(terminalType);
		body.setTerminalModelLength(terminalModelLength);
		body.setTerminalModel(terminalModel);
		body.setTerminalverLength(terminalverLength);
		body.setTerminalver(terminalver);
		body.setTerminalFirmwareLength(terminalFirmwareVerLength);
		body.setTerminalFirmwareVer(terminalFirmwareVer);
		body.setProducerId(producerId);
		this.body=body;
	}
	
public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

public class Body{
	private int terminalType;//终端类型
	private String producerId;//制造商ID
	private int terminalModelLength;//终端型号长度
	private String terminalModel;//终端型号
	private int terminalverLength;//终端硬件版本号长度
	private String terminalver;//终端硬件版本号
	private int terminalFirmwareLength;//终端固件版本号长度
	private String terminalFirmwareVer;//终端固件版本号


	public int getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
	}
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	public int getTerminalModelLength() {
		return terminalModelLength;
	}
	public void setTerminalModelLength(int terminalModelLength) {
		this.terminalModelLength = terminalModelLength;
	}
	public String getTerminalModel() {
		return terminalModel;
	}
	public void setTerminalModel(String terminalModel) {
		this.terminalModel = terminalModel;
	}
	public int getTerminalverLength() {
		return terminalverLength;
	}
	public void setTerminalverLength(int terminalverLength) {
		this.terminalverLength = terminalverLength;
	}
	public String getTerminalver() {
		return terminalver;
	}
	public void setTerminalver(String terminalver) {
		this.terminalver = terminalver;
	}
	public int getTerminalFirmwareLength() {
		return terminalFirmwareLength;
	}
	public void setTerminalFirmwareLength(int terminalFirmwareLength) {
		this.terminalFirmwareLength = terminalFirmwareLength;
	}
	public String getTerminalFirmwareVer() {
		return terminalFirmwareVer;
	}
	public void setTerminalFirmwareVer(String terminalFirmwareVer) {
		this.terminalFirmwareVer = terminalFirmwareVer;
	}	
}

}
