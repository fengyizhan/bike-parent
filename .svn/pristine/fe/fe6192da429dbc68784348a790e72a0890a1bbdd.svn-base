package com.tiamaes.bike.common.bean.connector;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ResponseInfo", description = "应答信息实体")
public class ResponseInfo implements Serializable {
	
	private static final long serialVersionUID = -860507905645205410L;
	
	public ResponseInfo() {
		super();
	}

	public ResponseInfo(String id, String simNo, int messageId, 
			int messageSeqNo, Date responseTime, String responseContent, 
			ResponseState responseState) {
		this.setId(id);
		this.setSimNo(simNo);
		this.setMessageId(messageId);
		this.setMessageSeqNo(messageSeqNo);
		this.setResponseTime(responseTime);
		this.setResponseContent(responseContent);
		this.setResponseState(responseState);
	}

	@ApiModelProperty(value="主键id")
	private String id;
	@ApiModelProperty(value="sim卡号")
	private String simNo;
	@ApiModelProperty(value="消息编号")
	private int messageId;
	@ApiModelProperty(value="消息流水号")
	private int messageSeqNo;
	@ApiModelProperty(value="响应时间")
	private Date responseTime;
	@ApiModelProperty(value="响应内容")
	private String responseContent;
	@ApiModelProperty(value="响应状态(0: 成功/确认 1: 失败 2:消息有误 3: 不支持 4: 报警处理确认)")
	@JsonDeserialize(using = ResponseState.Deserializer.class)
	private ResponseState responseState;
	
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

	public int getMessageId() {
		return messageId;
	}
	
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	
	public int getMessageSeqNo() {
		return messageSeqNo;
	}
	
	public void setMessageSeqNo(int messageSeqNo) {
		this.messageSeqNo = messageSeqNo;
	}
	
	public Date getResponseTime() {
		return responseTime;
	}
	
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	
	public String getResponseContent() {
		return responseContent;
	}
	
	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}
	
	public ResponseState getResponseState() {
		return responseState;
	}
	
	public void setResponseState(ResponseState responseState) {
		this.responseState = responseState;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public static enum ResponseState implements com.tiamaes.bike.common.Enum<ResponseState> {

		SUCCESS("成功/确认"), FAILD("失败"), ERRORMESSAGE("消息有误"), NOTSUPPORT("不支持"), WARNENSURE("报警处理确认");
		
		private String name;

		ResponseState(String name) {
			this.name = name;
		}

		@Override
		@JsonProperty("value")
		public String getValue() {
			return name();
		}

		@Override
		@JsonProperty("name")
		public String getName() {
			return this.name;
		}

		@Override
		@JsonProperty("index")
		public int getIndex() {
			return this.ordinal();
		}

		@JsonCreator
		public static ResponseState valueOf(@JsonProperty("index") final int index) {
			ResponseState[] roleTypes = ResponseState.values();
			if (index >= 0 && index < roleTypes.length) {
				return roleTypes[index];
			}
			return null;
		}

		public static class Deserializer extends JsonDeserializer<ResponseState> {
			@Override
			public ResponseState deserialize(JsonParser jp, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				JsonToken currentToken = jp.getCurrentToken();
				switch (currentToken) {
				case VALUE_NUMBER_INT:
					return ResponseState.valueOf(jp.getIntValue());
				case VALUE_STRING:
					String text = jp.getText();
					if(StringUtils.isNotBlank(text)){
						return ResponseState.valueOf(text.trim().toUpperCase());
					}
				case START_OBJECT:
					JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
					if(jsonNode != null && jsonNode.isValueNode()){
						text = jsonNode.asText();
						if (StringUtils.isNotBlank(text)) {
							return ResponseState.valueOf(text.trim().toUpperCase());
						}
					}
				default:
					break;
				}
				return null;
			}
		}
	}
	
}
