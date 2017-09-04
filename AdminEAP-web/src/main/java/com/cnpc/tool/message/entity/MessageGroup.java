package com.cnpc.tool.message.entity;

import com.cnpc.framework.annotation.Header;
import com.cnpc.framework.base.entity.BaseEntity;
 import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 消息群组管理
 */
@Entity
@Table(name = "tbl_message_group")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler",	"fieldHandler" })
public class MessageGroup extends BaseEntity {
	
	@Header(name="组名")
	@Column(name = "name")
	private String name;
	
	@Header(name="消息组拥有者")
	@Column(name = "user_id")
	private String userId;

	@Header(name="排序")
	@Column(name="sort")
	private Integer sort;

	@Header(name="备注")
	@Column(name = "remark", length = 1000)
	private String remark;

	@Transient
	private Number userNum;

	public Number getUserNum() {
		return userNum;
	}

	public void setUserNum(Number userNum) {
		this.userNum = userNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



}
