package com.cnpc.tool.message.controller;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.annotation.RefreshCSRFToken;
import com.cnpc.framework.annotation.VerifyCSRFToken;
import com.cnpc.framework.base.dao.RedisDao;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.tool.message.entity.Message;
import com.cnpc.tool.message.pojo.MessageConstant;
import com.cnpc.tool.message.service.MessageService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 消息管理控制器
 *
 * @author jrn
 *         2017-03-01 11:21:59由代码生成器自动生成
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;


    /**
     * 消息管理主页
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "tool/message/message_list";

    }

    //收件箱
    @RequestMapping(value = "/inbox", method = RequestMethod.GET)
    public String inbox() {
        //System.out.println(SecurityUtils.getSubject().getSession());
        return "tool/message/message_inbox";
    }

    //发件箱
    @RequestMapping(value = "/sent", method = RequestMethod.GET)
    public String sent() {
        return "tool/message/message_sent";
    }

    //草稿箱
    @RequestMapping(value = "/draft", method = RequestMethod.GET)
    public String draft() {
        return "tool/message/message_draft";
    }

    //垃圾箱/回收站
    @RequestMapping(value = "/trash", method = RequestMethod.GET)
    public String trash() {
        return "tool/message/message_trash";
    }

    @RefreshCSRFToken
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "tool/message/message_edit";
    }

    //发件明细
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "tool/message/message_detail";
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Message get(@PathVariable("id") String id) {
        return messageService.get(Message.class, id);
    }

    @VerifyCSRFToken
    @RequestMapping(value = "/save")
    @ResponseBody
    public Result save(String message) {
        Message msgobj = JSON.parseObject(message, Message.class);
        //保存为草稿或保存并直接发送
        messageService.deleteCacheForMsgCount();
        return messageService.saveMessage(msgobj);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable("id") String id) {
        Message message = this.get(id);
        if (!MessageConstant.SEND_STATUS_DRAFT.equals(message.getMessageStatus())) {
            return new Result(false, "该消息不是草稿状态，不能删除");
        }
        try {
            messageService.delete(message);
            messageService.deleteCacheForMsgCount();
            return new Result();
        } catch (Exception e) {
            return new Result(false, "该数据已经被引用，不可删除");
        }
    }

    //消息回复
    @RefreshCSRFToken
    @RequestMapping(value = "/reply/{id}", method = RequestMethod.GET)
    public String reply(@PathVariable("id") String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "tool/message/message_reply";
    }

    //消息转发
    @RefreshCSRFToken
    @RequestMapping(value = "/share/{id}", method = RequestMethod.GET)
    public String share(@PathVariable("id") String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "tool/message/message_share";
    }


    //获取 收件箱 发件箱 草稿箱 回收站的统计数量
    @RequestMapping(value ="/count",method = RequestMethod.POST)
    @ResponseBody
    @Cacheable("msg_count")
    public Map getCount(){
        return messageService.getMessageCount();
    }
}
