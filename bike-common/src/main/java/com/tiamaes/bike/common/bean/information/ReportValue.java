package com.tiamaes.bike.common.bean.information;

import java.io.IOException;

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


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReportValue implements com.tiamaes.bike.common.Enum<ReportValue> {

	VALUE0("超速报警"),
	VALUE1("进入区域报警给驾驶员"), 
	VALUE2("进入区域报警给平台"), 
	VALUE3("离开区域报警给驾驶员"),
	VALUE4("离开区域报警给平台");

	private String name;

	private ReportValue(String name) {
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
		return 1 << ordinal();
	}

	@JsonCreator
	public static ReportValue valueOf(@JsonProperty("index") final int index) {
		ReportValue[] types = ReportValue.values();
		if (index >= 0 && index < types.length) {
			return types[index];
		}
		return null;
	}

	public static class Deserializer extends JsonDeserializer<ReportValue> {
		@Override
		public ReportValue deserialize(JsonParser jp, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			JsonToken currentToken = jp.getCurrentToken();
			switch (currentToken) {
			case VALUE_NUMBER_INT:
				return ReportValue.valueOf(jp.getIntValue());
			case VALUE_STRING:
				String text = jp.getText();
				if (StringUtils.isNotBlank(text)) {
					return ReportValue.valueOf(text.trim().toUpperCase());
				}
			case START_OBJECT:
				JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
				if (jsonNode != null && jsonNode.isValueNode()) {
					text = jsonNode.asText();
					if (StringUtils.isNotBlank(text)) {
						return ReportValue.valueOf(text.trim().toUpperCase());
					}
				}
			default:
				break;
			}
			return null;
		}
	}
}
