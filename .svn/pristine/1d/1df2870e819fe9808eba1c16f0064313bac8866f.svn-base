package com.tiamaes.bike.connector.protocol.message;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
/**
 * 终端通用应答
 * @author fei
 *
 */

public class Received0001 extends Received {

	private Body body;

	public Received0001(Header header, byte[] bytes) {
		super(header, bytes);
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		int serialNo = buffer.readUnsignedShort();
		int messageId = buffer.readUnsignedShort();
		int resultValue = buffer.readByte();
		Body body = new Body();
		body.setSerialNo(serialNo);
		body.setMessageId(messageId);
		body.setResultValue(ResultValue.values()[resultValue]);
		this.body = body;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public class Body {
		private int messageId;//应答ID
		private int serialNo;//应答流水号
		private ResultValue resultValue;//结果

		public int getMessageId() {
			return messageId;
		}

		public void setMessageId(int messageId) {
			this.messageId = messageId;
		}

		public int getSerialNo() {
			return serialNo;
		}

		public void setSerialNo(int serialNo) {
			this.serialNo = serialNo;
		}

		public ResultValue getResultValue() {
			return resultValue;
		}

		public void setResultValue(ResultValue resultValue) {
			this.resultValue = resultValue;
		}

	}
public enum ResultValue{
	成功,失败,消息有误,不支持;
}
}
