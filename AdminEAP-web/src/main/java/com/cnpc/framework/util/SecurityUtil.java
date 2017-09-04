package com.cnpc.framework.util;

import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.service.UserService;
import com.cnpc.framework.utils.SpringContextUtil;
import org.apache.shiro.SecurityUtils;

import javax.annotation.Resource;

/**
 * Created by billJiang on 2017/2/6.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
public class SecurityUtil {

    public static String getUserId(){
       return SecurityUtils.getSubject().getPrincipal().toString();
    }

    public static User getUser(){
        return (User)SecurityUtils.getSubject().getSession().getAttribute("user");
    }
}
