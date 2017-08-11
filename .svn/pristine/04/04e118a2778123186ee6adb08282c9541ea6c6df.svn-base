package com.tiamaes.bike.common.bean.command;

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
import com.tiamaes.bike.common.bean.pay.PayRecharge.Style;

/**
 * 解锁命令
 * @author fyz
 *
 */
public class UnlockCommand extends Command {
	private static final long serialVersionUID = 1L;
	/**
	 * 控制命令
	 */
	@JsonDeserialize(using = Control.Deserializer.class)
	private Control control;
	/**
	 * 车辆ID
	 */
	private String vehicleId;
	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	/**
	 * 驾驶人ID
	 */
	private String driverId;
	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public static enum Control implements com.tiamaes.bike.common.Enum<Control> {

		UNLOCK("开锁"), LOCK("关锁");

		private String name;

		private Control(String name) {
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
		public static Control valueOf(@JsonProperty("index") final int index) {
			Control[] types = Control.values();
			if (index >= 0 && index < types.length) {
				return types[index];
			}
			return null;
		}

		public static class Deserializer extends JsonDeserializer<Control> {
			@Override
			public Control deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
				JsonToken currentToken = jp.getCurrentToken();
				switch (currentToken) {
				case VALUE_NUMBER_INT:
					return Control.valueOf(jp.getIntValue());
				case VALUE_STRING:
					String text = jp.getText();
					if (StringUtils.isNotBlank(text)) {
						return Control.valueOf(text.trim().toUpperCase());
					}
				case START_OBJECT:
					JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
					if (jsonNode != null && jsonNode.isValueNode()) {
						text = jsonNode.asText();
						if (StringUtils.isNotBlank(text)) {
							return Control.valueOf(text.trim().toUpperCase());
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
