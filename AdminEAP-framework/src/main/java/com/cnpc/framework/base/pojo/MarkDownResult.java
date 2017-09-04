package com.cnpc.framework.base.pojo;

/**
 * Created by cnpc on 2016/12/9.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
public class MarkDownResult {
    private Integer success;

    private String message;

    private String url;

    public MarkDownResult(){

    }

    public MarkDownResult(Integer success){
       this.success=success;
    }

    public MarkDownResult(Integer success, String message, String url){
        this.success=success;
        this.message=message;
        this.url=url;
    }

    public MarkDownResult(Integer success, String url){
        this.success=success;
        this.url=url;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
