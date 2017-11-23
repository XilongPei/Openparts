package com.openparts.base.controller;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.annotation.RefreshCSRFToken;
import com.cnpc.framework.annotation.VerifyCSRFToken;
import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.entity.UserAvatar;
import com.cnpc.framework.base.pojo.PageInfo;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.UserRoleService;
import com.cnpc.framework.base.service.UserService;
import com.cnpc.framework.utils.EncryptUtil;
import com.cnpc.framework.utils.PropertiesUtil;
import com.cnpc.framework.utils.StrUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    private String list() {
        return "openparts/dashboard";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getdata")
    @ResponseBody
    public String getdata() {

        String sMorrisArea =
    "{\n" +
    "\"sMorrisArea\" : [ \n" +
    "  {\"y\": \"2011 Q1\", \"item1\": 2666, \"item2\": 2666},\n" +
    "  {\"y\": \"2011 Q2\", \"item1\": 2778, \"item2\": 2294},\n" +
    "  {\"y\": \"2011 Q3\", \"item1\": 4912, \"item2\": 1969},\n" +
    "  {\"y\": \"2011 Q4\", \"item1\": 3767, \"item2\": 3597},\n" +
    "  {\"y\": \"2012 Q1\", \"item1\": 6810, \"item2\": 1914},\n" +
    "  {\"y\": \"2012 Q2\", \"item1\": 5670, \"item2\": 4293},\n" +
    "  {\"y\": \"2012 Q3\", \"item1\": 4820, \"item2\": 3795},\n" +
    "  {\"y\": \"2012 Q4\", \"item1\": 15073, \"item2\": 5967},\n" +
    "  {\"y\": \"2013 Q1\", \"item1\": 10687, \"item2\": 4460},\n" +
    "  {\"y\": \"2013 Q2\", \"item1\": 8432, \"item2\": 5713}\n" +
    "],\n" +
    "\"sMorrisLine\" : [ \n" +
    "  {\"y\": \"2011 Q1\", \"item1\": 2666},\n" +
    "  {\"y\": \"2011 Q2\", \"item1\": 2778},\n" +
    "  {\"y\": \"2011 Q3\", \"item1\": 4912},\n" +
    "  {\"y\": \"2011 Q4\", \"item1\": 3767},\n" +
    "  {\"y\": \"2012 Q1\", \"item1\": 6810},\n" +
    "  {\"y\": \"2012 Q2\", \"item1\": 5670},\n" +
    "  {\"y\": \"2012 Q3\", \"item1\": 4820},\n" +
    "  {\"y\": \"2012 Q4\", \"item1\": 15073},\n" +
    "  {\"y\": \"2013 Q1\", \"item1\": 10687},\n" +
    "  {\"y\": \"2013 Q2\", \"item1\": 8432}\n" +
    "]\n" +
    "}\n";

        return sMorrisArea;
    }
}
