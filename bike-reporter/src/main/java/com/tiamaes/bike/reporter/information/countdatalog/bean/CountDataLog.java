package com.tiamaes.bike.reporter.information.countdatalog.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CountData", description = "总计数据")
public class CountDataLog implements Serializable {

	private static final long serialVersionUID = 9077552558171696663L;
	
	public CountDataLog(){
		
	}
	
	public CountDataLog(int orderNum, double money, double avgTime) {
		this.setOrderNum(orderNum);
		this.setMoneyNum(money);
		this.setTimeNum(avgTime);
	}
	
	@ApiModelProperty(value="总订单数")
	private int orderNum;
	@ApiModelProperty(value="总成交额(元)")
	private double moneyNum;
	@ApiModelProperty(value="平均骑行时长(分钟)")
	private double timeNum;

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public double getMoneyNum() {
		return moneyNum;
	}

	public void setMoneyNum(double moneyNum) {
		this.moneyNum = moneyNum;
	}

	public double getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(double timeNum) {
		this.timeNum = timeNum;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
