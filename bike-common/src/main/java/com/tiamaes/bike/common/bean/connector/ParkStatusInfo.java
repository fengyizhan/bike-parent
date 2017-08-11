package com.tiamaes.bike.common.bean.connector;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.integrated.WarnCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ParkStatusInfo", description = "借车点状态信息实体")
public class ParkStatusInfo implements Serializable {

	private static final long serialVersionUID = 7604752196771519835L;
	
	public ParkStatusInfo(){}
	
	public ParkStatusInfo(String id, WarnCode warnCode, Park park,
			int vehicles, Date createDate) {
		this.setId(id);
		this.setWarnCode(warnCode);
		this.setPark(park);
		this.setVehicles(vehicles);
		this.setCreateDate(createDate);
	}

	@ApiModelProperty(value="日志记录id")
	private String id;
	@ApiModelProperty(value="停车点报警信息")
	@JsonDeserialize(using = WarnCode.Deserializer.class)
	private WarnCode warnCode;
	@ApiModelProperty(value="停车点相关信息")
	private Park park;
	@ApiModelProperty(value="停车点当前停车总数0-255")
	private int vehicles;
	@ApiModelProperty(value="创建时间")
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WarnCode getWarnCode() {
		return warnCode;
	}

	public void setWarnCode(WarnCode warnCode) {
		this.warnCode = warnCode;
	}

	public Park getPark() {
		return park;
	}

	public void setPark(Park park) {
		this.park = park;
	}

	public int getVehicles() {
		return vehicles;
	}

	public void setVehicles(int vehicles) {
		this.vehicles = vehicles;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
