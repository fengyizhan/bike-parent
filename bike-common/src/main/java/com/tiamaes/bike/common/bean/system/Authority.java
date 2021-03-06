package com.tiamaes.bike.common.bean.system;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Authority", description = "权限信息")
public class Authority implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="主键")
	private String id;
	@ApiModelProperty(value="编号或名称")
	private String name;
	@ApiModelProperty(value="级别")
	private Integer level;
	@ApiModelProperty(value="父id")
	private String parentId;
	@ApiModelProperty(value="图标状态")
	private String iconSkin;
	@ApiModelProperty(value="是否打开")
	private Boolean open;
	@ApiModelProperty(value="是否选中")
	private Boolean checked;
	@ApiModelProperty(value="子集合")
	private List<? extends Authority> children;

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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<? extends Authority> getChildren() {
		return children;
	}

	public void setChildren(List<? extends Authority> children) {
		this.children = children;
	}

}
