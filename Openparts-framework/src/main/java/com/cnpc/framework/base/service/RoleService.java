package com.cnpc.framework.base.service;

import com.cnpc.framework.base.entity.Role;
import com.cnpc.framework.base.pojo.Result;

import java.util.List;
import java.util.Set;

public interface RoleService extends BaseService {

    Result delete(String id);

    /**
     * 根据登录名，获取角色集合
     * @param userId 用户id
     * @return 角色编码集合
     */
    Set<String> getRoleCodeSet(String userId);

    List<Role> getRoleList(String userId);
}
