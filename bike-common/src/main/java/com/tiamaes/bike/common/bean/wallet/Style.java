package com.tiamaes.bike.common.bean.wallet;

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
public enum Style implements com.tiamaes.bike.common.Enum<Style> {

	ALIPAY("支付宝"), WECHAT("微信");
	
	private String name;
	
	Style(String name) {
		this.name = name;
	}

	@Override
	@JsonProperty("name")
	public String getName() {
		return this.name;
	}

	@Override
	@JsonProperty("value")
	public String getValue() {
		return name();
	}

	@Override
	@JsonProperty("index")
	public int getIndex() {
		return this.ordinal();
	}
	
	@JsonCreator
	public static Style valueOf(@JsonProperty("index") final int index) {
		Style[] types = Style.values();
		if (index >= 0 && index < types.length) {
			return types[index];
		}
		return null;
	}
	
	public static class Deserializer extends JsonDeserializer<Style> {
		@Override
		public Style deserialize(JsonParser jp, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			JsonToken currentToken = jp.getCurrentToken();
			switch (currentToken) {
			case VALUE_NUMBER_INT:
				return Style.valueOf(jp.getIntValue());
			case VALUE_STRING:
				String text = jp.getText();
				if(StringUtils.isNotBlank(text)){
					return Style.valueOf(text.trim().toUpperCase());
				}
			case START_OBJECT:
				JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
				if(jsonNode != null && jsonNode.isValueNode()){
					text = jsonNode.asText();
					if (StringUtils.isNotBlank(text)) {
						return Style.valueOf(text.trim().toUpperCase());
					}
				}
			default:
				break;
			}
			return null;
		}
	}
}
