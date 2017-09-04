package com.cnpc.demos.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnpc.demos.service.UserDemoService;
import com.cnpc.framework.base.entity.User;

/**
 * 人员增删改demo
 *
 * Created by HANZO on 2016/6/16.
 */
@Controller
@RequestMapping("userDemo")
public class UserDemoController {

    private static final String VIEW_LIST = "demos/user_demo_list";

    @Resource
    private UserDemoService userDemoService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {

        return VIEW_LIST;
    }

    @RequestMapping(value = "progress", method = RequestMethod.GET)
    public String progress_demo() {

        return "demos/progress";
    }

    @RequestMapping(value = "saveUser", method = RequestMethod.POST)
    @ResponseBody
    public User saveUser(User user) {

        return userDemoService.saveUser(user);
    }

    @RequestMapping(value = "getUser", method = RequestMethod.POST)
    @ResponseBody
    public User getUser(String id) {

        return userDemoService.get(User.class, id);
    }

}
