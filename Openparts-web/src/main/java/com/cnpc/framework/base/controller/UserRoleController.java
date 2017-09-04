package com.cnpc.framework.base.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.cnpc.framework.base.dao.RedisDao;
import com.cnpc.framework.constant.RedisConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.base.entity.UserRole;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.UserRoleService;

@Controller
@RequestMapping(value = "/userrole")
public class UserRoleController {


    @Resource
    private UserRoleService userRoleService;



    /**
     * 用户选择
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/select")
    private String select(String roleId, HttpServletRequest request) {

        request.setAttribute("roleId", roleId);
        return "base/auth/userrole_select";
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public UserRole get(String id) {

        UserRole ur = userRoleService.get(UserRole.class, id);
        return ur;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(String urlist) {

        List<UserRole> urs = JSON.parseArray(urlist, UserRole.class);
        for (UserRole ur : urs) {
            ur.setUpdateDateTime(new Date());
            //清除redis缓存
           userRoleService.deleteAuthInRedis(ur.getUser().getId());
        }
        userRoleService.batchSave(urs);
        return new Result(true);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(String ids) {

        try {
            return userRoleService.delete(ids);
        } catch (Exception e) {
            return new Result(false, "已经被其他数据引用，不可删除");
        }
    }

}
