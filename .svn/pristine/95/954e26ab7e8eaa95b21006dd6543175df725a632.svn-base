package com.tiamaes.bike.connector.protocol.message;

import org.bouncycastle.util.encoders.Hex;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 终端鉴权包
 * @author fei
 *
 */
public class Received0005 extends Received {
	private Body body;
	public Received0005(Header header, byte[] bytes) {
		super(header, bytes);
		String acode=Hex.toHexString(bytes);
		Body body=new Body();
		body.setAcode(acode);
		this.body=body;
	}
	
	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public class Body{
		private String acode;//鉴权码

		public String getAcode() {
			return acode;
		}

		public void setAcode(String acode) {
			this.acode = acode;
		}
		
	}
}
