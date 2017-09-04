package com.cnpc.framework.base.service.impl;

import com.cnpc.framework.base.dao.BaseDao;
import com.cnpc.framework.base.dao.RedisDao;
import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.entity.UserAvatar;
import com.cnpc.framework.base.service.UserService;
import com.cnpc.framework.constant.RedisConstant;
import com.cnpc.framework.utils.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    @Resource
    private BaseDao baseDao;


    private List<User> getUsers() {

        return baseDao.find("from User");
    }

    public User getUserByLoginName(String loginName){
        return this.get("from User where loginName='"+loginName+"'");
    }

    @Override
    public UserAvatar getAvatarByUserId(String userId) {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("userId",userId);
        return this.get("from UserAvatar where userId=:userId",params);
    }

    @Override
    public void updateUserAvatar(User user,String dirPath) {
       if(StrUtil.isEmpty(user.getAvatarId()))
           return;
        UserAvatar userAvatar=this.get(UserAvatar.class,user.getAvatarId());
        userAvatar.setUserId(user.getId());
        String src=userAvatar.getSrc();
        File file = new File(dirPath +src);
        String newPath=src.replaceAll("new",user.getName());
        if (file.exists()) {
            file.renameTo(new File(dirPath+newPath));
        }
        userAvatar.setSrc(newPath);
        userAvatar.setName(userAvatar.getName().replaceAll("new",user.getName()));
        this.update(userAvatar);
    }

    @Override
    public User getUserById(String userId){
        String key= RedisConstant.USER_PRE+userId;
        User user=redisDao.get(key,User.class);
        if(user==null) {
             user = this.get(User.class,userId);
            redisDao.add(key,user);
            return user;
        }else{
            return user;
        }
    }

    @Override
    public void deleteAuthInRedis(String userId) {
        //缓存SimpleAuthorizationInfo权限
        redisDao.delete(userId);
        redisDao.delete(RedisConstant.ROLE_PRE+userId);
        redisDao.delete(RedisConstant.PERMISSION_PRE+userId);
    }

    @Override
    public String getUserNamesByUserIds(String userIds) {
        StringBuffer buf = new StringBuffer();
        buf.append("select distinct name as name from tbl_user");
        buf.append(" where id in (" + StrUtil.getInStr(userIds) + ")");
        List list = this.findMapBySql(buf.toString());
        return StrUtil.mapToStr(list, "name");
    }


}
