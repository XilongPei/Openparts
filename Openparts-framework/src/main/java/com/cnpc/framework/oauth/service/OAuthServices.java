package com.cnpc.framework.oauth.service;

import com.cnpc.framework.oauth.common.CustomOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import com.cnpc.framework.conf.OAuthGithubConfig;
import com.cnpc.framework.conf.OAuthWeixinConfig;
import com.cnpc.framework.conf.OAuthQqConfig;

@Service
public class OAuthServices {

    @Autowired
    private List<CustomOAuthService> customOAuthServices;

    public OAuthServices() {

        customOAuthServices = new ArrayList<CustomOAuthService>();

        OAuthWeixinConfig configWeixin = new OAuthWeixinConfig();
        customOAuthServices.add(configWeixin.getWeixinOAuthService());

        OAuthGithubConfig configGithub = new OAuthGithubConfig();
        customOAuthServices.add(configGithub.getGithubOAuthService());

        OAuthQqConfig configQq = new OAuthQqConfig();
        customOAuthServices.add(configQq.getQqOAuthService());
    }

    public CustomOAuthService getOAuthService(String type) {
        for (CustomOAuthService customOAuthService : customOAuthServices) {
            if (customOAuthService.getoAuthType().equals(type)) {
                return customOAuthService;
            }
        }
        return null;
    }

    public List<CustomOAuthService> getAllOAuthServices() {
        return customOAuthServices;
    }


}
