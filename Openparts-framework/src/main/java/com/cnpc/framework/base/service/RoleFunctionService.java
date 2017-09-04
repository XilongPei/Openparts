package com.cnpc.framework.base.service;

import com.cnpc.framework.base.entity.FunctionFilter;
import com.cnpc.framework.base.entity.RoleFunction;
import com.cnpc.framework.base.pojo.Result;

import java.util.List;

public interface RoleFunctionService extends BaseService {


    /**
     * 删除角色功能绑定
     * @param id 功能角色id
     * @return
     */
    Result deleteRoleFunction(String id);

    /**
     * 保存角色授权
     * @param rfobj 角色功能对象
     * @return
     */
    Result saveRoleFunction(RoleFunction rfobj);

    /**
     * 获取角色功能绑定
     * @param roleId 角色ID
     * @param functionId 功能ID
     * @return 获取的角色功能实体
     */
    RoleFunction getRoleFunction(String roleId, String functionId);

    /**
     * 批量保存角色功能绑定
     * @param roleId 角色Id
     * @param roleFunctionList 角色功能列表
     * @return 执行结果
     */
    Result saveBatchRoleFunction(String roleId, List<RoleFunction> roleFunctionList);

    /**
     * 批量保存数据权限
     * @param roleId 角色ID
     * @param functionId 功能ID
     * @param functionFilterList 数据权限列表
     * @return 执行结果
     */
    Result saveBatchFunctionFilter(String roleId, String functionId, List<FunctionFilter> functionFilterList);

    /**
     * 更改权限后，把绑定相关角色的权限缓存清除
     * @param roleId
     */
    void deleteAuthInRedis(String roleId);
}
