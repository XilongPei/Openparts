package com.cnpc.framework.base.service;

import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.entity.UserAvatar;

public interface UserService extends BaseService {

    /**
     * 根据登录名获取用户
     *
     * @param loginName 登录名
     * @return
     */
    User getUserByLoginName(String loginName);

    /**
     * 根据用户Id 获取头像
     *
     * @param userId
     * @return
     */
    UserAvatar getAvatarByUserId(String userId);

    /**
     * 关联头像和用户 user.getAvatarId()
     *
     * @param user
     * @param dirPath 项目目录
     */
    void updateUserAvatar(User user, String dirPath);

    User getUserById(String userId);

    /**
     * 清除redis中的相关权限设置，主要在更新权限时使用
     * @param userId
     */
    void deleteAuthInRedis(String userId);

    String getUserNamesByUserIds(String userIds);
}
