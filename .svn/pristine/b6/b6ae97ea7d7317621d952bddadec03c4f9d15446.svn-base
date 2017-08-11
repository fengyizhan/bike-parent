package com.tiamaes.bike.api.information.countdata.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CountData", description = "总计数据")
public class CountData implements Serializable {

	private static final long serialVersionUID = 9077552558171696663L;
	
	public CountData(){
		
	}
	
	public CountData(int bikeNum, int parkNum, int userNum) {
		this.setBikeNum(bikeNum);
		this.setParkNum(parkNum);
		this.setUserNum(userNum);
	}
	
	@ApiModelProperty(value="总车辆数")
	private int bikeNum;
	@ApiModelProperty(value="总站点数")
	private int parkNum;
	@ApiModelProperty(value="总用户数")
	private int userNum;

	public int getBikeNum() {
		return bikeNum;
	}

	public void setBikeNum(int bikeNum) {
		this.bikeNum = bikeNum;
	}

	public int getParkNum() {
		return parkNum;
	}

	public void setParkNum(int parkNum) {
		this.parkNum = parkNum;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
