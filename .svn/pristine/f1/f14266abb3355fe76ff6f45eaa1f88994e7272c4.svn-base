/**
 * 
 */
package com.tiamaes.bike.common.bean.system;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Chen
 * 
 */
@ApiModel(value = "Resource", description = "资源实体")
public class Resource implements Serializable {
	private static final long serialVersionUID = -9014317397898636555L;

	@ApiModelProperty(value="资源id")
	private String id;
	@ApiModelProperty(value="资源名(新增时必填, 查询时可不填)")
	private String name;
	@ApiModelProperty(value="资源访问路径(相对路径)")
	private String path;
	@ApiModelProperty(value="父资源id(新增时必填, 查询时可不填)")
	private String parentId;
	@ApiModelProperty(value="资源排名")
	private int rank;
	@ApiModelProperty(value="资源排序(新增时必填, 查询时可不填)")
	private int orderNo;
	@ApiModelProperty(value="资源图标路径(相对路径)")
	private String ico;
	@ApiModelProperty(value="子资源列表")
	private List<Resource> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

}
