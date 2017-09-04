package com.cnpc.framework.oauth.service;

import com.cnpc.framework.oauth.common.CustomOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by billJiang on 2017/1/15.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
@Service
public class OAuthServices {


    @Autowired
    private List<CustomOAuthService> customOAuthServices;

    /*public OAuthServices(){
        OAuthConfig config=new OAuthConfig();
        customOAuthServices=new ArrayList<CustomOAuthService>();
        customOAuthServices.add(config.getGithubOAuthService());
    }*/

    public CustomOAuthService getOAuthService(String type) {
        CustomOAuthService oAuthService = null;
        for (CustomOAuthService customOAuthService : customOAuthServices) {
            if (customOAuthService.getoAuthType().equals(type)) {
                oAuthService = customOAuthService;
                break;
            }
        }
        return oAuthService;
    }
    public List<CustomOAuthService> getAllOAuthServices() {
        return customOAuthServices;
    }


}
