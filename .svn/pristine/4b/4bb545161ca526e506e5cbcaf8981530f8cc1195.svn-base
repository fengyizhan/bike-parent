package com.tiamaes.bike.common.bean.information;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 费率信息实体
 * @author kangty
 */
@ApiModel(value = "Rate", description = "费率信息实体")
public class Rate implements Serializable {

	private static final long serialVersionUID = -1758849390082082559L;
	@ApiModelProperty(value="费率id(入库必需)")
	private String id;
	@ApiModelProperty(value="费率时间")
	private float stageTime;
	@ApiModelProperty(value="费率价格")
	private float stagePrice;
	@ApiModelProperty(value="终端状态")
	private Integer stageLevel;
	@ApiModelProperty(value="本条记录入库时间(入库必需)")
	private Date createTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getStageTime() {
		return stageTime;
	}

	public void setStageTime(float stageTime) {
		this.stageTime = stageTime;
	}

	public float getStagePrice() {
		return stagePrice;
	}

	public void setStagePrice(float stagePrice) {
		this.stagePrice = stagePrice;
	}

	public Integer getStageLevel() {
		return stageLevel;
	}

	public void setStageLevel(Integer stageLevel) {
		this.stageLevel = stageLevel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
