package com.tiamaes.bike.connector.protocol.message;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * 终端注册应答
 * 
 * @author Chen
 */
public class Message8003 extends Message {

	private Body body;

	public Message8003(Header header, Result result,String acode) {
		Header _header = new Header();
		_header.setId(0x8003);
		_header.setChildPackage(header.isChildPackage());
		_header.setEnableRSA(header.isEnableRSA());
		_header.setTerminalId(header.getTerminalId());
		_header.setTerminalNo(header.getTerminalNo());
		Body _body = new Body();
		
		_body.setSerialNo(_header.getSerialNo());
		_body.setAcode(acode);
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
		private String acode;//鉴权码
		private long serialNo;//流水号
		private Result result;//结果



		public String getAcode() {
			return acode;
		}



		public void setAcode(String acode) {
			this.acode = acode;
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
			bufferStr.append(String.format("%08X", serialNo));
			bufferStr.append(String.format("%02X", result.ordinal()));
			bufferStr.append(acode);
			try {
				return Hex.decodeHex(bufferStr.toString().toCharArray());
			} catch (DecoderException e) {
				return null;
			}
		}
	}

	public enum Result {
		SUCCESS, REGISTERED,NOTEXISTED;
	}
}
