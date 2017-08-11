package com.tiamaes.bike.common.bean.integrated;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.system.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ParkVehicleUseRecord", description = "停车区车辆使用实体")
public class ParkVehicleUseRecord implements Serializable {

	private static final long serialVersionUID = 7106339318816093912L;

	public ParkVehicleUseRecord() {}

	public ParkVehicleUseRecord(String id, Park park, Vehicle vehicle, 
			User user, UseType useType, double kilometers, double cost, 
			Type type, Date createDate) {
		this.setId(id);
		this.setPark(park);
		this.setVehicle(vehicle);
		this.setUser(user);
		this.setUseType(useType);
		this.setKilometers(kilometers);
		this.setCost(cost);
		this.setType(type);
		this.setCreateDate(createDate);
	}

	@ApiModelProperty(value="日志编号")
	private String id;
	@ApiModelProperty(value="停放区信息")
	private Park park;
	@ApiModelProperty(value="车辆信息")
	private Vehicle vehicle;
	@ApiModelProperty(value="用户信息")
	private User user;
	@ApiModelProperty(value="使用类型(0: 借车, 1: 还车)")
	@JsonDeserialize(using = UseType.Deserializer.class)
	private UseType useType;
	@ApiModelProperty(value="公里数")
	private double kilometers;
	@ApiModelProperty(value="花费")
	private double cost;
	@ApiModelProperty(value="类型(0: App, 1: IC卡)")
	@JsonDeserialize(using = Type.Deserializer.class)
	private Type type;
	@ApiModelProperty(value="创建时间")
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Park getPark() {
		return park;
	}

	public void setPark(Park park) {
		this.park = park;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UseType getUseType() {
		return useType;
	}

	public void setUseType(UseType useType) {
		this.useType = useType;
	}

	public double getKilometers() {
		return kilometers;
	}

	public void setKilometers(double kilometers) {
		this.kilometers = kilometers;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
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
