package com.tiamaes.bike.common.bean.connector.command;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Command", description = "命令基类")
public class Command implements Serializable {
	private static final long serialVersionUID = 6877620391004575769L;
	/**
	 * 前台生成ID，用于订阅应答
	 */
	@ApiModelProperty(value="前台生成ID，用于订阅应答")
	private String id;
	/**
	 * 对应车辆车载机sim卡号
	 */
	@ApiModelProperty(value="对应车辆车载机sim卡号")
	private String simNo;
	/**
	 * 请求体
	 */
	private String body;
	
	private String clazz;
	
	private Date createTime;
	
	private Long seqNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}
}
