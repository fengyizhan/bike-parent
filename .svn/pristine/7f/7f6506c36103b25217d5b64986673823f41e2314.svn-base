package com.tiamaes.bike.common.bean.system;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登陆注册PO实体类
 * @author fyz
 */
@Api(tags = {"loginModel"}, description = "用户登陆注册")
public class LoginModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="手机号码")
	private String username;
	@ApiModelProperty(value="登陆密码（短信验证码/自定义系统密码）")
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
