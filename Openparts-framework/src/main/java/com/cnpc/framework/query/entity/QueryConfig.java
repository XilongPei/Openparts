package com.cnpc.framework.query.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import com.cnpc.framework.utils.StrUtil;

@Entity
@Table(name = "query_config")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class QueryConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4716945141383027168L;

	@Id
	@Column(name = "id", length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@JsonProperty("id")
	private String id;
	/**
	 * 用户id
	 */
	@Column(name = "userid", length = 36)
	private String userid;
	/**
	 * 配置XML queryid
	 */
	@Column(name = "queryId")
	private String queryId;
	/**
	 * 界面url
	 */
	@Column(name = "pageName", length = 500)
	private String pageName;
	
	/**
	 * 显示的Column key
	 */
	@Column(length=2000,name="columnsName")
	private String columnsName;

	@Transient
	public List<String> getColumns() {
		if (StrUtil.isBlank(this.columnsName) == true) {
			return null;
		}
		return Arrays.asList(this.columnsName.split(","));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}


	public String getColumnsName() {
		return columnsName;
	}

	public void setColumnsName(String columnsName) {
		this.columnsName = columnsName;
	}


}
