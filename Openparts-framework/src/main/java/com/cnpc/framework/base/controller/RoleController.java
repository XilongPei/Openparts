package com.cnpc.framework.base.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnpc.framework.base.entity.Role;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.RoleService;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 角色列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    private String list() {

        return "base/auth/role_list";
    }

    /**
     * 角色编辑
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/edit")
    private String eidt(String id, HttpServletRequest request) {

        request.setAttribute("id", id);
        return "base/auth/role_edit";
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public Role get(String id) {

        Role role = roleService.get(Role.class, id);
        return role;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Role role) {

        role.setUpdateDateTime(new Date());
        roleService.saveOrUpdate(role);
        return new Result(true);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable("id") String id) {

        return roleService.delete(id);
    }

}
