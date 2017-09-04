package com.cnpc.framework.base.controller;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.BaseService;
import com.cnpc.framework.utils.ObjectUtil;
import com.cnpc.framework.utils.StrUtil;

@Controller
@RequestMapping("/base")
public class BaseController {

    @Resource
    private BaseService baseService;

    /**
     * 唯一性校验
     *
     * @param className  类名
     * @param fieldName  属性名
     * @param fieldValue 属性值
     * @param id         当前对象id
     * @return Map 校验通过
     */
    @RequestMapping("/checkUnique")
    @ResponseBody
    public Map checkUnique(String className, String fieldName, String fieldValue, String id) {

        Map<String, Boolean> map = new HashMap<String, Boolean>();
        try {
            Class<?> objClass = Class.forName(className);
            Object obj = objClass.newInstance();
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = objClass.getMethod(ObjectUtil.methodName(fieldName, "set"), String.class);
            method.invoke(obj, fieldValue);
            String condition = null;
            if (!StrUtil.isEmpty(id))
                condition = "{alias}.id!='" + id + "'";
            List objList = baseService.findByExample(obj, condition, false);
            map.put("valid", objList.isEmpty());
            return map;
        } catch (Exception ex) {
            System.out.println(ex.getMessage().toString());
            map.put("valid", false);
            return map;
        }
    }

    @RequestMapping("/getServerTime")
    @ResponseBody
    public Date getServerTime() {
        return new Date();
    }

    /**
     * 通用删除记录
     *
     * @param className 类名
     * @param id        主键id
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public Result deleteObj(String className, String id) {

        try {
            Assert.notNull(id, "id 不能为空");
            Class<?> objClass = Class.forName(className);
            Object obj = baseService.get(objClass, id);
            baseService.delete(obj);
            return new Result(true, "删除成功！");
        } catch (ClassNotFoundException ex) {
            return new Result(false, "该类不存在！");
        } catch (IllegalArgumentException ex) {
            return new Result(false, "id不能为空！");
        } catch (Exception ex) {
            return new Result(false, "该数据已经被其他数据引用，不可删除！");
        }
    }

}
