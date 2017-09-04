package com.cnpc.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.Subcriteria;

public class ObjectUtil {

    /**
     * 根据属性名获取getter setter方法名
     *
     * @param field  字段名
     * @param prefix 前缀
     * @return
     */
    public static String methodName(String field, String prefix) {

        if (field == null)
            return null;
        if (field.length() > 1 && Character.isUpperCase(field.charAt(1)))
            return prefix + field;
        else
            return prefix + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /**
     * 根据属性名获取值
     *
     * @param obj   对象
     * @param field 字段名
     * @return
     */
    public static Object getValueByKey(Object obj, String field) {

        try {
            Method method = null;
            if (obj == null || StrUtil.isBlank(field))
                return null;
            String[] fieldArr = field.split("[.]");
            for (String str : fieldArr) {
                method = obj.getClass().getMethod(methodName(str, "get"));
                obj = method.invoke(obj);
            }
            // System.out.println("value:"+value);
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将对象object特定方法的返回值（
     *
     * @param obj    对象
     * @param field  方法
     * @param format 格式
     * @return
     */
    public static String ObjectToString(Object obj, String field, String format) throws Exception {

        try {
            Method method = null;
            if (obj == null || StrUtil.isBlank(field))
                return null;
            String[] fieldArr = field.split("[.]");
            for (String str : fieldArr) {
                if (method != null)
                    obj = method.invoke(obj);
                method = obj.getClass().getMethod(methodName(str, "get"));
            }
            // System.out.println("value:"+value);
            return ObjectToString(obj, method, format);

        } catch (Exception e) {
            return null;
        }

    }

    public static Object ObjectSetValue(Object obj, String field, Object value) throws Exception {

        try {
            Object retObject = obj;
            Method getMethod = null;
            Method setMethod = null;
            if (obj == null || StrUtil.isBlank(field))
                return null;
            String[] fieldArr = field.split("[.]");
            for (String str : fieldArr) {
                if (getMethod != null) {
                    Object childObject = getMethod.getReturnType().newInstance();
                    setMethod.invoke(obj, childObject);
                    obj = childObject;
                }
                getMethod = obj.getClass().getMethod(methodName(str, "get"));
                setMethod = obj.getClass().getMethod(methodName(str, "set"), getMethod.getReturnType());
            }
            // System.out.println("value:"+value);
            if (value == null || StrUtil.isEmpty(value.toString()))
                value = null;
            else {
                if (getMethod.getReturnType().equals(Integer.class)) {
                    value = Integer.valueOf(value.toString());
                } else if (getMethod.getReturnType().equals(Double.class)) {
                    value = Double.valueOf(value.toString());
                } else if (getMethod.getReturnType().equals(Float.class)) {
                    value = Float.valueOf(value.toString());
                } else if (getMethod.getReturnType().equals(Date.class)) {
                    value = DateUtil.parseToDate(value.toString());
                }
                setMethod.invoke(obj, value);
            }

            return retObject;
            // return (obj, method, format);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    /*
     * public static void main(String[] args) { JIANG jiang = new JIANG(); try {
     * Object object = ObjectSetValue(jiang, "name", "test");
     * System.out.println(jiang.getPolitic()); } catch (Exception e) { // TODO
     * Auto-generated catch block e.printStackTrace(); }
     * 
     * }
     */

    /**
     * 将对象object特定方法的返回值（主要是get方法）按照format格式转化为字符串类型
     *
     * @param object 对象
     * @param method 方法
     * @param format 格式
     * @return
     */
    public static String ObjectToString(Object object, Method method, String format) throws Exception {

        if (object == null || method == null)
            return null;
        // 时间类型
        if (method.getReturnType().getName().equals(Date.class.getName())) {
            if (StrUtil.isEmpty(format))
                return DateUtil.format((Date) method.invoke(object));
            else
                return DateUtil.format((Date) method.invoke(object), format);
        }

        return method.invoke(object).toString();

    }

    public static DetachedCriteria getCriteriaWithAlias(DetachedCriteria criteria, String columnName) {

        if (columnName.indexOf(".") == -1)
            return criteria;
        String[] nameArr = columnName.split("[.]");
        for (int index = 0; index < nameArr.length - 1; index++) {
            String str = nameArr[index];
            if (index > 0 && !isExistAlias((DetachedCriteria) criteria, "" + nameArr[index - 1] + "." + str + "")) {
                criteria.createAlias("" + nameArr[index - 1] + "." + str + "", "" + str + "", DetachedCriteria.LEFT_JOIN);
            }
            if (index == 0 && !isExistAlias((DetachedCriteria) criteria, str)) {
                criteria.createAlias("" + str + "", "" + str + "", DetachedCriteria.LEFT_JOIN);
            }
        }
        return criteria;
    }

    @SuppressWarnings("unused")
    public static boolean isExistAlias(DetachedCriteria impl, String path) {

        try {
            Field field = DetachedCriteria.class.getDeclaredField("impl");
            field.setAccessible(true);
            CriteriaImpl criteriaImpl = (CriteriaImpl) field.get(impl);
            Iterator iterator = criteriaImpl.iterateSubcriteria();
            for (; iterator.hasNext(); ) {
                Subcriteria subcriteria = (Subcriteria) iterator.next();
                if (subcriteria.getPath().equals(path)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
