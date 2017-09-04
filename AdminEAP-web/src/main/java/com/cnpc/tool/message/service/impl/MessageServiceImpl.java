package com.cnpc.tool.message.service.impl;

import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.impl.BaseServiceImpl;
import com.cnpc.framework.constant.RedisConstant;
import com.cnpc.framework.message.SimpleMailSender;
import com.cnpc.framework.util.SecurityUtil;
import com.cnpc.framework.utils.StrUtil;
import com.cnpc.tool.message.entity.Message;
import com.cnpc.tool.message.entity.MessageGroup;
import com.cnpc.tool.message.entity.MessageGroupUser;
import com.cnpc.tool.message.entity.MessageReceiver;
import com.cnpc.tool.message.pojo.MessageConstant;
import com.cnpc.tool.message.service.MessageService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by billJiang on 2016/12/27.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl implements MessageService {

    private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);


    public List<MessageGroup> getMessageGroupByUserId(String userId) {
        StringBuffer buf = new StringBuffer();
        buf.append("SELECT mg.id id,mg.name name,count(mgu.id) as userNum FROM tbl_message_group_user mgu");
        buf.append(" left join tbl_message_group  mg on mgu.group_id=mg.id");
        buf.append(" where mg.user_id=? group by mgu.group_id order by mg.sort");
        List<MessageGroup> groups = this.findMapBySql(buf.toString(), new Object[]{userId}, new Type[]{StringType.INSTANCE}, MessageGroup.class);
        return groups;

    }

    public List<MessageGroupUser> getUserByGroupId(String groupId) {
        String hql = "from MessageGroupUser where groupId=:groupId";
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        return this.find(hql, params);

    }

    @Override
    public Result checkExistGroup(String groupIds, String userIds) {
        String hql = "select distinct userId from MessageGroupUser where groupId in (" + StrUtil.getInStr(groupIds) + ")";
        List<String> idlist = this.find(hql);
        String[] userArr = userIds.split(",");
        if (idlist.size() != userArr.length) {
            return new Result(false);
        } else {
            for (String id : idlist) {
                if (userIds.indexOf(id) == -1) {
                    return new Result(false);
                }
            }
            return new Result(true);
        }
    }

    @Override
    public MessageGroup saveGroup(MessageGroup group, String userIds) {
        //新增
        String groupId;
        if (StrUtil.isEmpty(group.getId())) {
            group.setUserId(SecurityUtil.getUserId());
            groupId = this.save(group).toString();
        } else {
            groupId = group.getId();
            MessageGroup group_old = this.get(MessageGroup.class, groupId);
            group_old.setUpdateDateTime(new Date());
            group_old.setName(group.getName());
            group_old.setRemark(group.getRemark());
            group_old.setSort(group.getSort());
            this.update(group_old);
            //删除原有GroupUser数据，重新建立数据
            String hql = "delete from MessageGroupUser where groupId='" + groupId + "'";
            this.executeHql(hql);
        }
        String[] userArr = userIds.split(",");
        List<MessageGroupUser> list = new ArrayList<>();
        for (String userId : userArr) {
            MessageGroupUser groupUser = new MessageGroupUser();
            groupUser.setGroupId(groupId);
            groupUser.setUserId(userId);
            groupUser.setDeleted(0);
            groupUser.setUpdateDateTime(new Date());
            groupUser.setCreateDateTime(new Date());
            list.add(groupUser);
        }
        this.batchSave(list);
        return group;
    }

    @Override
    public List<MessageGroup> getGroupListByIds(String groupIds) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MessageGroup.class);
        String[] groupArr = groupIds.split(",");
        criteria.add(Restrictions.in("groupId", groupArr));
        criteria.addOrder(Order.desc("sort"));
        return this.findByCriteria(criteria);
    }

    @Override
    public Map getUserNamesByGroupIds(String groupIds) {
        String buf = "select name from tbl_message_group_user mgu" +
                " left join tbl_user us on mgu.user_id=us.id" +
                " where mgu.group_id in (" + StrUtil.getInStr(groupIds) + ")";
        List list = this.findMapBySql(buf);
        Map map = new HashMap();
        map.put("name", StrUtil.mapToStr(list, "name"));
        return map;

    }

    @Override
    public Map getUserNamesByUserIds(String userIds) {
        StringBuffer buf = new StringBuffer();
        buf.append("select name from tbl_user us");
        buf.append(" where us.id in (" + StrUtil.getInStr(userIds) + ")");
        List list = this.findMapBySql(buf.toString());
        Map map = new HashMap();
        map.put("name", StrUtil.mapToStr(list, "name"));
        return map;
    }

    @Override
    public Result saveMessage(Message message) {
        message.setSendUser(SecurityUtil.getUser().getName());
        message.setSendUserID(SecurityUtil.getUserId());
        //新增
        if (StrUtil.isEmpty(message.getId())) {
            //保存为草稿
            if (MessageConstant.SEND_STATUS_DRAFT.equals(message.getMessageStatus())) {
                this.save(message);
            }
            //直接保存并发送
            else if (MessageConstant.SEND_STATUS_SEND.equals(message.getMessageStatus())) {
                message.setSendTime(new Date());
                this.save(message);
                //复制消息到个人
                this.saveRecievers(message);
            }
        } else {
            //编辑草稿
            message.setUpdateDateTime(new Date());
            if (MessageConstant.SEND_STATUS_DRAFT.equals(message.getMessageStatus())) {
                this.update(message);
                //草稿变发送
            } else if (MessageConstant.SEND_STATUS_SEND.equals(message.getMessageStatus())) {
                //已发送不能编辑
                message.setSendTime(new Date());
                this.update(message);
                this.saveRecievers(message);
            }
        }
        return new Result();
    }


    /**
     * 保存消息到个人
     *
     * @param message 消息
     */
    public void saveRecievers(Message message) {
        //群组到人
        List<MessageReceiver> receiverlist = new ArrayList<>();
        if (MessageConstant.RECEIVER_GROUP.equals(message.getMessageType())) {
            List<String> userIds = this.getUserIdsByGroupIds(message.getReceiverIds());
            List<User> userList = this.getUserListByUserIds(userIds);
            receiverlist = getMessageReceiverList(userList, message);
            this.sendMessageToUser(message, userList);
        } else {
            //人到人
            List<User> userList = getUserListByUserIds(Arrays.asList(message.getReceiverIds().split(",")));
            receiverlist = getMessageReceiverList(userList, message);
            this.sendMessageToUser(message, userList);
        }
        this.batchSave(receiverlist);

    }

    /**
     * 消息发送
     *
     * @param message
     * @param userList
     */
    public void sendMessageToUser(Message message, List<User> userList) {
        if (message.getMessageType().indexOf(MessageConstant.TYPE_EMAIL) > -1) {
            SimpleMailSender mailSender = new SimpleMailSender();
            for (User user : userList) {
                try {
                    //非上线系统注释该接口
                    //mailSender.send(user.getEmail(), message.getSendSubject(), message.getSendContent());
                } catch (Exception ex) {
                    logger.error("向用户【" + user.getName() + ":" + user.getEmail() + "】发送邮件失败，请检查邮箱是否正确");
                }
            }
        }

        if (message.getMessageType().indexOf(MessageConstant.TYPE_SMS) > -1) {
            //TODO 发送短信
        }

    }

    public List<MessageReceiver> getMessageReceiverList(List<User> userList, Message message) {
        List<MessageReceiver> receiverlist = new ArrayList<>();
        for (User user : userList) {
            MessageReceiver receiver = new MessageReceiver();
            receiver.setReadYet(MessageConstant.READ_NO);
            receiver.setMessage(message);
            receiver.setReceiveUserID(user.getId());
            receiver.setDeleted(0);
            receiver.setMessageFlag(message.getMessageFlag());
            receiver.setMessageType(message.getMessageType());
            if (receiver.getMessageType().indexOf(MessageConstant.TYPE_EMAIL) > -1)
                receiver.setReceiveAddress(user.getEmail());
            if (receiver.getMessageType().indexOf(MessageConstant.TYPE_SMS) > -1)
                receiver.setReceiveAddress(StrUtil.isEmpty(receiver.getReceiveAddress()) ?
                        user.getTelphone() : receiver.getReceiveAddress() + "," + user.getTelphone());
            receiverlist.add(receiver);
        }
        return receiverlist;
    }

    public List<User> getUserListByUserIds(List userIds) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.in("id", userIds));
        List<User> userList = this.findByCriteria(criteria);
        return userList;
    }

    /**
     * 根据群组id获取用户id
     *
     * @param groupIds
     * @return
     */
    public List<String> getUserIdsByGroupIds(String groupIds) {
        String groupId = StrUtil.getInStr(groupIds);
        String hql = "select distinct userId from MessageGroupUser where groupId in (" + groupId + ")";
        return this.find(hql);
    }



    @Override
    public Map getMessageCount() {
        Map<String, Integer> retMap =redisDao.get(RedisConstant.MESSAGE_PRE+"count:"+SecurityUtil.getUserId(),Map.class);
        if(retMap==null) {
            retMap = new HashMap<>();
            //发件箱
            String hql_sent = "select count(id) from Message where sendUserID='" + SecurityUtil.getUserId() + "'";
            Long count_sent = this.get(hql_sent);
            retMap.put("sent", count_sent.intValue());
            //草稿箱
            String hql_draft = "select count(id) from Message where sendUserID='" + SecurityUtil.getUserId() + "' and messageStatus='0'";
            Long count_draft = this.get(hql_draft);
            retMap.put("draft", count_draft.intValue());
            //收件箱
            String hql_inbox = "select count(id) from MessageReceiver where receiveUserID='" + SecurityUtil.getUserId() + "' and deleted=0";
            Long count_inbox = this.get(hql_inbox);
            retMap.put("inbox", count_inbox.intValue());
            //回收站
            String hql_trash = "select count(id) from MessageReceiver where receiveUserID='" + SecurityUtil.getUserId() + "' and deleted=1 ";
            Long count_trash = this.get(hql_trash);
            retMap.put("trash", count_trash.intValue());
            redisDao.add(RedisConstant.MESSAGE_PRE+"count:"+SecurityUtil.getUserId(), retMap);
            return retMap;
        }else {
            return retMap;
        }
    }

    @Override
    public void deleteCacheForMsgCount(){
        redisDao.delete(RedisConstant.MESSAGE_PRE+"count:"+SecurityUtil.getUserId());
    }


}
