package com.tiamaes.bike.common.bean.integrated;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.utils.UUIDGenerator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车辆报警记录
 * @author kangty
 */
@ApiModel(value = "AlarmRecord", description = "车辆报警记录实体")
public class AlarmRecord extends Export {

	private static final long serialVersionUID = 4013755153677966283L;

	public AlarmRecord() {
	}

	public AlarmRecord(Vehicle vehicle, WarnCode warnCode, Date createDate, double lng, double lat) {
		this.setId(UUIDGenerator.getUUID());
		this.setVehicle(vehicle);
		this.setWarnCode(warnCode);
		this.setWarnContent(this.getWarnCode().getName());
		this.setCreateDate(createDate);
		this.setLng(lng);
		this.setLat(lat);
	}

	@ApiModelProperty(value="日志编号")
	private String id;
	@ApiModelProperty(value="车辆信息")
	private Vehicle vehicle;
	@ApiModelProperty(value="报警码")
	@JsonDeserialize(using = WarnCode.Deserializer.class)
	private WarnCode warnCode;
	@ApiModelProperty(value="报警内容")
	private String warnContent;
	@ApiModelProperty(value="进出区域类型(0：进，1：出)")
	@JsonDeserialize(using = DirectionType.Deserializer.class)
	private DirectionType directionType;
	@ApiModelProperty(value="报警时间")
	private Date createDate;
	@ApiModelProperty(value="经度")
	private double lng;
	@ApiModelProperty(value="纬度")
	private double lat;
	@ApiModelProperty(value="报警地点")
	private String place;
	@ApiModelProperty(value="查询条件(开始时间)")
	private Date startTime;
	@ApiModelProperty(value="查询条件(结束时间)")
	private Date endTime;

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

	public WarnCode getWarnCode() {
		return warnCode;
	}

	public void setWarnCode(WarnCode warnCode) {
		this.warnCode = warnCode;
	}

	public String getWarnContent() {
		return warnContent;
	}

	public void setWarnContent(String warnContent) {
		this.warnContent = warnContent;
	}

	public DirectionType getDirectionType() {
		return directionType;
	}

	public void setDirectionType(DirectionType directionType) {
		this.directionType = directionType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
