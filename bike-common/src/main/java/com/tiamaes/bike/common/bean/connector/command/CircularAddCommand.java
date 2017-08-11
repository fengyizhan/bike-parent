package com.tiamaes.bike.common.bean.connector.command;

import java.io.Serializable;
import java.util.Date;

/**
 * 设置圆形区域
 * @author waibao001
 *
 */
public class CircularAddCommand extends Command implements Serializable{

	private static final long serialVersionUID = 2560562853818381616L;
	
	/**
	 * 设置属性 	0：更新区域；1：追加区域；2：修改区域
	 */
	private int setAttr;
	/**
	 * 区域总数
	 */
	private int circularCounts;
	/**
	 * 区域id
	 */
	private int regionId;
	/**
	 * 区域属性
	 */
	private int regionAttr;
	/**
	 * 中心点纬度
	 */
	private double lat;
	/**
	 * 中心点经度
	 */
	private double lng;
	/**
	 * 半径
	 */
	private double radius;
	/**
	 * 起始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 最高速度
	 */
	private short maxSpeed; 
	/**
	 * 超速持续时间 单位为秒（s），若区域属性 1 位为 0 则没有该字段
	 */
	private short overSpeedS;
	
	
	public int getSetAttr() {
		return setAttr;
	}
	public void setSetAttr(int setAttr) {
		this.setAttr = setAttr;
	}
	public int getCircularCounts() {
		return circularCounts;
	}
	public void setCircularCounts(int circularCounts) {
		this.circularCounts = circularCounts;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getRegionAttr() {
		return regionAttr;
	}
	public void setRegionAttr(int regionAttr) {
		this.regionAttr = regionAttr;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
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
	public short getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(short maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public short getOverSpeedS() {
		return overSpeedS;
	}
	public void setOverSpeedS(short overSpeedS) {
		this.overSpeedS = overSpeedS;
	}
}
