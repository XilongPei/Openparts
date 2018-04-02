package com.openparts.utilsWeb;

import com.cnpc.framework.utils.StrUtil;
import com.cnpc.framework.utils.SessionUtil;

/**
 *
 */
public class KcaptchaUtil {

    public int checkCode(String captcha) {

        //从session中获取系统生成的验证码
        String verifyCodeExpected = (String)SessionUtil.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

        if (StrUtil.isEqual(captcha, verifyCodeExpected)) {
            return 0;
        }

        return 1;
    }
}
