package com.cnpc.framework.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
@Table(name = "tbl_role_function")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class RoleFunction extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -1340123834197348115L;

    @Column(name = "roleId", length = 36)
    private String roleId;

    @Column(name = "functionId", length = 36)
    private String functionId;

    @Column(name="remark")
    private String remark;

    @Transient
    private List<FunctionFilter> fflist;

    public List<FunctionFilter> getFflist() {
        return fflist;
    }

    public void setFflist(List<FunctionFilter> fflist) {
        this.fflist = fflist;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFunctionId() {

        return functionId;
    }

    public void setFunctionId(String functionId) {

        this.functionId = functionId;
    }

    public String getRoleId() {

        return roleId;
    }

    public void setRoleId(String roleId) {

        this.roleId = roleId;
    }

}
