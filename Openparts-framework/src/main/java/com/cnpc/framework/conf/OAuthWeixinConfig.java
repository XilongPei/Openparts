package com.cnpc.framework.conf;

import com.cnpc.framework.oauth.common.CustomOAuthService;
import com.cnpc.framework.oauth.common.OAuthTypes;
import com.cnpc.framework.oauth.weixin.WeixinApi;
import com.cnpc.framework.utils.PropertiesUtil;
import org.scribe.builder.ServiceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;

@Configuration
public class OAuthWeixinConfig {

    @Value("${oauth.weixin.appid:appid}") String weixinAppId;
    @Value("${oauth.weixin.appsecret}") String weixinAppSecret;
    @Value("${oauth.callback.url}") String callback_url;

    @Bean
    public CustomOAuthService getWeixinOAuthService() {

        if (weixinAppId == null) {
            return null;
        }

        return (CustomOAuthService) new ServiceBuilder()
            .provider(WeixinApi.class)
            .apiKey(weixinAppId)
            .apiSecret(weixinAppSecret)
            .scope("snsapi_login")
            .callback(String.format(callback_url, OAuthTypes.WEIXIN))
            .build();
    }
}
