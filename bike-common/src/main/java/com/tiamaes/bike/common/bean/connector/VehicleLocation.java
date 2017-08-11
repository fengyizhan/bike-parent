package com.tiamaes.bike.common.bean.connector;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tiamaes.bike.common.bean.information.Vehicle;
/**
 * 车辆定位信息
 * @author lsl
 *
 */
public class VehicleLocation implements Serializable {

	private static final long serialVersionUID = 4180442590136950593L;
	/**
	 * 日志编号
	 */
	private String id;
	/**
	 * 车辆信息(车辆编号，车牌号，SIM卡号)
	 */
	private Vehicle vehicle;
	/**
	 * 定位时间
	 */
	private Date createTime;
	/**
	 * 经度
	 */
	private double lng;
	/**
	 * 纬度
	 */
	private double lat;
	/**
	 * 高程
	 */
	private int height;
	/**
	 * 速度
	 */
	private double speed;
	/**
	 * 方向
	 */
	private int direction;
	/**
	 * 车辆状态信息
	 */
	private VehicleStatusInfo vehicleStatusInfo;
	/**
	 * 车辆报警信息
	 */
	private List<VehicleWarningInfo> vehicleWarningInfos;
	
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
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
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
	
}
