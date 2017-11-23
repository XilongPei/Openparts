package com.cnpc.tool.message.controller;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.annotation.RefreshCSRFToken;
import com.cnpc.framework.annotation.VerifyCSRFToken;
import com.cnpc.framework.base.dao.RedisDao;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.tool.message.entity.Message;
import com.cnpc.tool.message.pojo.MessageConstant;
import com.cnpc.tool.message.service.NavbarService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/message")
public class NavbarController {

    @Resource
    private NavbarService navbarService;

    @RequestMapping(value ="/navbarnumber",method = RequestMethod.POST)
    @ResponseBody
    @Cacheable("msg_navbarnumber")
    public Map getNavbarNum(){
        return navbarService.getNavbarNumber();
    }
}
