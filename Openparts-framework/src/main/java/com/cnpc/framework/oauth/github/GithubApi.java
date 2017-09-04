package com.cnpc.framework.oauth.github;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;

/**
 * Created by billJiang on 2017/1/15.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 * Github Api for oauth2.0
 */
public class GithubApi extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&state=%s";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token?state=%s";

    private final String githubState;

    public GithubApi(String state) {
        this.githubState = state;
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        if (config.hasScope()) {
            return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), githubState, OAuthEncoder.encode(config.getScope()));
        } else {
            return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), githubState);
        }
    }

    @Override
    public String getAccessTokenEndpoint() {
        return String.format(ACCESS_TOKEN_URL,githubState);
    }



    @Override
    public OAuthService createService(OAuthConfig config){
        return new GithubOAuthService(this,config);
    }

}
