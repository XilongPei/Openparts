package com.cnpc.tool.message.entity;

import com.cnpc.framework.annotation.Header;
import com.cnpc.framework.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tbl_message")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler",	"fieldHandler" })
public class Message extends BaseEntity {

	//private static final long serialVersionUID = 2836972845793033123L;
	@Header(name="发送人id")
	@Column(name="sendUserID")
	private String sendUserID;

    @Header(name="发送人")
	@Column(name = "sendUser")
	private String sendUser;
    
    @Header(name="发送时间")
    @Column(name="sendTime")
    private Date sendTime;
    
    @Header(name="主题")
    @Column(name="sendSubject")
    private String sendSubject;
    
    @Header(name="内容")
    @Column(name="sendContent",length=4000)
    private String sendContent;
    
    @Header(name="消息类型")
    @Column(name="messageType")  //0 系统消息  1 邮件  2 短信
    private String messageType;
    
    @Header(name="消息状态")
    @Column(name="messageStatus")
    private String messageStatus; //0  临时保存   1 审批中  2 审批退回  4 已发送（审批通过

	@Header(name="接收人类型")
	@Column(name="receiverType")
	private String receiverType;//0 群组 1用户 接收人类型决定receiverIds是哪类ID

    @Header(name="接收人ID/群组ID")
    @Column(name="receiverIds",length=4000)
    private String receiverIds;

	@Header(name="消息标记")
	@Column(name="messageFlag")
	private String messageFlag;// 0 重要紧急消息  1一般消息

	@Header(name="附件")
	@Column(name="fileIds",length=1000)
	private String fileIds;

	@Header(name="备注")
	@Column(name="remark",length = 1000)
	private String remark;


	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public String getMessageFlag() {
		return messageFlag;
	}

	public void setMessageFlag(String messageFlag) {
		this.messageFlag = messageFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}


	public String getSendSubject() {
		return sendSubject;
	}

	public void setSendSubject(String sendSubject) {
		this.sendSubject = sendSubject;
	}

	public String getSendUserID() {
		return sendUserID;
	}

	public void setSendUserID(String sendUserID) {
		this.sendUserID = sendUserID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
}
