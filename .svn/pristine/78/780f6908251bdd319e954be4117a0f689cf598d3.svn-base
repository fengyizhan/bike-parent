package com.tiamaes.bike.common.bean.information;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "StatusVehicleInfo", description = "各种状态车辆的信息")
public class StatusVehicleInfo implements Serializable {

	private static final long serialVersionUID = -8269306808134867421L;
	
	public StatusVehicleInfo() {}

	public StatusVehicleInfo(List<Vehicle> online, List<Vehicle> offline, 
			List<Vehicle> lowPower, List<Vehicle> using) {
		this.setOnline(online);
		this.setOffline(offline);
		this.setLowPower(lowPower);
		this.setUsing(using);
	}

	@ApiModelProperty(value="在线车辆信息")
	private List<Vehicle> online;
	@ApiModelProperty(value="离线车辆信息")
	private List<Vehicle> offline;
	@ApiModelProperty(value="低电量车辆信息")
	private List<Vehicle> lowPower;
	@ApiModelProperty(value="正在使用的车辆信息")
	private List<Vehicle> using;

	public List<Vehicle> getOnline() {
		return online;
	}

	public void setOnline(List<Vehicle> online) {
		this.online = online;
	}

	public List<Vehicle> getOffline() {
		return offline;
	}

	public void setOffline(List<Vehicle> offline) {
		this.offline = offline;
	}

	public List<Vehicle> getLowPower() {
		return lowPower;
	}

	public void setLowPower(List<Vehicle> lowPower) {
		this.lowPower = lowPower;
	}

	public List<Vehicle> getUsing() {
		return using;
	}

	public void setUsing(List<Vehicle> using) {
		this.using = using;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
