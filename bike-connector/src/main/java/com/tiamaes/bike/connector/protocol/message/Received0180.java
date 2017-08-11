package com.tiamaes.bike.connector.protocol.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bouncycastle.util.encoders.Hex;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
/**
 * 开锁协议
 * @author fei
 *
 */
public class Received0180 extends Received {
	private Body body;
	
	public Received0180(Header header, byte[] bytes) {
		super(header, bytes);
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		byte[] bytes1=new byte[6];
		buffer.readBytes(bytes1);
		String s=Hex.toHexString(bytes1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
		Body body=new Body();
		try {
			Date unlockTime = sdf.parse(s);
			body.setUnlockTime(unlockTime);
			
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
	private Date unlockTime;//开锁时间
	private int timecheckcode;//时间校验码
	public Date getUnlockTime() {
		return unlockTime;
	}
	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}
	private int lockcommand;//开锁命令
	private int checkcode;//总校验码
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
