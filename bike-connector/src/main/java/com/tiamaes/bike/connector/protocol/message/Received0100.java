package com.tiamaes.bike.connector.protocol.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;

import com.tiamaes.bike.common.bean.integrated.WarnCode;
import com.tiamaes.bike.common.bean.system.PointVector.Info.RunState;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 位置及车辆状态信息汇报包
 * 
 * @author lzf
 */
public class Received0100 extends Received {
	private Body body;
	public Received0100(Header header, byte[] bytes)  {
		super(header, bytes);
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		int warnFlag=buffer.readUnsignedShort();
		List<WarnCode> warncode=new ArrayList<WarnCode>();
		for(WarnCode warnCode:WarnCode.values()){
			if(warnCode.warnging(warnFlag))
				warncode.add(warnCode);
		}
		int electricity=buffer.readByte();
		int bikeStatus=buffer.readByte();
		boolean isLock=false;
		if((bikeStatus&0x0001)==0x0001){
			isLock=true;
		}
		RunState runState = null;
		if((bikeStatus&0x0110)==0x0110){
			runState = RunState.CANNOTBORROW;
		}else if((bikeStatus&0x0100)==0x0010){
			runState=RunState.BORROWED;
		}else if((bikeStatus&0x0010)==0x0010){
			runState=RunState.RESERVED;
		}else{
			runState=RunState.CANBORROW;
		}
		
		int position=buffer.readByte();
		boolean location;
		if((position&0x0001)==0x0001){
			location=true;
		}else{
			location=false;
		}
		boolean lngState;
		if((position&0x0010)==0x0010){
			lngState=true;
		}else{
			lngState=false;
		}
		boolean latState;
		if((position&0x0100)==0x0100){
			latState=true;
		}else{
			latState=false;
		}
		byte[] bytes1=new byte[4];
		buffer.readBytes(bytes1);		
		int atitudetemp=Integer.parseInt(Hex.toHexString(bytes1),16);
		double atitude=(double)atitudetemp/(Math.pow(10, 6));
		byte[] bytes2=new byte[4];
		buffer.readBytes(bytes2);
		int longitudetemp=Integer.parseInt(Hex.toHexString(bytes2),16);
		double longitude=(double)longitudetemp/(Math.pow(10, 6));

		int height=buffer.readUnsignedShort();
		int velocity=buffer.readUnsignedShort();
		int direction=buffer.readUnsignedShort();
		byte[] bytes3=new byte[6];
		buffer.readBytes(bytes3);
		String temp=Hex.toHexString(bytes3);
		SimpleDateFormat sdf=new SimpleDateFormat("YYMMDDhhmmss");
		byte[] bytes4=new byte[2];
		buffer.readBytes(bytes4);
		String s=Hex.toHexString(bytes4);
		int voltage=Integer.valueOf(s, 10);
				
		float bikevoltage=(float)voltage/1000;
		Body body=new Body();
			try {
				Date time = sdf.parse(temp);
				body.setTime(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		body.setLocationstate(location);
		body.setLock(isLock);
		body.setRunState(runState);
		body.setLatState(latState);
		body.setLngState(lngState);
		body.setElectricity(electricity);
		body.setWarnFlag(warncode);
		body.setAtitude(atitude);
		body.setDirection(direction);
		body.setHeight(height);
		body.setLongitude(longitude);
		body.setBikevoltage(bikevoltage);
		this.body=body;
	}
	
public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

public class Body{
	private List<WarnCode> warnFlag;//报警标志
	private int electricity;//电量
	private boolean isLock;//锁状态
	private RunState runState;//车状态
	private boolean locationstate;//是否定位
	private boolean lngState;//定位纬度
	private boolean latState;//定位经度
	private double atitude;//纬度
	private double longitude;//经度
	private int height;//高程
	private int velocity;//速度
	private int direction;//方向
	private Date time;//时间
	private float bikevoltage;//车辆电压


	public List<WarnCode> getWarnFlag() {
		return warnFlag;
	}
	public void setWarnFlag(List<WarnCode> warnFlag) {
		this.warnFlag = warnFlag;
	}
	
	public int getElectricity() {
		return electricity;
	}
	public void setElectricity(int electricity) {
		this.electricity = electricity;
	}

	public boolean isLock() {
		return isLock;
	}
	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}
	public RunState getRunState() {
		return runState;
	}
	public void setRunState(RunState runState) {
		this.runState = runState;
	}

	public boolean isLocationstate() {
		return locationstate;
	}
	public void setLocationstate(boolean locationstate) {
		this.locationstate = locationstate;
	}
	public boolean isLngState() {
		return lngState;
	}
	public void setLngState(boolean lngState) {
		this.lngState = lngState;
	}
	public boolean isLatState() {
		return latState;
	}
	public void setLatState(boolean latState) {
		this.latState = latState;
	}
	public double getAtitude() {
		return atitude;
	}
	public void setAtitude(double atitude) {
		this.atitude = atitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getVelocity() {
		return velocity;
	}
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public float getBikevoltage() {
		return bikevoltage;
	}
	public void setBikevoltage(float bikevoltage) {
		this.bikevoltage = bikevoltage;
	}
	
}

}
