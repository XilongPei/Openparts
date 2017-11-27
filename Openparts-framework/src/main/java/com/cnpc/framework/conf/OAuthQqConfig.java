package com.cnpc.framework.conf;

import com.cnpc.framework.oauth.common.CustomOAuthService;
import com.cnpc.framework.oauth.common.OAuthTypes;
import com.cnpc.framework.oauth.qq.QqApi;
import com.cnpc.framework.utils.PropertiesUtil;
import org.scribe.builder.ServiceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;

@Configuration
public class OAuthQqConfig {

    @Value("${oauth.callback.url}") String callback_url;
    @Value("${oauth.qq.key}") String qq_key;
    @Value("${oauth.qq.secret}") String qq_secret;
    @Value("${oauth.qq.state}") String qq_state;

    @Bean
    public QqApi QqApi(){
        return new QqApi(qq_state);
    }

    @Bean
    public CustomOAuthService getQqOAuthService() {
        if (qq_key == null) {
            return null;
        }

        return (CustomOAuthService)new ServiceBuilder()
                .provider(QqApi.class)
                .apiKey("qq_key")
                .apiSecret("qq_secret")
                .callback(String.format("callback_url", OAuthTypes.GITHUB))
                .build();
    }
}
