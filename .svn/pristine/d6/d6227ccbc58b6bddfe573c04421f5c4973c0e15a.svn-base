package com.tiamaes.bike.common.bean.integrated;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.tiamaes.bike.common.bean.connector.VehicleStatusInfo;
import com.tiamaes.bike.common.bean.connector.VehicleWarningInfo;
import com.tiamaes.bike.common.bean.information.Vehicle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车辆定位记录
 * @author kangty
 */
@ApiModel(value = "LocationRecord", description = "车辆定位记录实体")
public class LocationRecord implements Serializable {
	private static final long serialVersionUID = -4746062426923582077L;
	@ApiModelProperty(value="日志编号")
	private String id;
	@ApiModelProperty(value="借还流水号")
	private String borrowId;
	@ApiModelProperty(value="车辆信息")
	private Vehicle vehicle;
	@ApiModelProperty(value="经度")
	private double lng;
	@ApiModelProperty(value="纬度")
	private double lat;
	@ApiModelProperty(value="电量")
	private int electricity;
	@ApiModelProperty(value="速度")
	private int speed;
	@ApiModelProperty(value="高程")
	private int elevation;
	@ApiModelProperty(value="方向")
	private int direction;
	@ApiModelProperty(value="定位时间")
	private Date createDate;
	@ApiModelProperty(value="车辆状态")
	private VehicleStatusInfo vehicleStatusInfo;
	@ApiModelProperty(value="车辆报警信息")
	private List<VehicleWarningInfo> vehicleWarningInfos;
	@ApiModelProperty(value="车辆进出场信息")
	private VehicleWarningInfo vehicleStationInfo;
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
	
	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
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
	
	public int getElectricity() {
		return electricity;
	}
	
	public void setElectricity(int electricity) {
		this.electricity = electricity;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
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
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public VehicleStatusInfo getVehicleStatusInfo() {
		return vehicleStatusInfo;
	}
	
	public void setVehicleStatusInfo(VehicleStatusInfo vehicleStatusInfo) {
		this.vehicleStatusInfo = vehicleStatusInfo;
	}
	
	public List<VehicleWarningInfo> getVehicleWarningInfos() {
		return vehicleWarningInfos;
	}
	
	public void setVehicleWarningInfos(List<VehicleWarningInfo> vehicleWarningInfos) {
		this.vehicleWarningInfos = vehicleWarningInfos;
	}
	
	public VehicleWarningInfo getVehicleStationInfo() {
		return vehicleStationInfo;
	}
	
	public void setVehicleStationInfo(VehicleWarningInfo vehicleStationInfo) {
		this.vehicleStationInfo = vehicleStationInfo;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
