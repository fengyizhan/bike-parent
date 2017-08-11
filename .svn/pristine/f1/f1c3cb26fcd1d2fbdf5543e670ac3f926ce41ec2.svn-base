package com.tiamaes.bike.connector.protocol.message;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * 终端注册应答
 * 
 * @author Chen
 */
public class Message8100 extends Message {

	private Body body;

	public Message8100(Header header, Result result) {
		Header _header = new Header();
		_header.setId(0x8003);
		_header.setChildPackage(header.isChildPackage());
		_header.setEnableRSA(header.isEnableRSA());
		_header.setTerminalId(header.getTerminalId());
		_header.setTerminalNo(header.getTerminalNo());
		Body _body = new Body();
		_body.setMessageId(_header.getId());
		_body.setSerialNo(_header.getSerialNo());
		_body.setResult(result);
		this.setHeader(_header);
		this.setBody(_body);
		this.setBytes(_body.getBytes());
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public class Body {
		/**
		 * 消息类型
		 */
		private int messageId;
		/**
		 * 消息流水号
		 */
		private long serialNo;
		/**
		 * 执行结果
		 */
		private Result result;
		/**
		 * 鉴权码
		 */
		private String authentication;
		public String getAuthentication() {
			return authentication;
		}

		public void setAuthentication(String authentication) {
			this.authentication = authentication;
		}

		public int getMessageId() {
			return messageId;
		}

		public void setMessageId(int messageId) {
			this.messageId = messageId;
		}

		public long getSerialNo() {
			return serialNo;
		}

		public void setSerialNo(long serialNo) {
			this.serialNo = serialNo;
		}

		public Result getResult() {
			return result;
		}

		public void setResult(Result result) {
			this.result = result;
		}

		public byte[] getBytes() {
			StringBuffer bufferStr = new StringBuffer();
//			bufferStr.append(String.format("%04X", messageId));
			bufferStr.append(String.format("%04X", serialNo));
			bufferStr.append(String.format("%02X", result.ordinal()));
			bufferStr.append(authentication);
			try {
				return Hex.decodeHex(bufferStr.toString().toCharArray());
			} catch (DecoderException e) {
				return null;
			}
		}
	}

	public enum Result {
		SUCCESS, FAILURE, ERROR, NOT_SUPPORTED, ACKNOWLEDGED;
	}
}
