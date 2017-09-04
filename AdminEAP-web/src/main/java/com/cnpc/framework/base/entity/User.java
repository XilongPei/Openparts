package com.cnpc.framework.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class User extends BaseEntity {

    private static final long serialVersionUID = 6093546087036436583L;


    @Header(name = "姓名")
    @Column(name = "name")
    private String name;

    @Header(name = "性别")
    @Column(name = "sex")
    private String sex;

    @Header(name = "出生年月")
    @Column(name = "birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @Header(name = "登录名")
    @Column(name = "login_name")
    private String loginName;

    @Header(name = "密码")
    @Column(name = "password")
    private String password;

    @Header(name = "邮箱")
    @Column(name = "email", length = 50)
    private String email;

    @Header(name = "座机")
    @Column(name = "telphone")
    private String telphone;

    @Header(name = "手机")
    @Column(name = "mobile")
    private String mobile;

    @Header(name = "QQ")
    @Column(name = "qq")
    private String qq;

    @Header(name = "微信")
    @Column(name = "wechat")
    private String wechat;

    @Header(name = "是否开启账号")
    @Column(name = "open_account", length = 5)
    private String openAccount;

    @Header(name = "超级管理员")
    @Column(name = "isSuperAdmin")
    private String isSuperAdmin;

    @Header(name="部门ID")
    @Column(name="dept_id")
    private String deptId;


    @Transient
    private String avatarId;

    @Transient
    private int isSelected;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getSex() {

        return sex;
    }

    public void setSex(String sex) {

        this.sex = sex;
    }

    public Date getBirthday() {

        return birthday;
    }

    public void setBirthday(Date birthday) {

        this.birthday = birthday;
    }

    public String getLoginName() {

        return loginName;
    }

    public void setLoginName(String loginName) {

        this.loginName = loginName;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getTelphone() {

        return telphone;
    }

    public void setTelphone(String telphone) {

        this.telphone = telphone;
    }

    public String getMobile() {

        return mobile;
    }

    public void setMobile(String mobile) {

        this.mobile = mobile;
    }

    public String getQq() {

        return qq;
    }

    public void setQq(String qq) {

        this.qq = qq;
    }

    public String getWechat() {

        return wechat;
    }

    public void setWechat(String wechat) {

        this.wechat = wechat;
    }

    public String getOpenAccount() {

        return openAccount;
    }

    public void setOpenAccount(String openAccount) {

        this.openAccount = openAccount;
    }

    public String getIsSuperAdmin() {

        return isSuperAdmin;
    }

    public void setIsSuperAdmin(String isSuperAdmin) {

        this.isSuperAdmin = isSuperAdmin;
    }

}
