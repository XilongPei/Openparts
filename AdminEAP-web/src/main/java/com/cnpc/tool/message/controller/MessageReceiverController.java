package com.cnpc.tool.message.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.util.SecurityUtil;
import com.cnpc.tool.message.entity.Message;
import com.cnpc.tool.message.entity.MessageGroup;
import com.cnpc.tool.message.entity.MessageGroupUser;
import com.cnpc.tool.message.entity.MessageReceiver;
import com.cnpc.tool.message.pojo.MessageConstant;
import com.cnpc.tool.message.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by billJiang on 2017/3/2.
 * e-mail:475572229@qq.com  qq:475572229
 */
@Controller
@RequestMapping(value="/message")
public class MessageReceiverController {
    @Resource
    private MessageService messageService;

    //收件人管理
    @RequestMapping(value = "/receiver", method = RequestMethod.GET)
    public String receiver() {
        return "tool/message/message_receiver";
    }

    //选择接收人
    @RequestMapping(value="/receiver/select",method=RequestMethod.GET)
    public String selectReceiver(HttpServletRequest request){
        String userId=SecurityUtil.getUserId();
        //获取当前用户的消息用户群组
        List<MessageGroup> groups=messageService.getMessageGroupByUserId(userId);
        request.setAttribute("groups",groups);
        return "tool/message/message_receiver_select";
    }


    @RequestMapping(value = "/receiver/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MessageReceiver get(@PathVariable("id") String id) {
        return messageService.get(MessageReceiver.class, id);
    }

    //接收人群组
    @RequestMapping(value = "/receiver/users/{groupId}",method = RequestMethod.POST)
    @ResponseBody
    public List<MessageGroupUser> getGroupUsers(@PathVariable("groupId")String groupId){
        return messageService.getUserByGroupId(groupId);
    }

    //新增群组
    @RequestMapping(value = "/receiver/group/edit",method = RequestMethod.GET)
    public String edit(String groupId,HttpServletRequest request){
          request.setAttribute("groupId",groupId);
        return "tool/message/message_receiver_group_edit";
    }

    @RequestMapping(value = "/receiver/group/save",method = RequestMethod.POST)
    @ResponseBody
    public MessageGroup save(String group, String userIds){
        MessageGroup groupObj= JSON.parseObject(group,MessageGroup.class);
       return messageService.saveGroup(groupObj,userIds);
    }

    //校验是否userIds 完全包含在GroupIds中
    @RequestMapping(value = "/receiver/group/exist",method = RequestMethod.POST)
    @ResponseBody
    public Result existGroup(String groupIds,String userIds){
        return messageService.checkExistGroup(groupIds,userIds);
    }

    @RequestMapping(value = "/receiver/group/name",method = RequestMethod.POST)
    @ResponseBody
    public List<MessageGroup> getGroupListIds(String groupIds){
        return messageService.getGroupListByIds(groupIds);
    }

    //根据群组ids获取接收人姓名
    @RequestMapping(value="/receiver/user/group",method = RequestMethod.POST)
    @ResponseBody
    public Map getByGroupIds(String groupIds){
        return messageService.getUserNamesByGroupIds(groupIds);
    }

    @RequestMapping(value = "/receiver/user/names",method = RequestMethod.POST)
    @ResponseBody
    public Map getByUserIds(String userIds){
        return messageService.getUserNamesByUserIds(userIds);
    }



    //收件箱 阅读邮件
    @RequestMapping(value="/receiver/read/{id}",method = RequestMethod.GET)
    public String readMsg(@PathVariable("id") String id){
        MessageReceiver receiver=messageService.get(MessageReceiver.class,id);
        if(MessageConstant.READ_NO.equals(receiver.getReadYet())){
            receiver.setReadTime(new Date());
            receiver.setReadYet(MessageConstant.READ_YES);
        }
        return "tool/message/message_read";
    }

    //放入回收站
    @RequestMapping(value = "/receiver/trash/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable("id") String id) {
        MessageReceiver message = this.get(id);
        message.setDeleted(1);
        messageService.update(message);
        messageService.deleteCacheForMsgCount();
        return new Result();
    }

    //标记为未读已读
    @RequestMapping(value = "/receiver/readUpdate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result toggleReadState(@PathVariable("id") String id) {
        MessageReceiver receiver = this.get(id);
        //未读变已读
        if(MessageConstant.READ_NO.equals(receiver.getReadYet())){
            receiver.setReadYet(MessageConstant.READ_YES);
            //强制变为已读不设置阅读时间
            //receiver.setReadTime(new Date());
        }else{
            //已读变未读
            receiver.setReadYet(MessageConstant.READ_NO);
        }
        messageService.update(receiver);
        return new Result();
    }

    //回收站中彻底删除
    @RequestMapping(value = "/trash/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteForever(@PathVariable("id") String id) {
        MessageReceiver receiver = this.get(id);
        messageService.delete(receiver);
        return new Result();
    }

    //回收站恢复到收件箱
    @RequestMapping(value = "/trash/restore/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result restore(@PathVariable("id") String id) {
        MessageReceiver message = this.get(id);
        message.setDeleted(0);
        messageService.update(message);
        messageService.deleteCacheForMsgCount();
        return new Result();
    }



}
