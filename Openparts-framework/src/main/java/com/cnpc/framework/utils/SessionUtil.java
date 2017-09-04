package com.cnpc.framework.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.Map;


/**
 * SessionUtil session 工具类
 *
 * @author billJiang 2016年3月26日 下午10:12:16
 */
public class SessionUtil {


    public static Session getSession() {

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        return session;
    }

    public static String getUserId() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }


}
