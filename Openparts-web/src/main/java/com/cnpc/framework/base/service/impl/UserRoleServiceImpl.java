package com.cnpc.framework.base.service.impl;

import java.util.List;

import com.cnpc.framework.base.dao.RedisDao;
import com.cnpc.framework.base.entity.Role;
import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.service.UserService;
import com.cnpc.framework.constant.RedisConstant;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cnpc.framework.base.entity.UserRole;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.UserRoleService;

import javax.annotation.Resource;

@Service("userRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl implements UserRoleService {
    private static final Logger logger= LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Resource
    private UserService userService;

    public Result delete(String ids) {

        DetachedCriteria criteria = DetachedCriteria.forClass(UserRole.class);
        criteria.add(Restrictions.in("id", ids.split(",")));
        List<UserRole> urlist = this.findByCriteria(criteria);
        for (UserRole userRole : urlist) {
            this.deleteAuthInRedis(userRole.getUser().getId());
        }
        this.batchDelete(urlist);
        return new Result();
    }

    @Override
    public void deleteAuthInRedis(String userId){
        userService.deleteAuthInRedis(userId);
    }

    @Override
    public void setRoleForRegisterUser(String userId) {
        String hql="from Role where code='COMMON' and (deleted=0 or deleted is null)";
        Role role=this.get(hql);
        if(role==null){
            //System.out.println("系统管理需要配置名称为COMMON的角色，并未该角色分配权限");
            logger.error("系统管理需要配置名称为COMMON的角色，并未该角色分配权限");
        }else{
            UserRole userRole=new UserRole();
            User user=this.get(User.class,userId);
            userRole.setUser(user);
            userRole.setRoleId(role.getId());
            userRole.setDeleted(0);
            this.save(userRole);
        }

    }
}
