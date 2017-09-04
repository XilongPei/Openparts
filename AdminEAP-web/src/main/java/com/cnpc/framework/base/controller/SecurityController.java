package com.cnpc.framework.base.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by billJiang on 2017/1/12.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 * shiro 安全控制
 */
@Controller
@RequestMapping("/security")
public class SecurityController {
    @RequestMapping("/hasRole")
    @ResponseBody
    private boolean hasRole(String roleCode) {
        Subject subject = SecurityUtils.getSubject();
        return subject.hasRole(roleCode);
    }

    @RequestMapping("/isPermitted")
    @ResponseBody
    private boolean isPermitted(String permission) {
        Subject subject = SecurityUtils.getSubject();
        return subject.isPermitted(permission);
    }
}
