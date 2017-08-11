package com.tiamaes.bike.connector.protocol.message;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author Chen
 *
 */
public class Received {

	private Header header;
	private byte[] bytes;
	
	public Received(Header header, byte[] bytes){
		this.header = header;
		this.bytes = bytes;
	}
	
	public Header getHeader() {
		return header;
	}
	
	public byte[] getBytes(){
		return this.bytes;
	}
	
	/**
	 * 返回16进制数字符串
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.header.toString());
		if(this.bytes != null){
			buffer.append(Hex.encodeHexString(this.bytes));
		}
		return buffer.toString().toLowerCase();
	}
	
	
	public byte[] toBytes(){
		return ArrayUtils.addAll(this.header.toBytes(), this.bytes);
	}
}
