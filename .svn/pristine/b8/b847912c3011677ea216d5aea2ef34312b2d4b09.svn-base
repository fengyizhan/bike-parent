package com.tiamaes.bike.common.bean.connector;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

public class ShotResponseInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * simNo
	 */
	private String simNo;
	
	/**
	 * 应答消息流水号
	 */
	private int messageSeqNo;
	/**
	 * 结果
	 */
	private int result;
	/**
	 * 拍摄成功的多媒体个数
	 */
	private short mediaCounts;
	/**
	 * 拍摄成功的多媒体id
	 */
	private List<Integer>  mediaIds;
	/**
	 * 响应时间
	 */
	private Date responseTime;
	/**
	 * 响应内容
	 */
	private String responseContent;
	/**
	 * 响应状态（0：失败，1：成功）
	 */
	@JsonDeserialize(using = ResponseState.Deserializer.class)
	private ResponseState responseState;
	
	public String getSimNo() {
		return simNo;
	}
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
	public int getMessageSeqNo() {
		return messageSeqNo;
	}
	public void setMessageSeqNo(int messageSeqNo) {
		this.messageSeqNo = messageSeqNo;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public short getMediaCounts() {
		return mediaCounts;
	}
	public void setMediaCounts(short mediaCounts) {
		this.mediaCounts = mediaCounts;
	}
	public List<Integer> getMediaIds() {
		return mediaIds;
	}
	public void setMediaIds(List<Integer> mediaIds) {
		this.mediaIds = mediaIds;
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

		FAILD("失败"), SUCCESS("成功");
		
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
