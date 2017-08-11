package com.tiamaes.bike.common.bean.system;

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

public class UserSetting implements Serializable {

	private static final long serialVersionUID = 7074781323328103915L;

	@JsonDeserialize(using = DisplayName.Deserializer.class)
	private DisplayName displayName;

	private Boolean hasAllWarnAuthority;
	private Boolean hasAllDataAuthority;
	private boolean hasUpdateDataAuthorityPower;

	public DisplayName getDisplayName() {
		return displayName;
	}

	public void setDisplayName(DisplayName displayName) {
		this.displayName = displayName;
	}

	public Boolean isHasAllWarnAuthority() {
		return hasAllWarnAuthority;
	}

	public void setHasAllWarnAuthority(Boolean hasAllWarnAuthority) {
		this.hasAllWarnAuthority = hasAllWarnAuthority;
	}

	public Boolean isHasAllDataAuthority() {
		return hasAllDataAuthority;
	}

	public void setHasAllDataAuthority(Boolean hasAllDataAuthority) {
		this.hasAllDataAuthority = hasAllDataAuthority;
	}

	public boolean isHasUpdateDataAuthorityPower() {
		return hasUpdateDataAuthorityPower;
	}

	public void setHasUpdateDataAuthorityPower(boolean hasUpdateDataAuthorityPower) {
		this.hasUpdateDataAuthorityPower = hasUpdateDataAuthorityPower;
	}
	
	
	
	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public static enum DisplayName implements com.tiamaes.bike.common.Enum<DisplayName> {
		
		name("显示车工号"), carNo("显示车牌号"), busSerialNo("显示车自编号"),vinCode("显示VIN码");
		
		private String displayName;
		
		private DisplayName(String displayName){
			this.displayName = displayName;
		}
		
		@Override
		@JsonProperty("value")
		public String getValue() {
			return name();
		}
		@Override
		@JsonProperty("name")
		public String getName() {
			return this.displayName;
		}
		@Override
		@JsonProperty("index")
		public int getIndex(){
			return this.ordinal();
		}
		
		@JsonCreator
		public static DisplayName valueOf(@JsonProperty("index") final int index){
			DisplayName[] values =  DisplayName.values();
			if(index > 0 && index < values.length){
				return values[index];
			}
			return null;
		}
		
		
		public static class Deserializer extends JsonDeserializer<DisplayName> {
			@Override
			public DisplayName deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
				JsonToken currentToken = jp.getCurrentToken();
				switch (currentToken) {
				case VALUE_NUMBER_INT:
					return DisplayName.valueOf(jp.getIntValue());
				case VALUE_STRING:
					String text = jp.getText();
					if(StringUtils.isNotBlank(text)){
						return DisplayName.valueOf(text.trim());
					}
				case START_OBJECT:
					text = ((JsonNode)jp.readValueAsTree()).get("value").asText();
					if(StringUtils.isNotBlank(text)){
						return DisplayName.valueOf(text.trim());
					}
				default:
					break;
				}
				return null;
			}

		}
	}
	
}
