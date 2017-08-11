package com.tiamaes.bike.connector.protocol.message;

import org.bouncycastle.util.encoders.Hex;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 刷卡借还车消息包
 * 
 * @author Chen
 */
public class Received0202 extends Received {
	private Body body;

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Received0202(Header header, byte[] bytes) {
		super(header, bytes);
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		int bikestateflag = buffer.readByte();
		byte[] bytes1 = new byte[4];
		buffer.readBytes(bytes1);
		String bikenum = Hex.toHexString(bytes1);
		byte[] bytes2 = new byte[30];
		buffer.readBytes(bytes2);
		String cardnum = Hex.toHexString(bytes2);
		Body body = new Body();
		body.setBikestateflag(bikestateflag);
		body.setBikenum(bikenum);
		body.setCardnum(cardnum);
		this.body = body;
	}

	public class Body {
		private int bikestateflag;// 借还车标志
		private String bikenum;// 车辆编号
		private String cardnum;// 卡号

		public int getBikestateflag() {
			return bikestateflag;
		}

		public void setBikestateflag(int bikestateflag) {
			this.bikestateflag = bikestateflag;
		}

		public String getBikenum() {
			return bikenum;
		}

		public void setBikenum(String bikenum) {
			this.bikenum = bikenum;
		}

		public String getCardnum() {
			return cardnum;
		}

		public void setCardnum(String cardnum) {
			this.cardnum = cardnum;
		}

	}
}
