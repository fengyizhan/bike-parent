package com.tiamaes.bike.connector.protocol.message;

import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;

import com.tiamaes.bike.common.bean.integrated.WarnCode;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 停车点状态包
 * 
 * @author Chen
 */
public class Received0201 extends Received {
	private Body body;
	public Received0201(Header header, byte[] bytes) {
		super(header, bytes);
		CompositeByteBuf buffer = Unpooled.compositeBuffer();
		buffer.writeBytes(bytes);
		int cardflag=buffer.readInt();
		List<WarnCode> Warnflag=new ArrayList<WarnCode>();
		for(WarnCode warncode:WarnCode.values()){
			if(warncode.warnging(cardflag)){
				Warnflag.add(warncode);
			}
		}
		String cardnum=buffer.readInt()+"";
		int bikenum=buffer.readByte();
		Body body=new Body();
		body.setCardflag(Warnflag);
		body.setCardnum(cardnum);
		body.setBikenum(bikenum);
		this.body=body;
	}
	
public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

public class Body{
	private List<WarnCode> cardflag;//刷卡桩设备报警标志
	private String cardnum;//刷卡桩编号
	private int bikenum;//当前车辆总数
	
	public List<WarnCode> getCardflag() {
		return cardflag;
	}
	public void setCardflag(List<WarnCode> cardflag) {
		this.cardflag = cardflag;
	}

	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public int getBikenum() {
		return bikenum;
	}
	public void setBikenum(int bikenum) {
		this.bikenum = bikenum;
	}
	
}
}
