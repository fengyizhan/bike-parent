package com.tiamaes.bike.common.bean.wallet;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 电子钱包消费记录
 * @author kangty
 */
@ApiModel(value = "Consume", description = "电子钱包消费记录")
public class Consume implements Serializable {

	private static final long serialVersionUID = -229597684452007827L;
	@ApiModelProperty(value="主键(入库必需)")
	private Integer id;
	@ApiModelProperty(value="用户名(入库必需)")
	private String username;
	@ApiModelProperty(value="消费金额(入库必需)")
	private volatile float money;
	@ApiModelProperty(value="入库时间(入库必需)")
	private Date createTime;
	@ApiModelProperty(value="开始时间(查询条件)")
	private Date startTime;
	@ApiModelProperty(value="结束时间(查询条件)")
	private Date endTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
	
}
