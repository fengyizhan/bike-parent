package com.tiamaes.bike.common.bean.connector;

import java.io.IOException;
import java.io.Serializable;

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

public class RegisterResult implements Serializable {

	private static final long serialVersionUID = 7474633750233875678L;
	/**
	 * 注册结果0成功，1车辆已被注册，2数据库中无该车辆，3终端已被注册，4数据库中无该终端
	 */
	@JsonDeserialize(using = RegisterResultType.Deserializer.class)
	private RegisterResultType result;
	/**
	 * 鉴权码(时间戳毫秒数字符串)
	 */
	private String registerNo;
	public RegisterResultType getResult() {
		return result;
	}
	public void setResult(RegisterResultType result) {
		this.result = result;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public static enum RegisterResultType implements com.tiamaes.bike.common.Enum<RegisterResultType> {

		SUCCESS("成功"), VREGISTERED("车辆已被注册"), VNULL("数据库中无该车辆"), TREGISTERED("终端已被注册"), TNULL("数据库中无该终端");

		private String name;

		RegisterResultType(String name) {
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
		public static RegisterResultType valueOf(@JsonProperty("index") final int index) {
			RegisterResultType[] roleTypes = RegisterResultType.values();
			if (index >= 0 && index < roleTypes.length) {
				return roleTypes[index];
			}
			return null;
		}

		public static class Deserializer extends JsonDeserializer<RegisterResultType> {
			@Override
			public RegisterResultType deserialize(JsonParser jp, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				JsonToken currentToken = jp.getCurrentToken();
				switch (currentToken) {
				case VALUE_NUMBER_INT:
					return RegisterResultType.valueOf(jp.getIntValue());
				case VALUE_STRING:
					String text = jp.getText();
					if(StringUtils.isNotBlank(text)){
						return RegisterResultType.valueOf(text.trim().toUpperCase());
					}
				case START_OBJECT:
					JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
					if(jsonNode != null && jsonNode.isValueNode()){
						text = jsonNode.asText();
						if (StringUtils.isNotBlank(text)) {
							return RegisterResultType.valueOf(text.trim().toUpperCase());
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
