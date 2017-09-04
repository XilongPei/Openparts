package com.cnpc.framework.oauth.entity;

import com.cnpc.framework.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user_oauth")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class OAuthUser extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 2836972841233228L;

    @Column(name="user_id")
    private String userId;

    @Column(name="user_name")
    private  String userName;

    @Column(name="oauth_type")
    private String oAuthType;

    @Column(name="oauth_id")
    private String oAuthId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getoAuthType() {
        return oAuthType;
    }

    public void setoAuthType(String oAuthType) {
        this.oAuthType = oAuthType;
    }

    public String getoAuthId() {
        return oAuthId;
    }

    public void setoAuthId(String oAuthId) {
        this.oAuthId = oAuthId;
    }
}
