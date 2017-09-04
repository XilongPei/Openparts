package com.cnpc.tool.message.pojo;

/**
 * Created by billJiang on 2017/3/1.
 * e-mail:475572229@qq.com  qq:475572229
 * 消息常量
 */
public class MessageConstant {

    //消息类型  0 系统消息  1 邮件  2 短信
    public static final String TYPE_SYSTEM = "0";
    public static final String TYPE_EMAIL = "1";
    public static final String TYPE_SMS = "2";

    //消息状态 0  临时保存（草稿箱）   1 审批中  2 审批退回  4 已发送（审批通过
    public static final String SEND_STATUS_DRAFT = "0";
    public static final String SEND_STATUS_APPROVE = "1";
    public static final String SEND_STATUS_BACK = "2";
    public static final String SEND_STATUS_SEND = "4";

    //消息标记 0 一般 1 重要
    public static final String FLAG_IMPORTANT = "1";
    public static final String FLAG_NORMAL = "0";

    //消息是否已读取 1 已读 0 未读
    public static final String READ_YES = "1";
    public static final String READ_NO = "0";



    /**
     * 接受类型 0=群组
     */
    public static final String RECEIVER_GROUP = "0";

    /**
     * 接受类型 1=用户
     */
    public static final String RECEIVER_USER = "1";

}
