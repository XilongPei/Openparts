package com.cnpc.framework.base.entity;

import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户头像
 */
@Entity
@Table(name = "tbl_user_avatar")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class UserAvatar extends BaseEntity {

    @Header(name="用户Id")
    @Column(name="user_id",length=32)
    private String userId;

    @Header(name="头像名称")
    @Column(name="name")
    private String name;

    @Header(name="头像路径")
    @Column(name="src")
    private String src;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
