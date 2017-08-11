package com.tiamaes.bike.common.bean.system;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tiamaes.security.core.DefaultGrantedAuthority;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色实体类
 * 
 * @author ls
 * @date 2014-8-20 下午5:15:28
 */
@ApiModel(value = "Role", description = "角色实体")
public class Role extends DefaultGrantedAuthority {
	private static final long serialVersionUID = -9118721073078126986L;

	@ApiModelProperty(value="角色类型(新增时必填, 查询时可不填)")
	@JsonProperty("type")
	@JsonDeserialize(using = Role.Type.Deserializer.class)
	private Type type; // 角色类型
	@ApiModelProperty(value="创建时间")
	private Date createtime; // 创建时间
	@ApiModelProperty(value="备注")
	private String remarks; // 备注
	@ApiModelProperty(value="图标(相对路径)")
	private String icon; // 图标
	
	public Role() {
	}
	
	public Role(String authority) {
		super(authority, authority);
	}
	
	public Role(String authority, String alias) {
		super(authority, alias);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public static enum Type implements com.tiamaes.bike.common.Enum<Type> {

		ADMINISTRATOR("管理角色"), OPERATOR("运维角色"), GROUP("分组角色"), USER("用户角色");

		private String name;

		Type(String name) {
			this.name = name;
		}

		@Override
		@JsonProperty("value")
		public String getValue() {
			return name();
		}

		@Override
		@JsonProperty("name")
		public String getName() {
			return this.name;
		}

		@Override
		@JsonProperty("index")
		public int getIndex() {
			return this.ordinal();
		}

		@JsonCreator
		public static Type valueOf(@JsonProperty("index") final int index) {
			Type[] roleTypes = Type.values();
			if (index > 0 && index < roleTypes.length) {
				return roleTypes[index];
			}
			return null;
		}

		public static class Deserializer extends JsonDeserializer<Type> {
			@Override
			public Type deserialize(JsonParser jp, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				JsonToken currentToken = jp.getCurrentToken();
				switch (currentToken) {
				case VALUE_NUMBER_INT:
					return Type.valueOf(jp.getIntValue());
				case VALUE_STRING:
					String text = jp.getText();
					if(StringUtils.isNotBlank(text)){
						return Type.valueOf(text.trim().toUpperCase());
					}
				case START_OBJECT:
					/*JsonNode jNode = (JsonNode)jp.readValueAsTree();
					JsonNode nodeValue = jNode.get("value");*/
					JsonNode jsonNode = ((JsonNode) jp.readValueAsTree()).get("value");
					if(jsonNode != null && jsonNode.isValueNode()){
						text = jsonNode.asText();
						if (StringUtils.isNotBlank(text)) {
							return Type.valueOf(text.trim().toUpperCase());
						}
					}
					
				default:
					break;
				}
				return null;
			}

		}

	}
}
