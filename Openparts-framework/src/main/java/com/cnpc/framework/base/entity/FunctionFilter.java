package com.cnpc.framework.base.entity;

import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by billJiang on 2017/1/3.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 * 功能上的数据权限
 */
@Entity
@Table(name="tbl_function_filter")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class FunctionFilter extends BaseEntity{
    private static final long serialVersionUID = -4489812553857126394L;


    @Header(name="关键字")
    @Column(name="[key]")
    private String key;

    @Header(name="操作符")
    @Column(name="operator")
    private String operator;

    @Header(name ="值" )
    @Column(name="[value]")
    private String value;

    @Header(name="类型")
    @Column(name="class_type")
    private String classType;

    @Header(name="排序")
    @Column(name="sort")
    private Integer sort;

    @Header(name="角色Id")
    @Column(name="roleId")
    private String roleId;

    @Header(name="功能Id")
    @Column(name="functionId")
    private String functionId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


}
