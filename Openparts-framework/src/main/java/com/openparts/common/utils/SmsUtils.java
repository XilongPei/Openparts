package com.openparts.common.utils;

/*
 * http://www.ihuyi.com/demo/sms/java.html
 */

// 接口类型：互亿无线触发短信接口，支持发送验证码短信、订单通知短信等。
// 账户注册：请通过该地址开通账户http://sms.ihuyi.com/register.html
// 注意事项：
//（1）调试期间，请用默认的模板进行测试，默认模板详见接口文档；
//（2）请使用 用户名(例如：cf_demo123)及 APIkey来调用接口，APIkey在会员中心可以获取；
//（3）该代码仅供接入互亿无线短信接口参考使用，客户可根据实际需要自行编写；

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.openparts.base.service.UtilsService;
//import util.StringUtil;

public class SmsUtils {



    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
    static String account = "用户名"; //用户名是登录ihuyi.com账号名（例如：cf_demo123）
    static String password = "密码";  //查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY

    public static String sendSms(UtilsService utilsService, String mobile) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");

        int mobile_code = (int)((Math.random()*9+1)*100000);

        //store mobile_code into database for verify mobile
        utilsService.saveVerifyString(mobile,  Integer.toString(mobile_code).substring(0,6));

        String content = new String("您的验证码是：" + mobile_code + "，请在60秒内完成验证。如非本人操作，请忽略。");

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", account),
                new NameValuePair("password", password), //查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", mobile),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);

        try {
            //client.excuteMethod(method);

            String SubmitResult =method.getResponseBodyAsString();

            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);

            if("2".equals(code)){
                //return "短信提交成功";
                return "success";
            }

        } catch (HttpException e) {
            return null;
            //e.printStackTrace();
        } catch (IOException e) {
            return null;
            //e.printStackTrace();
        } catch (DocumentException e) {
            return null;
            //e.printStackTrace();
        }
        return "";
    }
}

