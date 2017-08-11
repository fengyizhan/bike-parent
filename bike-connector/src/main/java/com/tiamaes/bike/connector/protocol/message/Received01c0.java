package com.tiamaes.bike.connector.protocol.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bouncycastle.util.encoders.Hex;

import com.tiamaes.bike.connector.protocol.message.Received0180.Body;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class Received01c0 extends Received{
	private Body body;
	public Received01c0(Header header, byte[] bytes) {
		super(header, bytes);
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		byte[] bytes1=new byte[6];
		buffer.readBytes(bytes1);
		String s=Hex.toHexString(bytes1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
		Body body=new Body();
		try {
			Date lockTime = sdf.parse(s);
			body.setLockTime(lockTime);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int timecheckcode=buffer.readByte();
		int lockcommand=buffer.readByte();
		int checkcode=buffer.readByte();
		
		body.setCheckcode(checkcode);
		body.setLockcommand(lockcommand);
		body.setTimecheckcode(timecheckcode);
		this.body=body;
	}

public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

public class Body{
	private Date lockTime;//开锁时间
	private int timecheckcode;//时间校验码
	private int lockcommand;//开锁命令
	private int checkcode;//总校验码
	public Date getLockTime() {
		return lockTime;
	}
	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}
	public int getTimecheckcode() {
		return timecheckcode;
	}
	public void setTimecheckcode(int timecheckcode) {
		this.timecheckcode = timecheckcode;
	}
	public int getLockcommand() {
		return lockcommand;
	}
	public void setLockcommand(int lockcommand) {
		this.lockcommand = lockcommand;
	}
	public int getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(int checkcode) {
		this.checkcode = checkcode;
	}
	
}

}
