package com.tiamaes.bike.common.bean;

import java.io.Serializable;
import java.util.Date;

import com.tiamaes.security.core.userdetails.User;
import com.tiamaes.bike.common.bean.system.Authority;

public class Parameters<T> implements Serializable {
	private static final long serialVersionUID = -6714632496342637170L;
	private User user;
	private Authority authority;
	private Date startTime;
	private Date endTime;
	private T model;

	public Parameters() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
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

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}
}
