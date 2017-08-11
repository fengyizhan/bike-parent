package com.tiamaes.bike.connector.protocol.message;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 升级数据包应答包
 * 
 * @author Chen
 */
public class Received0010 extends Received {
	private Body body;
	public Received0010(Header header, byte[] bytes) {
		super(header, bytes);
		CompositeByteBuf buffer=Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		int up=buffer.readByte();
		int result=buffer.readByte();
		Body body=new Body();
		body.setUp(up);
		body.setResult(result);
		this.body=body;
	}
	
public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

public class Body{
	private int up;//升级类型
	private int result;//结果
	public int getUp() {
		return up;
	}
	public void setUp(int up) {
		this.up = up;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
}
}
