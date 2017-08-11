package com.tiamaes.bike.connector.protocol.message;

import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;

import com.tiamaes.bike.common.bean.integrated.DirectionType;
import com.tiamaes.bike.common.bean.integrated.WarnCode;
import com.tiamaes.bike.common.bean.system.PointVector.Info.RunState;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 停车点车辆信息包
 * 
 * @author Chen
 */
public class Received0200 extends Received {
	private Body body;
	public Received0200(Header header, byte[] bytes) {
		super(header, bytes);
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		int bikenum=buffer.readUnsignedShort();
		int bikeupflag=buffer.readByte();
		int warning=buffer.readUnsignedShort();
		List<WarnCode> list=new ArrayList<WarnCode>();
		for(WarnCode warncode:WarnCode.values()){
			if(warncode.warnging(warning))
				list.add(warncode);
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
		byte[] bytes1=new byte[4];
	//	buffer.readBytes(bytes1);
		String bikeserial=buffer.readInt()+"";
	//	String bikeserial=Hex.toHexString(bytes1);
		
		Body body=new Body();
		body.setBikenum(bikenum);
		body.setBikeserial(bikeserial);
		body.setBikeupflag(DirectionType.values()[bikeupflag]);
		body.setElectricity(electricity);
		body.setLock(isLock);
		body.setRunState(runState);
		body.setWarning(list);
		this.body=body;
	}
	
public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

public class Body{
	private int bikenum;//车辆总数
	private DirectionType bikeupflag;//车辆上传标志
	private List<WarnCode> warning;//车辆报警标志
	private int electricity;//电量
	private boolean isLock;//锁状态
	private RunState runState;//车状态
	private String bikeserial;//车辆编号
	public int getBikenum() {
		return bikenum;
	}
	public void setBikenum(int bikenum) {
		this.bikenum = bikenum;
	}

	public DirectionType getBikeupflag() {
		return bikeupflag;
	}
	public void setBikeupflag(DirectionType bikeupflag) {
		this.bikeupflag = bikeupflag;
	}
	public List<WarnCode> getWarning() {
		return warning;
	}
	public void setWarning(List<WarnCode> warning) {
		this.warning = warning;
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
	public String getBikeserial() {
		return bikeserial;
	}
	public void setBikeserial(String bikeserial) {
		this.bikeserial = bikeserial;
	}

}
}
