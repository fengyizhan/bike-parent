package com.tiamaes.bike.common.bean.information;

import org.apache.commons.lang.builder.ToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "FactoryDictionary", description = "数据字典：生产厂家")
public class FactoryDictionary {

	@ApiModelProperty(value="生产厂家编号")
	private String id;
	@ApiModelProperty(value="生产厂家名称")
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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
