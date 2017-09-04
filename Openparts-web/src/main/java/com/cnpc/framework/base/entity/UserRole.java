package com.cnpc.framework.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_user_role")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class UserRole extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 2836972845793033228L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "roleId", length = 36)
    private String roleId;

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public String getRoleId() {

        return roleId;
    }

    public void setRoleId(String roleId) {

        this.roleId = roleId;
    }

}
