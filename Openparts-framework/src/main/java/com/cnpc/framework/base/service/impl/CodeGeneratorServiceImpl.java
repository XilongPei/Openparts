package com.cnpc.framework.base.service.impl;

import com.cnpc.framework.base.service.CodeGeneratorService;
import com.cnpc.framework.utils.CodeUtil;
import com.cnpc.framework.utils.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by billJiang on 2017/2/6.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
@Service("codeGeneratorService")
public class CodeGeneratorServiceImpl extends BaseServiceImpl implements CodeGeneratorService {
    private static final Logger logger= LoggerFactory.getLogger(CodeGeneratorServiceImpl.class);

    public String getLevelCode(String className, String pId) {

        try {
            Class<?> objClass = Class.forName(className);
            Object parentObj = this.get(objClass, pId);
            Object newObj = objClass.newInstance();
            String initCode = "000001";
            Method methodSet = objClass.getMethod("setLevelCode", String.class);
            if (parentObj == null) {
                return initCode;
            }
            Method methodGet = objClass.getMethod("getLevelCode");
            String parentCode = (String) methodGet.invoke(parentObj);
            methodSet.invoke(newObj, parentCode + "0%");
            int len = parentCode.length() + 6;
            String condition = "LENGTH(levelCode)=" + len;
            Object maxCodeObj = this.getMaxByExample(newObj, "levelCode", condition, true);
            if (maxCodeObj == null) {
                methodSet.invoke(newObj, parentCode + initCode);
                return parentCode + initCode;
            }
            String maxCode=maxCodeObj.toString();
            maxCode=CodeUtil.nextCode(parentCode,maxCode,6);
            return maxCode;
        } catch (Exception ex) {
            System.out.println(ex.getMessage().toString());
            logger.error("生成levelCode失败，错误信息为："+ex.getMessage().toString());
            return null;
        }
    }

}
