package com.cnpc.framework.message;

import com.cnpc.framework.message.entity.SimpleMail;
import com.cnpc.framework.utils.PropertiesUtil;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 简单邮件发送器
 * Created by billJiang on 2016/12/27.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
public class SimpleMailSender {

    /**
     * 发送邮件的props文件
     */
    private final transient Properties props = System.getProperties();

    /**
     * 邮件服务器登录验证
     */
    private transient MailAuthenticator authenticator;

    /**
     * 邮箱session
     */
    private transient Session session;

    /**
     * 初始化邮件发送器
     */
    public SimpleMailSender() {
        init();
    }

    private void init() {
        //初始化props
        props.put("mail.smtp.auth", PropertiesUtil.getValue("mail.smtp.auth"));
        props.put("mail.smtp.host", PropertiesUtil.getValue("mail.smtp.host"));
        props.put("mail.smtp.port", PropertiesUtil.getValue("mail.smtp.port"));
        //验证
        authenticator = new MailAuthenticator(PropertiesUtil.getValue("mail.username"), PropertiesUtil.getValue("mail.password"));
        //创建session
        session = Session.getInstance(props, authenticator);

    }


    /**
     * 发送邮件
     *
     * @param recipient 收件人邮箱地址
     * @param subject   邮件主题
     * @param content   邮件内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(String recipient, String subject, Object content)
            throws AddressException, MessagingException {
        // 创建mime类型邮件
        final MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // 设置收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));
        // 设置主题
        message.setSubject(subject);
        message.setSentDate(new Date());
        // 设置邮件内容
        message.setContent(content.toString(), "text/html;charset=utf-8");
        // 发送
        Transport.send(message);
    }

    /**
     * 群发邮件
     *
     * @param recipients 收件人们
     * @param subject    主题
     * @param content    内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, String subject, Object content)
            throws AddressException, MessagingException {
        // 创建mime类型邮件
        final MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        message.setSentDate(new Date());
        // 设置收件人们
        final int num = recipients.size();
        InternetAddress[] addresses = new InternetAddress[num];
        for (int i = 0; i < num; i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(MimeMessage.RecipientType.TO, addresses);
        // 设置主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setContent(content.toString(), "text/html;charset=utf-8");
        // 发送
        Transport.send(message);
    }

    /**
     * 发送邮件
     *
     * @param recipient 收件人邮箱地址
     * @param mail      邮件对象
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(String recipient, SimpleMail mail)
            throws AddressException, MessagingException {
        send(recipient, mail.getSubject(), mail.getContent());
    }

    /**
     * 群发邮件
     *
     * @param recipients 收件人们
     * @param mail       邮件对象
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, SimpleMail mail)
            throws AddressException, MessagingException {
        send(recipients, mail.getSubject(), mail.getContent());
    }

}
