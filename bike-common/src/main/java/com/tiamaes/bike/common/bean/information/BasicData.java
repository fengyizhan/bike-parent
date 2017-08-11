package com.tiamaes.bike.common.bean.information;

import java.util.Date;

/**
 * 数据字典项
 * @author lenovo
 *
 */
public class BasicData {
	
	private int baseId;
	private String name;//名称
	private String code;//编码
	private String parent;//父编码
	private String meta;//扩展定义数据
	private int deleted;//是否删除0未删除
	private Date createDate;//创建时间
	private int tenantId;
	private String remark;//数据项注释
	private String owner;//所有人，或者说创建人
	private int sn;//用于排序的序号 0
	public int getBaseId() {
		return baseId;
	}
	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getTenantId() {
		return tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getSn() {
		return sn;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	
}
