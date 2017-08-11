package com.tiamaes.bike.common.bean.integrated;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Export", description = "导出实体")
public class Export implements Serializable {
	
	private static final long serialVersionUID = 3326132512421661069L;
	
	/**
	 * 要生成的压缩文件名
	 */
	private String zipFileName;
	
	/**
	 * 当前操作导出的用户id
	 */
	private String userId;

	public String getZipFileName() {
		return zipFileName;
	}

	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
