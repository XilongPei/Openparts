package com.cnpc.tool.message.entity;

import com.cnpc.framework.annotation.Header;
import com.cnpc.framework.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by billJiang on 2017/3/1.
 * e-mail:475572229@qq.com  qq:475572229
 * 消息组关联用户
 */
@Entity
@Table(name="tbl_message_group_user")
public class MessageGroupUser extends BaseEntity {

    @Header(name="消息组")
    @Column(name = "group_id")
    private String groupId;

    @Header(name="用户")
    @Column(name = "user_id")
    private String userId;

    @Header(name="备注")
    @Column(name="remark")
    private String remark;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
