package com.tiamaes.bike.common.bean.integrated;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public enum Type implements com.tiamaes.bike.common.Enum<Type> {

	APP("APP"), ICCARD("IC卡");

	private String name;

	Type(String name) {
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
	public static Type valueOf(@JsonProperty("index") final int index) {
		Type[] roleTypes = Type.values();
		if (index >= 0 && index < roleTypes.length) {
			return roleTypes[index];
		}
		return null;
	}

	public static class Deserializer extends JsonDeserializer<Type> {
		@Override
		public Type deserialize(JsonParser jp, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			JsonToken currentToken = jp.getCurrentToken();
			switch (currentToken) {
			case VALUE_NUMBER_INT:
				return Type.valueOf(jp.getIntValue());
			case VALUE_STRING:
				String text = jp.getText();
				if (StringUtils.isNotBlank(text)) {
					return Type.valueOf(text.trim().toUpperCase());
				}
			case START_OBJECT:
				JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
				if (jsonNode != null && jsonNode.isValueNode()) {
					text = jsonNode.asText();
					if (StringUtils.isNotBlank(text)) {
						return Type.valueOf(text.trim().toUpperCase());
					}
				}
			default:
				break;
			}
			return null;
		}
	}
}
