package com.tiamaes.bike.common.bean.information;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 电子围栏的路径点实体
 * @author Chen
 */
@ApiModel(value = "Pathpoint", description = "电子围栏路径点管理")
public class Pathpoint implements Serializable {
	private static final long serialVersionUID = 2088788474812315404L;
	
	@ApiModelProperty(value="坐标点id")
	private int id;
	@ApiModelProperty(value="电子围栏id")
	private int targetid;
	@ApiModelProperty(value="经度")
	private double lng;
	@ApiModelProperty(value="纬度")
	private double lat;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTargetid() {
		return targetid;
	}

	public void setTargetid(int targetid) {
		this.targetid = targetid;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
