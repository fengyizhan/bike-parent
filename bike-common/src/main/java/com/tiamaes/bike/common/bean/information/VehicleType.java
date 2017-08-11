package com.tiamaes.bike.common.bean.information;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车辆类型字典表
 * @author waibao001
 *
 */
@ApiModel(value = "VehicleType", description = "车辆类型字典表")
public class VehicleType {
	/**
	 * 车辆主键
	 */
	@ApiModelProperty(value="车辆主键")
	private String id;
	/**
	 * 车辆名称
	 */
	@ApiModelProperty(value="车辆名称")
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
