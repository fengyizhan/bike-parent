package com.tiamaes.bike.common.bean;

import java.util.Date;

public class ResponseBody implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private Auto auto;
	private Date time;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Auto getAuto() {
		return auto;
	}

	public void setAuto(Auto auto) {
		this.auto = auto;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
