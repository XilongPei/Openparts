package com.cnpc.framework.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_dict")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 5569761987303812150L;

    @ForeignShow
    @Header(name = "字典名称")
    @Column(name = "name", length = 200)
    private String name;

    @Header(name = "唯一编码")
    @Column(name = "code", length = 200)
    private String code;

    @Header(name = "字典值")
    @Column(name = "value", length = 200)
    private String value;

    @Header(name = "父级ID")
    @Column(name = "parent_id")
    private String parentId;

    @Header(name = "层级编码")
    @Column(name = "levelCode", length = 36)
    private String levelCode;

    @Header(name = "备注")
    @Column(name = "remark", length = 1000)
    private String remark;

    @Transient
    private String parentName;

    public String getParentName() {

        return parentName;
    }

    public void setParentName(String parentName) {

        this.parentName = parentName;
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

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    public String getLevelCode() {

        return levelCode;
    }

    public void setLevelCode(String levelCode) {

        this.levelCode = levelCode;
    }

    public String getRemark() {

        return remark;
    }

    public void setRemark(String remark) {

        this.remark = remark;
    }

    public String getParentId() {

        return parentId;
    }

    public void setParentId(String parentId) {

        this.parentId = parentId;
    }

}
