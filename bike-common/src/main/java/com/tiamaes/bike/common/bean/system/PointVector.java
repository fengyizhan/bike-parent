package com.tiamaes.bike.common.bean.system;

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

/**
 * 车辆定位信息
 * @author lsl
 *
 */
@ApiModel(value = "PointVector", description = "车辆定位信息")
public class PointVector extends com.tiamaes.bike.common.bean.system.Authority {
	private static final long serialVersionUID = -8071167220890499001L;

	@ApiModelProperty(value="车辆基本信息")
	private PointVector.Info info;
	@ApiModelProperty(value="车辆定位信息")
	private PointVector.Center center;

	public PointVector.Info getInfo() {
		return info;
	}

	public void setInfo(PointVector.Info info) {
		this.info = info;
	}

	public PointVector.Center getCenter() {
		return center;
	}

	public void setCenter(PointVector.Center center) {
		this.center = center;
	}

	/**
	 * 车辆实时信息
	 * @author Chen
	 */
	public static class Info implements Serializable{
		private static final long serialVersionUID = -416081140691731705L;
		@ApiModelProperty(value="最后更新时间")
		private Date date;
		@ApiModelProperty(value="电量")
		private int electricity;
		@ApiModelProperty(value="是否锁车")
		private boolean isLock;
		@ApiModelProperty(value="高程")
		private int elevation;
		@ApiModelProperty(value="当前速度")
		private int speed;
		@ApiModelProperty(value="当前位置")
		private String address;
		@ApiModelProperty(value="方向")
		private int direction;
		@ApiModelProperty(value="车辆当前上下线状态")
		@JsonDeserialize(using=State.Deserializer.class)
		private State state;
		@ApiModelProperty(value="当前借车的用户")
		private User user;
		@ApiModelProperty(value="车辆的当天趟次")
		private int dayTrip;
		@ApiModelProperty(value="定位状态(0：未定位， 1：已定位)")
		private Boolean locationState;
		@ApiModelProperty(value="南北纬(0：北纬，1：南纬)")
		private Boolean lngState;
		@ApiModelProperty(value="东西经(0：东经，1：西经)")
		private Boolean latState;
		@ApiModelProperty(value="车辆状态(0:车辆可借,1:车已被预约,2:车已借出,3:车不可借)")
		@JsonDeserialize(using = RunState.Deserializer.class)
		private RunState runState;
		
		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public int getElevation() {
			return elevation;
		}

		public void setElevation(int elevation) {
			this.elevation = elevation;
		}

		public int getDirection() {
			return direction;
		}

		public void setDirection(int direction) {
			this.direction = direction;
		}

		public int getSpeed() {
			return speed;
		}

		public void setSpeed(int speed) {
			this.speed = speed;
		}

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public int getDayTrip() {
			return dayTrip;
		}

		public void setDayTrip(int dayTrip) {
			this.dayTrip = dayTrip;
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

		public RunState getRunState() {
			return runState;
		}

		public void setRunState(RunState runState) {
			this.runState = runState;
		}
		
		public int getElectricity() {
			return electricity;
		}

		public void setElectricity(int electricity) {
			this.electricity = electricity;
		}

		public boolean isLock() {
			return isLock;
		}

		public void setLock(boolean isLock) {
			this.isLock = isLock;
		}

		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public static enum State implements com.tiamaes.bike.common.Enum<State> {
			
			OFFLINE("离线"), ONLINE("在线"), LOWPOWER("低电量");

			private String name;

			private State(String name) {
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
			public static State valueOf(@JsonProperty("index") final int index) {
				State[] values = State.values();
				if (index >= 0 && index < values.length) {
					return values[index];
				}
				return null;
			}

			public static class Deserializer extends JsonDeserializer<State> {
				@Override
				public State deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
					JsonToken currentToken = jp.getCurrentToken();
					switch (currentToken) {
					case VALUE_STRING:
						String content = jp.getText();
						if (StringUtils.isNumeric(content)) {
							return State.valueOf(Integer.valueOf(content));
						} else if (!StringUtils.startsWith(content, "{")) {
							return State.valueOf(content.trim());
						}
						jp = jp.getCodec().getFactory().createParser(content);
					case VALUE_NUMBER_INT:
						return State.valueOf(jp.getIntValue());
					case START_OBJECT:
						JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
						if (jsonNode != null && jsonNode.isValueNode()) {
							content = jsonNode.asText();
							if (StringUtils.isNotBlank(content)) {
								return State.valueOf(content.trim());
							}
						}
					default:
						break;
					}
					return null;
				}
			}
		}
		
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public static enum RunState implements com.tiamaes.bike.common.Enum<RunState> {
			CANBORROW("车辆可借"), RESERVED("车已被预约"), BORROWED("车已借出"), CANNOTBORROW("车不可借");

			private String name;

			RunState(String name) {
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
			public static RunState valueOf(@JsonProperty("index") final int index) {
				RunState[] types = RunState.values();
				if (index >= 0 && index < types.length) {
					return types[index];
				}
				return null;
			}

			public static class Deserializer extends JsonDeserializer<RunState> {
				@Override
				public RunState deserialize(JsonParser jp, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					JsonToken currentToken = jp.getCurrentToken();
					switch (currentToken) {
					case VALUE_NUMBER_INT:
						return RunState.valueOf(jp.getIntValue());
					case VALUE_STRING:
						String text = jp.getText();
						if (StringUtils.isNotBlank(text)) {
							return RunState.valueOf(text.trim().toUpperCase());
						}
					case START_OBJECT:
						JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
						if (jsonNode != null && jsonNode.isValueNode()) {
							text = jsonNode.asText();
							if (StringUtils.isNotBlank(text)) {
								return RunState.valueOf(text.trim().toUpperCase());
							}
						}
					default:
						break;
					}
					return null;
				}
			}
		}

	};

	public static class Center {
		private double lng;
		private double lat;

		private int rotation;

		public double getLng() {
			return lng;
		}

		public void setLng(double lng) {
			this.lng = lng;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public int getRotation() {
			return rotation;
		}

		public void setRotation(int rotation) {
			this.rotation = rotation;
		}
	};
	
}
