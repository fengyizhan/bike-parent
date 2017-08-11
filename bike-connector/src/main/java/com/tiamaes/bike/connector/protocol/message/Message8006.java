package com.tiamaes.bike.connector.protocol.message;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * 设置终端参数包
 * 
 * @author Chen
 */
public class Message8006 extends Message {

	private Body body;

	public Message8006(Header header ) {
		Header _header = new Header();
		_header.setId(0x8001);
		_header.setChildPackage(header.isChildPackage());
		_header.setEnableRSA(header.isEnableRSA());
		_header.setTerminalId(header.getTerminalId());
		_header.setTerminalNo(header.getTerminalNo());
//得到需要下发的命令
		//为body赋值
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public class Body {
		private int paramnum;
		private int paramId;
		private int paramlength;
		private int paramvalue;


		public int getParamnum() {
			return paramnum;
		}


		public void setParamnum(int paramnum) {
			this.paramnum = paramnum;
		}


		public int getParamId() {
			return paramId;
		}


		public void setParamId(int paramId) {
			this.paramId = paramId;
		}


		public int getParamlength() {
			return paramlength;
		}


		public void setParamlength(int paramlength) {
			this.paramlength = paramlength;
		}


		public int getParamvalue() {
			return paramvalue;
		}


		public void setParamvalue(int paramvalue) {
			this.paramvalue = paramvalue;
		}


		public byte[] getBytes() {
			StringBuffer bufferStr = new StringBuffer();
//			bufferStr.append(String.format("%08X", serialNo));
//			bufferStr.append(String.format("%04X", messageId));
			try {
				return Hex.decodeHex(bufferStr.toString().toCharArray());
			} catch (DecoderException e) {
				return null;
			}
		}
	}


}
