package com.cnpc.framework.message.entity;

import com.cnpc.framework.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by billJiang on 2016/12/27.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
@Table
@Entity(name = "tbl_simple_mail")
public class SimpleMail extends BaseEntity {
    @Column(name = "title")
    private String subject;
    @Column(name = "content", length = 4000)
    private String content;
    @Column(name = "mail_type")
    private String mailType;

    @Column(name="fromUser")
    private String fromUser;

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }
}
