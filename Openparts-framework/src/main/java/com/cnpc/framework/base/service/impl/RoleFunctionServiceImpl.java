package com.cnpc.framework.base.service.impl;

import com.cnpc.framework.base.entity.FunctionFilter;
import com.cnpc.framework.base.entity.RoleFunction;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.RoleFunctionService;
import com.cnpc.framework.constant.RedisConstant;
import com.cnpc.framework.utils.StrUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by billJiang on 2017/1/3.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 * 角色授权服务
 */
@Service("roleFunctionService")
public class RoleFunctionServiceImpl extends BaseServiceImpl implements RoleFunctionService {

    @Override
    public Result deleteRoleFunction(String id) {
        //delete functionfilter first
        RoleFunction roleFunction = this.get(RoleFunction.class, id);
        String hql = "delete from FunctionFilter where roleId='" + roleFunction.getRoleId() + "' and functionId='"+roleFunction.getFunctionId()+"'";
        this.executeHql(hql);
        //delete rolefunction entity
        this.delete(roleFunction);
        //-----------update redis-----------
        this.deleteAuthInRedis(roleFunction.getRoleId());
        //----------------------------------
        return new Result();
    }

    @Override
    public Result saveRoleFunction(RoleFunction rfobj) {
        if (!StrUtil.isEmpty(rfobj.getId())) {
            deleteRoleFunction(rfobj.getId());
        }
       this.save(rfobj).toString();
        //-----------update redis-----------
        this.deleteAuthInRedis(rfobj.getRoleId());
        //----------------------------------
       return saveBatchFunctionFilter(rfobj.getRoleId(),rfobj.getFunctionId(),rfobj.getFflist());
    }

    @Override
    public RoleFunction getRoleFunction(String roleId, String functionId) {
        RoleFunction roleFunction = this.get("from RoleFunction where roleId='" + roleId + "' and functionId='" + functionId + "'");
        if (roleFunction == null)
            return new RoleFunction();
        //-----------update redis-----------
        this.deleteAuthInRedis(roleId);
        //----------------------------------
        return roleFunction;
    }

    @Override
    public Result saveBatchRoleFunction(String roleId, List<RoleFunction> roleFunctionList) {
        String hql="delete from RoleFunction where roleId='"+roleId+"'";
        this.executeHql(hql);
        this.batchSave(roleFunctionList);
        //-----------update redis-----------
        this.deleteAuthInRedis(roleId);
        //----------------------------------
        return new Result();
    }

    @Override
    public Result saveBatchFunctionFilter(String roleId, String functionId, List<FunctionFilter> functionFilterList) {
        String hql="delete from FunctionFilter where roleId='"+roleId+"' and functionId='"+functionId+"'";
        this.executeHql(hql);
        this.batchSave(functionFilterList);
        //-----------update redis-----------
        this.deleteAuthInRedis(roleId);
        //----------------------------------
        return new Result();
    }

    @Override
    public void deleteAuthInRedis(String roleId) {
        String sql="select distinct userId from tbl_user_role where roleId=:roleId";
        Map<String,Object> params=new HashMap<>();
        params.put("roleId",roleId);
        List<Map<String,Object>> users=this.findMapBySql(sql,params);
        for (Map<String, Object> user : users) {
            String userId=user.get("userId").toString();
            redisDao.delete(userId);
            redisDao.delete(RedisConstant.ROLE_PRE+userId);
            redisDao.delete(RedisConstant.PERMISSION_PRE+userId);
        }
    }
}
