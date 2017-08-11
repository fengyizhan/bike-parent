package com.tiamaes.bike.common.bean.connector;

import java.io.Serializable;

/**
 * 提问应答
 * @author waibao001
 *
 */
public class QuizAnswer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 应答流水号
	 */
	private short serialNumber;
	/**
	 * 答案id
	 */
	private int answerId;
	
	public short getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(short serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
}
