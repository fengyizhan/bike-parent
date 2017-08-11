package com.tiamaes.bike.common.bean;

import java.io.Serializable;

/**
 * 极光推送消息体
 * @author fyz
 */
public class JPushContent implements Serializable{

	private static final long serialVersionUID = 3288489469129501148L;
	private String mobiles;
	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private String content;
}
