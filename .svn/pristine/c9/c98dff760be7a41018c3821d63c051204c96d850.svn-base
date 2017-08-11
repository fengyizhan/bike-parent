package com.tiamaes.bike.common.bean.connector.command;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文本信息下发请求参数实体
 * @author lsl
 *
 */
@ApiModel(value = "TextCommand", description = "文本信息下发请求参数实体")
public class TextCommand extends Command implements Serializable {
	private static final long serialVersionUID = 2560562853818381616L;
	
	/**
	 * 要发送的消息内容
	 */
	@ApiModelProperty(value="要发送的消息内容")
	private String messageInfo;
	
	public String getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}
}
