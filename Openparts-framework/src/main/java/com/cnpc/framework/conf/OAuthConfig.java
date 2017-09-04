package com.cnpc.framework.conf;

import com.cnpc.framework.oauth.common.CustomOAuthService;
import com.cnpc.framework.oauth.common.OAuthTypes;
import com.cnpc.framework.oauth.github.GithubApi;
import com.cnpc.framework.utils.PropertiesUtil;
import org.scribe.builder.ServiceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * Created by billJiang on 2017/1/15.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
@Configuration
public class OAuthConfig {
    @Value("${oauth.callback.url}")
    String callback_url;

    /**
     * github配置
     */
    @Value("${oauth.github.key}")
    String github_key;
    @Value("${oauth.github.secret}")
    String github_secret;
    @Value("${oauth.github.state}")
    String github_state;

    @Bean
    public GithubApi githubApi(){
        return new GithubApi(github_state);
    }

    @Bean
    public CustomOAuthService getGithubOAuthService(){
        return (CustomOAuthService)new ServiceBuilder()
                .provider(githubApi())
                .apiKey(github_key)
                .apiSecret(github_secret)
                .callback(String.format(callback_url, OAuthTypes.GITHUB))
                .build();
    }
}
