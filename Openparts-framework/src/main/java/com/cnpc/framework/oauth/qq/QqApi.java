package com.cnpc.framework.oauth.qq;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;

public class QqApi extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code";

    private final String qqState;

    public QqApi() {
        this.qqState = "state";
    }

    public QqApi(String state) {
        this.qqState = state;
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        if (config.hasScope()) {
            return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), qqState, OAuthEncoder.encode(config.getScope()));
        } else {
            return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), qqState);
        }
    }

    @Override
    public String getAccessTokenEndpoint() {
        return String.format(ACCESS_TOKEN_URL,qqState);
    }



    @Override
    public OAuthService createService(OAuthConfig config){
        return new QqOAuthService(this,config);
    }

}
