package com.cnpc.framework.oauth.service;

import com.cnpc.framework.base.service.BaseService;
import com.cnpc.framework.oauth.entity.OAuthUser;

/**
 * Created by billJiang on 2017/1/15.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
public interface OAuthUserService extends BaseService {
    /**
     * 根据第三方登录类型和授权ID 找到授权用户
     * @param authType 授权类型
     * @param oAuthId  第三方登录id
     * @return
     */
    public OAuthUser findByOAuthTypeAndOAuthId(String authType, String oAuthId);
}
