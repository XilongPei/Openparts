package com.cnpc.framework.message;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮箱登陆验证
 * Created by billJiang on 2016/12/27.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
public class MailAuthenticator extends Authenticator {
    /**
     * 用户名（登陆邮箱）
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public MailAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
