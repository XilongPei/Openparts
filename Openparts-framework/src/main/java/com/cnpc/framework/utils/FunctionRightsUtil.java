package com.cnpc.framework.utils;

import org.apache.shiro.SecurityUtils;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cnpc.framework.base.service.FunctionService;
import com.cnpc.framework.base.service.RoleService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.util.AntPathMatcher;

public class FunctionRightsUtil {

    private static final Logger logger = LoggerFactory.getLogger(FunctionRightsUtil.class);
    private static RoleService roleService = null;
    private static FunctionService functionService = null;
    private static PatternMatcher pathMatcher = new AntPathMatcher();

    public static boolean getFunctionRights(Subject subject, String path) {

        if (roleService == null) {
            roleService = (RoleService)SpringContextUtil.getBean("roleService");
        }

        if (functionService == null) {
            functionService = (FunctionService)SpringContextUtil.getBean("functionService");
        }

        String userId = subject.getPrincipal().toString();
        Set<String> roles = roleService.getRoleCodeSet(userId);
        Set<String> functions = functionService.getFunctionCodeSet(roles, userId);
        if (functions.isEmpty()) {
            return false;
        }

        boolean match = false;
        for (String str : functions) {
            if (pathMatcher.matches(str, path)) {
                match = true;
                return true;
            }
        }

        return true;
    }

    public static Set<String> getCurrentUserRoles() {
        if (roleService == null) {
            roleService = (RoleService)SpringContextUtil.getBean("roleService");
        }

        String userId = SecurityUtils.getSubject().getPrincipal().toString();
        Set<String> roles = roleService.getRoleCodeSet(userId);

        return roles;
    }
}
