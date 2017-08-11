package com.tiamaes.bike.common.bean.connector.command;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 设置线路
 * @author waibao001
 *
 */
public class LineAddCommand extends Command implements Serializable{
	private static final long serialVersionUID = 2560562853818381616L;
	/**
	 * 线路id
	 */
	private int lineId;
	/**
	 * 线路属性
	 */
	private int lineAttr;
	/**
	 * 起始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 路线总拐点数
	 */
	private short pointCount; 
	/**
	 * 拐点项
	 */
	
	private List<TurnPoint> turnPoints;
	
	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public int getLineAttr() {
		return lineAttr;
	}

	public void setLineAttr(int lineAttr) {
		this.lineAttr = lineAttr;
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


	public short getPointCount() {
		return pointCount;
	}

	public void setPointCount(short pointCount) {
		this.pointCount = pointCount;
	}

	public List<TurnPoint> getTurnPoints() {
		return turnPoints;
	}

	public void setTurnPoints(List<TurnPoint> turnPoints) {
		this.turnPoints = turnPoints;
	}


	public static class TurnPoint{
		/**
		 * 拐点id
		 */
		private int pointId;
		/**
		 * 路段id
		 */
		private int sectionId;
		/**
		 * 拐点纬度
		 */
		private double lat;
		/**
		 * 拐点经度
		 */
		private double lng;
		/**
		 * 路段宽度
		 */
		private int sectionWidth;
		/**
		 * 路段属性
		 */
		private short sectionAttr;
		/**
		 * 路段行驶过长阈值
		 */
		private short longThreshold;
		/**
		 * 路段行驶不足阈值
		 */
		private short shortThreshold;
		/**
		 * 最高速度
		 */
		private short maxSpeed; 
		/**
		 * 超速持续时间 单位为秒（s），若区域属性 1 位为 0 则没有该字段
		 */
		private int overSpeedS;
		
		public int getPointId() {
			return pointId;
		}
		public void setPointId(int pointId) {
			this.pointId = pointId;
		}
		public int getSectionId() {
			return sectionId;
		}
		public void setSectionId(int sectionId) {
			this.sectionId = sectionId;
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
		public int getSectionWidth() {
			return sectionWidth;
		}
		public void setSectionWidth(int sectionWidth) {
			this.sectionWidth = sectionWidth;
		}
		public short getSectionAttr() {
			return sectionAttr;
		}
		public void setSectionAttr(short sectionAttr) {
			this.sectionAttr = sectionAttr;
		}
		public short getLongThreshold() {
			return longThreshold;
		}
		public void setLongThreshold(short longThreshold) {
			this.longThreshold = longThreshold;
		}
		public short getShortThreshold() {
			return shortThreshold;
		}
		public void setShortThreshold(short shortThreshold) {
			this.shortThreshold = shortThreshold;
		}
		public short getMaxSpeed() {
			return maxSpeed;
		}
		public void setMaxSpeed(short maxSpeed) {
			this.maxSpeed = maxSpeed;
		}
		public int getOverSpeedS() {
			return overSpeedS;
		}
		public void setOverSpeedS(int overSpeedS) {
			this.overSpeedS = overSpeedS;
		}
	};
}
