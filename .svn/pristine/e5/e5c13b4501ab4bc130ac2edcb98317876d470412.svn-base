package com.tiamaes.bike.common.bean.integrated;

import java.io.Serializable;
import java.util.Date;

public class Task extends Schedule implements Serializable{

	private static final long serialVersionUID = 3714171290291463820L;
	
	public Task(){
		
	}
	
	public Task(String id, String userId, Progress progress) {
		super.setId(id);
		super.setUserId(userId);
		this.fileName = id + ".zip";
		super.setProgress(progress);
	}
	
	/** 文件名 */
	private String fileName;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 需要完成的任务 */
	private Export export;
	
	/** 是否已经下载(0:未下载, 1:已下载) */
	private int download;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Export getExport() {
		return export;
	}

	public void setExport(Export export) {
		this.export = export;
	}

	public int getDownload() {
		return download;
	}

	public void setDownload(int download) {
		this.download = download;
	}
	
}
