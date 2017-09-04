package com.cnpc.framework.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_role")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Role extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 8549799122579486310L;

    @ForeignShow
    @Header(name = "名称")
    @Column(name = "name", length = 50)
    private String name;

    @Header(name = "编码")
    @Column(name = "code", length = 50)
    private String code;

    @Header(name = "备注")
    @Column(name = "remark", length = 1000)
    private String remark;

    @Header(name = "排序")
    @Column(name = "sort")
    private Integer sort;

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

    public String getRemark() {

        return remark;
    }

    public void setRemark(String remark) {

        this.remark = remark;
    }

    public Integer getSort() {

        return sort;
    }

    public void setSort(Integer sort) {

        this.sort = sort;
    }

}
