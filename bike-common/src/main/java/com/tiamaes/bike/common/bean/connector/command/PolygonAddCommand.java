package com.tiamaes.bike.common.bean.connector.command;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 设置多边形区域
 * @author waibao001
 *
 */
public class PolygonAddCommand extends Command implements Serializable{

	private static final long serialVersionUID = 2560562853818381616L;
	/**
	 * 区域id
	 */
	private int regionId;
	/**
	 * 区域属性
	 */
	private int regionAttr;
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
	/**
	 * 区域总顶点数
	 */
	private int vertexCount;
	/**
	 * 顶点项
	 */
	private List<PolygonAddCommand.VertexProperty> vertexPropertys;
	
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


	public int getVertexCount() {
		return vertexCount;
	}


	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}


	public List<PolygonAddCommand.VertexProperty> getVertexPropertys() {
		return vertexPropertys;
	}


	public void setVertexPropertys(List<PolygonAddCommand.VertexProperty> vertexPropertys) {
		this.vertexPropertys = vertexPropertys;
	}


	public static class VertexProperty{
		
		private double lng;
		private double lat;
		
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
	}
}
