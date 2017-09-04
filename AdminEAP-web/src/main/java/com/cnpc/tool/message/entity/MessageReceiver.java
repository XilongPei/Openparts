package com.cnpc.tool.message.entity;

import com.cnpc.framework.annotation.Header;
import com.cnpc.framework.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by billJiang on 2017/3/1.
 * e-mail:475572229@qq.com  qq:475572229
 * 消息管理器-接收人
 */

@Entity
@Table(name = "tbl_message_receiver")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class MessageReceiver extends BaseEntity {
    // private static final long serialVersionUID = 2836972845793033124L;

    @Header(name = "消息id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message")
    private Message message;

    @Header(name = "接收人id")
    @Column(name = "receiveUserID")
    private String receiveUserID;

    @Header(name = "接收地址")
    @Column(name = "receiveAddress")
    private String receiveAddress;

    @Header(name = "是否已读")
    @Column(name = "readYet")
    private String readYet;  //0 未读  1 已读

    @Header(name = "读取时间")
    @Column(name = "readTime")
    private Date readTime;

    @Header(name = "消息标记")
    @Column(name = "messageFlag")
    private String messageFlag;// 0 重要紧急消息  1一般消息

    @Header(name = "消息类型")
    @Column(name = "messageType")  //0 系统消息  1 邮件  2 短信
    private String messageType;

    @Header(name = "备注")
    @Column(name = "remark", length = 1000)
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getReceiveUserID() {
        return receiveUserID;
    }

    public void setReceiveUserID(String receiveUserID) {
        this.receiveUserID = receiveUserID;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReadYet() {
        return readYet;
    }

    public void setReadYet(String readYet) {
        this.readYet = readYet;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public String getMessageFlag() {
        return messageFlag;
    }

    public void setMessageFlag(String messageFlag) {
        this.messageFlag = messageFlag;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
