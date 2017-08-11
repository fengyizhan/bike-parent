package com.tiamaes.bike.common.bean.wallet;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "WalletUser", description = "电子钱包用户信息")
public class WalletUser implements Serializable {

	private static final long serialVersionUID = 2025897637408204969L;
	@ApiModelProperty(value="用户名(入库必需)")
	private String username;
	@ApiModelProperty(value="用户余额(入库必需)")
	private volatile float balance;
	@ApiModelProperty(value="用户押金余额(入库必需)")
	private volatile float deposit;
	public float getDeposit() {
		return deposit;
	}

	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
