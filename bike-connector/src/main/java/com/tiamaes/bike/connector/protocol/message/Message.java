package com.tiamaes.bike.connector.protocol.message;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Chen
 *
 */
public class Message {
	private Header header;
	private byte[] bytes;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	/**
	 * 返回16进制数字符串
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.header.toString());
		if (this.bytes != null) {
			buffer.append(Hex.encodeHexString(this.bytes));
		}
		return buffer.toString();
	}
	
	public byte[] toBytes(){
		return getBytes();
	}
}
