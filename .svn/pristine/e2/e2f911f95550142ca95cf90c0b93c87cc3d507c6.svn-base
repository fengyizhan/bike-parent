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
import com.tiamaes.bike.common.bean.information.Vehicle;

public class VehicleStatusInfo implements Serializable {

	private static final long serialVersionUID = -1951169085195649289L;
	
	/**
	 * 日志编号
	 */
	private String id;
	/**
	 * 车辆信息(车辆编号，车牌号，SIM卡号)
	 */
	private Vehicle vehicle;
	/**
	 * 定位状态(0：未定位， 1：已定位)
	 */
	private Boolean locationState;
	/**
	 * 南北纬(0：北纬，1：南纬)
	 */
	private Boolean lngState;
	/**
	 * 东西经(0：东经，1：西经)
	 */
	private Boolean latState;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Boolean getLocationState() {
		return locationState;
	}

	public void setLocationState(Boolean locationState) {
		this.locationState = locationState;
	}

	public Boolean getLngState() {
		return lngState;
	}

	public void setLngState(Boolean lngState) {
		this.lngState = lngState;
	}

	public Boolean getLatState() {
		return latState;
	}

	public void setLatState(Boolean latState) {
		this.latState = latState;
	}


	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public static enum LoadState implements com.tiamaes.bike.common.Enum<LoadState> {
		//00：空车；01：半载；10：保留；11：满载
		NOLOAD("空车"), HALFLOAD("半载"), RESERVE("保留"), FULLLOAD("满载");

		private String name;

		LoadState(String name) {
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
		public static LoadState valueOf(@JsonProperty("index") final int index) {
			LoadState[] roleTypes = LoadState.values();
			if (index >= 0 && index < roleTypes.length) {
				return roleTypes[index];
			}
			return null;
		}

		public static class Deserializer extends JsonDeserializer<LoadState> {
			@Override
			public LoadState deserialize(JsonParser jp, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				JsonToken currentToken = jp.getCurrentToken();
				switch (currentToken) {
				case VALUE_NUMBER_INT:
					return LoadState.valueOf(jp.getIntValue());
				case VALUE_STRING:
					String text = jp.getText();
					if (StringUtils.isNotBlank(text)) {
						return LoadState.valueOf(text.trim().toUpperCase());
					}
				case START_OBJECT:
					JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
					if (jsonNode != null && jsonNode.isValueNode()) {
						text = jsonNode.asText();
						if (StringUtils.isNotBlank(text)) {
							return LoadState.valueOf(text.trim().toUpperCase());
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
