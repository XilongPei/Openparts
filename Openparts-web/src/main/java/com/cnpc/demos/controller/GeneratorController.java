package com.cnpc.demos.controller;

import com.alibaba.fastjson.JSON;
import com.cnpc.demos.entity.Generator;
import com.cnpc.framework.annotation.RefreshCSRFToken;
import com.cnpc.framework.annotation.VerifyCSRFToken;
import com.cnpc.framework.base.entity.Dict;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.BaseService;
import com.cnpc.framework.utils.StrUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
* 测试管理控制器
* @author jrn
* 2017-02-20 14:15:46由代码生成器自动生成
*/
@Controller
@RequestMapping("/generator")
public class GeneratorController {

    @Resource
    private BaseService baseService;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list(){
        return "demos/generator_list";
    }

    @RefreshCSRFToken
    @RequestMapping(value="/edit",method = RequestMethod.GET)
    public String edit(String id,HttpServletRequest request){
        request.setAttribute("id", id);
        return "demos/generator_edit";
    }

    @RequestMapping(value="/detail",method = RequestMethod.GET)
    public String detail(String id,HttpServletRequest request){
        request.setAttribute("id", id);
        return "demos/generator_detail";
    }

    @RequestMapping(value="/get/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Generator get(@PathVariable("id") String id){
        return baseService.get(Generator.class, id);
    }

    @VerifyCSRFToken
    @RequestMapping(value="/save")
    @ResponseBody
    public Result save(String obj){
        Generator generator= JSON.parseObject(obj,Generator.class);
        generator.setNation(baseService.get(Dict.class,generator.getNation().getId()));
        if(StrUtil.isEmpty(generator.getId())){
            baseService.save(generator);
        }
        else{
            Generator generator_old=baseService.get(Generator.class,generator.getId());
            BeanUtils.copyProperties(generator,generator_old, "email","remark");
            generator_old.setUpdateDateTime(new Date());
            baseService.update(generator_old);
        }
        return new Result(true);
    }



    @RequestMapping(value="/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable("id") String id){
        Generator generator=this.get(id);
        try{
            baseService.delete(generator);
            return new Result();
        }
        catch(Exception e){
            return new Result(false,"该数据已经被引用，不可删除");
        }
    }



}
