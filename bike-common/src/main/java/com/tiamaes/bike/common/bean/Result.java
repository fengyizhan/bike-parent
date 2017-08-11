package com.tiamaes.bike.common.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * APP接口调用结果
 * @author fyz
 */
@ApiModel(value = "Result", description = "APP接口调用结果")
public class Result implements Serializable {
	private static final long serialVersionUID = 7434510344630312039L;
	public static String TEXT_TYPE="text";
	public static String JSON_TYPE="json";
	@ApiModelProperty(value="数据类型")
	private String type;
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	@ApiModelProperty(value="是否成功")
	private Boolean success=true;
	
	@ApiModelProperty(value="提示消息")
	private String message;
	
	@ApiModelProperty(value="返回数据")
	private Map<String,Object> data=new HashMap<String,Object>();
	

	public Boolean getSuccess() {
		return success;
	}


	public void setSuccess(Boolean success) {
		this.success = success;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Map<String, Object> getData() {
		return data;
	}


	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	

}
