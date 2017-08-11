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

@ApiModel(value = "RequestInfo", description = "请求信息实体")
public class RequestInfo implements Serializable {

	private static final long serialVersionUID = -5257331293807892625L;
	
	public RequestInfo() {}
	
	public RequestInfo(String id, String simNo, int messageId, 
			int messageSeqNo, Date requestTime, String requestContent, 
			RequestState requestState) {
		this.setId(id);
		this.setSimNo(simNo);
		this.setMessageId(messageId);
		this.setMessageSeqNo(messageSeqNo);
		this.setRequestTime(requestTime);
		this.setRequestContent(requestContent);
		this.setRequestState(requestState);
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
	private Date requestTime;
	@ApiModelProperty(value="响应内容")
	private String requestContent;
	@ApiModelProperty(value="请求状态(0: 成功/确认 1: 失败 2:消息有误 3: 不支持)")
	@JsonDeserialize(using = RequestState.Deserializer.class)
	private RequestState requestState;
	
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
	
	public Date getRequestTime() {
		return requestTime;
	}
	
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	public String getRequestContent() {
		return requestContent;
	}
	
	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}
	
	public RequestState getRequestState() {
		return requestState;
	}

	public void setRequestState(RequestState requestState) {
		this.requestState = requestState;
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public static enum RequestState implements com.tiamaes.bike.common.Enum<RequestState> {

		SUCCESS("成功/确认"), FAILD("失败"), ERRORMESSAGE("消息有误"), NOTSUPPORT("不支持");
		
		private String name;

		RequestState(String name) {
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
		public static RequestState valueOf(@JsonProperty("index") final int index) {
			RequestState[] roleTypes = RequestState.values();
			if (index >= 0 && index < roleTypes.length) {
				return roleTypes[index];
			}
			return null;
		}

		public static class Deserializer extends JsonDeserializer<RequestState> {
			@Override
			public RequestState deserialize(JsonParser jp, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				JsonToken currentToken = jp.getCurrentToken();
				switch (currentToken) {
				case VALUE_NUMBER_INT:
					return RequestState.valueOf(jp.getIntValue());
				case VALUE_STRING:
					String text = jp.getText();
					if(StringUtils.isNotBlank(text)){
						return RequestState.valueOf(text.trim().toUpperCase());
					}
				case START_OBJECT:
					JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
					if(jsonNode != null && jsonNode.isValueNode()){
						text = jsonNode.asText();
						if (StringUtils.isNotBlank(text)) {
							return RequestState.valueOf(text.trim().toUpperCase());
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
