package com.cnpc.framework.oauth.common;

import com.cnpc.framework.oauth.entity.OAuthUser;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.scribe.oauth.OAuthService;

/**
 * Created by billJiang on 2017/1/15.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
public abstract class OAuthServiceDecorator implements OAuthService {

    private final OAuthService oAuthService;
    private final String oAuthType;
    private final String authorizationUrl;

    public OAuthServiceDecorator(OAuthService oAuthService, String type) {
        super();
        this.oAuthService = oAuthService;
        this.oAuthType = type;
        this.authorizationUrl = oAuthService.getAuthorizationUrl(null);
    }


    public String getoAuthType() {
        return oAuthType;
    }

    public String getAuthorizationUrl(){
        return authorizationUrl;
    }

    public abstract OAuthUser getOAuthUser(Token accessToken);


    @Override
    public Token getRequestToken() {
        return null;
    }

    @Override
    public Token getAccessToken(Token token, Verifier verifier) {
        return null;
    }

    @Override
    public void signRequest(Token token, OAuthRequest oAuthRequest) {

    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getAuthorizationUrl(Token token) {
        return null;
    }
}
