package com.cnpc.framework.oauth.qq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.cnpc.framework.oauth.common.CustomOAuthService;
import com.cnpc.framework.oauth.common.OAuthTypes;
import com.cnpc.framework.oauth.entity.OAuthUser;
import com.cnpc.framework.utils.PropertiesUtil;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.*;
import org.scribe.oauth.OAuth20ServiceImpl;

/*
 * QQ登录OAuth2.0总体处理流程如下：
 * Step1：接入申请，获取appid和apikey；
 * Step2：放置QQ登录按钮；
 * Step3：通过用户登录验证和授权，获取Access Token；
 * Step4：通过Access Token获取用户的OpenID；
 * Step5：调用OpenAPI，来请求访问或修改用户授权的资源。
*/

public class QqOAuthService extends OAuth20ServiceImpl implements CustomOAuthService {
    private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";

    private final DefaultApi20 api;
    private final OAuthConfig config;
    private final String authorizationUrl;

    public QqOAuthService(DefaultApi20 api, OAuthConfig config) {
        super(api,config);
        this.api=api;
        this.config=config;
        this.authorizationUrl=getAuthorizationUrl(null);
    }

    @Override
    public String getoAuthType() {
        return OAuthTypes.QQ;
    }

    @Override
    public String getBtnClass(){
        return PropertiesUtil.getValue("oauth.qq.btnclass");
    }

    @Override
    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    @Override
    public OAuthUser getOAuthUser(Token accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        this.signRequest(accessToken, request);
        Response response = request.send();
        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setoAuthType(getoAuthType());
        Object result = JSON.parse(response.getBody());
        oAuthUser.setoAuthId(JSONPath.eval(result, "$.id").toString());
        oAuthUser.setUserName(JSONPath.eval(result, "$.login").toString());
        return oAuthUser;
    }

}
