package com.tiamaes.bike.common.bean.connector;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tiamaes.bike.common.bean.integrated.UseType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreditCardInfo", description = "刷卡借还车信息")
public class CreditCardInfo implements Serializable {

	private static final long serialVersionUID = 7383075197602056312L;

	public CreditCardInfo() {}

	public CreditCardInfo(String id, UseType useType, String bikeId, 
			String cardNumber, Date createDate) {
		this.setId(bikeId);
		this.setUseType(useType);
		this.setBikeId(bikeId);
		this.setCardNumber(cardNumber);
		this.setCreateDate(createDate);
	}

	@ApiModelProperty(value="日志记录id")
	private String id;
	@ApiModelProperty(value="借还车标志(0: 借车, 1: 还车)")
	@JsonDeserialize(using = UseType.Deserializer.class)
	private UseType useType;
	@ApiModelProperty(value="车辆id")
	private String bikeId;
	@ApiModelProperty(value="卡号")
	private String cardNumber;
	@ApiModelProperty(value="创建时间")
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UseType getUseType() {
		return useType;
	}

	public void setUseType(UseType useType) {
		this.useType = useType;
	}

	public String getBikeId() {
		return bikeId;
	}

	public void setBikeId(String bikeId) {
		this.bikeId = bikeId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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
