package com.cnpc.framework.base.pojo;

import com.cnpc.framework.annotation.Header;
import com.cnpc.framework.base.entity.Dict;
import com.cnpc.framework.utils.PingYinUtil;
import com.cnpc.framework.utils.StrUtil;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

public class FieldDictInfo {
    private Field field;
    private String tagType;
    private String dictCode;
    private Method methodGet;
    private Method methodSet;

    public static FieldDictInfo getFieldDictInfo(Class<?> objClass, Field field) {

    	FieldDictInfo fieldDictInfo = new FieldDictInfo();
        String fieldName = field.getName();

        //将属性的首字符大写，方便构造get，set方法
        fieldName = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        String dataSource = field.getAnnotation(Header.class).dataSource();
        if (StrUtil.isEmpty(dataSource) || (field.getType() != String.class)) {
            return null;
        }

        try {
        	fieldDictInfo.setMethodGet(objClass.getMethod("get" + fieldName));
        	fieldDictInfo.setMethodSet(objClass.getMethod("set"+fieldName, String.class));
        } catch (NoSuchMethodException ex) {
        	return null;
        }

        fieldDictInfo.setDictCode(dataSource);
        fieldDictInfo.setField(field);

        return fieldDictInfo;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public Method getMethodGet() {
        return methodGet;
    }

    public void setMethodGet(Method methodGet) {
        this.methodGet = methodGet;
    }

    public Method getMethodSet() {
        return methodSet;
    }

    public void setMethodSet(Method methodSet) {
        this.methodSet = methodSet;
    }
}
