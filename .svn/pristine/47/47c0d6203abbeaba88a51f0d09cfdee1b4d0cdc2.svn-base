package com.tiamaes.bike.common.bean.information;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 电子围栏信息实体
 * @author kangtianyu
 */
@ApiModel(value = "Station", description = "电子围栏信息实体")
public class Station implements Serializable {
	private static final long serialVersionUID = 423712134844831567L;
	@ApiModelProperty(value="场区编号(入库时必需)")
	private Integer id;
	@ApiModelProperty(value="场区名称(入库时必需)")
	private String name;
	@ApiModelProperty(value="场区地理坐标集")
	private List<Pathpoint> paths;
	@ApiModelProperty(value="创建时间")
	private Date createDate;
	@ApiModelProperty(value="行政区划")
	private Region region;
	@ApiModelProperty(value="所属地区")
	private Property property;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Pathpoint> getPaths() {
		return paths;
	}

	public void setPaths(List<Pathpoint> paths) {
		this.paths = paths;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public static class Property {
		@ApiModelProperty(value="所属省")
		private Region province;
		@ApiModelProperty(value="所属市")
		private Region city;
		@ApiModelProperty(value="所属区/县")
		private Region district;

		public Region getProvince() {
			return province;
		}

		public void setProvince(Region province) {
			this.province = province;
		}

		public Region getCity() {
			return city;
		}

		public void setCity(Region city) {
			this.city = city;
		}

		public Region getDistrict() {
			return district;
		}

		public void setDistrict(Region district) {
			this.district = district;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}

	}
}
