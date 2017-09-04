package com.cnpc.tool.message.service;

import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.BaseService;
import com.cnpc.tool.message.entity.Message;
import com.cnpc.tool.message.entity.MessageGroup;
import com.cnpc.tool.message.entity.MessageGroupUser;

import java.util.List;
import java.util.Map;

/**
 * Created by billJiang on 2016/12/27.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
public interface MessageService extends BaseService {
    /**
     * 根据用户id获取消息群组
     * @param userId 用户Id
     * @return
     */
    List<MessageGroup> getMessageGroupByUserId(String userId);

    /**
     * 根据groupId获取接收人列表
     * @param groupId
     * @return
     */
    List<MessageGroupUser> getUserByGroupId(String groupId);

    /**
     * 校验当前选中的用户userIds是否正好包含在GroupIds中
     * @param groupIds
     * @param userIds
     * @return
     */
    Result checkExistGroup(String groupIds, String userIds);

    /**
     * 保存新的群组
     * @param group
     * @param userIds
     * @return
     */
    MessageGroup saveGroup(MessageGroup group, String userIds);

    /**
     * 根据GroupIds获取群组列表
     * @param groupIds
     * @return
     */
    List<MessageGroup> getGroupListByIds(String groupIds);

    /**
     * 根据GroupIds获取用户名称
     * @param groupIds
     * @return
     */
    Map getUserNamesByGroupIds(String groupIds);

    /**
     * 根据UserIds获取用户名称
     * @param userIds
     * @return
     */
    Map getUserNamesByUserIds(String userIds);

    /**
     * 保存为草稿 保存并发送
     * @param message
     */
    Result saveMessage(Message message);

    Map getMessageCount();

    /**
     * 删除msgcount的缓存
     */
    public void deleteCacheForMsgCount();
}
