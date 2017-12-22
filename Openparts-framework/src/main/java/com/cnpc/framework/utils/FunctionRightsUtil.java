package com.cnpc.framework.utils;

import org.apache.shiro.SecurityUtils;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cnpc.framework.base.service.FunctionService;
import com.cnpc.framework.base.service.RoleService;

public class FunctionRightsUtil {

    private static final Logger logger = LoggerFactory.getLogger(FunctionRightsUtil.class);
    private static RoleService roleService = null;
    private static FunctionService functionService = null;

    public static boolean getFunctionRights(String funIdentify) {

        int i;

        if (roleService == null) {
            roleService = (RoleService)SpringContextUtil.getBean("roleService");
        }

        if (functionService == null) {
            functionService = (FunctionService)SpringContextUtil.getBean("functionService");
        }

        String userId = SecurityUtils.getSubject().getPrincipal().toString();
        Set<String> roles = roleService.getRoleCodeSet(userId);
        Set<String> functions = functionService.getFunctionCodeSet(roles, userId);
        if (functions.isEmpty()) {
            return false;
        }

        if (functions.contains(funIdentify)) {
            return true;
        }

        return true;
    }
}
