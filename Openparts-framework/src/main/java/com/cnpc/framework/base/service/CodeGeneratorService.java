package com.cnpc.framework.base.service;

/**
 * Created by billJiang on 2017/2/6.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
public interface CodeGeneratorService extends BaseService {

    /** 根据类名和父ID获取levelcode
     * @param className 类名
     * @param pId 父ID
     * @return
     */
    public String getLevelCode(String className, String pId);
}
