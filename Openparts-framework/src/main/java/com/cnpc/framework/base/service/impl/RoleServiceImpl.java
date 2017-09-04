package com.cnpc.framework.base.service.impl;

import com.cnpc.framework.base.entity.Role;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.RoleService;
import com.cnpc.framework.constant.RedisConstant;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {



    @Override
    public Result delete(String id) {

        String hql = "from UserRole where roleId='" + id + "'";
        if (this.find(hql).isEmpty()) {
            Role role = this.get(Role.class, id);
            this.delete(role);
            return new Result(true);
        }
        return new Result(false, "该角色已经绑定用户，请先解绑用户");
    }

    @Override
    public Set<String> getRoleCodeSet(String userId) {
        /*String sql= PropertiesUtil.getValue("shiro.sql.roles");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("param",userId);
        List<Map<String,Object>> list=super.findMapBySql(sql,params);
        Set<String> retSet=new HashSet<String>();
        for (Map map : list) {
            retSet.add(map.get("code").toString());
        }
        return retSet;*/
        List<Role> roles=getRoleList(userId);
        Set<String> roleSet=new HashSet<>();
        for (Role role : roles) {
            roleSet.add(role.getCode());
        }
        return roleSet;

    }

    @Override
    public List<Role> getRoleList(String userId){
        String key= RedisConstant.ROLE_PRE + userId;
        List<Role> roles=redisDao.getList(key,Role.class);
        if(roles==null){
            String sql="select r.* from tbl_user_role ur left join tbl_role r on ur.roleId=r.id " +
                    "left join tbl_user u on ur.userId=u.id where u.id=:userid order by r.sort";
            Map<String,Object> params=new HashMap<>();
            params.put("userid",userId);
            roles=this.findBySql(sql,params,Role.class);
            redisDao.add(key,roles);
            return roles;
        }
        return roles;
    }
}
