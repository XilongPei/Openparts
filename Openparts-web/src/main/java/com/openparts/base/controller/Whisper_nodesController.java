package com.openparts.base.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.base.entity.Dict;
import com.cnpc.framework.utils.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnpc.framework.base.service.BaseService;
import com.cnpc.framework.annotation.RefreshCSRFToken;
import com.cnpc.framework.annotation.VerifyCSRFToken;
import com.cnpc.framework.base.pojo.Result;
import com.openparts.base.entity.Whisper_nodes;

/**
* whisper_nodes管理控制器
* @author jrn
* 2017-11-17 15:05:27由代码生成器自动生成
*/
@Controller
@RequestMapping("/whisper_nodes")
public class Whisper_nodesController {

    @Resource
    private BaseService baseService;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list(){
        return "base/whisper_nodes_list";
    }

    @RefreshCSRFToken
    @RequestMapping(value="/edit",method = RequestMethod.GET)
    public String edit(String id,HttpServletRequest request){
        request.setAttribute("id", id);
        return "base/whisper_nodes_edit";
    }

    @RequestMapping(value="/detail",method = RequestMethod.GET)
    public String detail(String id,HttpServletRequest request){
        request.setAttribute("id", id);
        return "base/whisper_nodes_detail";
    }

    @RequestMapping(value="/get/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Whisper_nodes get(@PathVariable("id") String id){
        return baseService.get(Whisper_nodes.class, id);
    }

    @VerifyCSRFToken
    @RequestMapping(value="/save")
    @ResponseBody
    public Result save(Whisper_nodes whisper_nodes){
        if(StrUtil.isEmpty(whisper_nodes.getId())){
            baseService.save(whisper_nodes);
        }
        else{
            baseService.update(whisper_nodes);
        }
        return new Result(true);
    }



    @RequestMapping(value="/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable("id") String id){
        Whisper_nodes whisper_nodes=this.get(id);
        try{
            baseService.delete(whisper_nodes);
            return new Result();
        }
        catch(Exception e){
            return new Result(false,"该数据已经被引用，不可删除");
        }
    }



}
