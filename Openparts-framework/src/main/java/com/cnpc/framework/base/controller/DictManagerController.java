package com.cnpc.framework.base.controller;

import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.cnpc.framework.base.entity.Dict;
import com.cnpc.framework.utils.StrUtil;
import com.cnpc.framework.utils.SpringContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cnpc.framework.annotation.RefreshCSRFToken;
import com.cnpc.framework.annotation.VerifyCSRFToken;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.DictService;

@Controller
@RequestMapping("/dict_manager")
public class DictManagerController {

    private DictService dictService = (DictService)SpringContextUtil.getBean("dictService");

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list() {
        return "base/dict/dict_list";
    }

    @RefreshCSRFToken
    @RequestMapping(value="/edit",method = RequestMethod.GET)
    public String edit(String id,HttpServletRequest request) {
        request.setAttribute("id", id);
        return "base/dict/dict_edit";
    }

    @RequestMapping(value="/detail",method = RequestMethod.GET)
    public String detail(String id,HttpServletRequest request) {
        request.setAttribute("id", id);
        return "base/dict/dict_detail";
    }

    @RequestMapping(value="/get/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Dict get(@PathVariable("id") String id) {
        if (dictService == null) {
            dictService = (DictService)SpringContextUtil.getBean("dictService");
        }

        return dictService.get(Dict.class, id);
    }

    @VerifyCSRFToken
    @RequestMapping(value="/save")
    @ResponseBody
    public Result save(Dict dict) {
        if (dictService == null) {
            dictService = (DictService)SpringContextUtil.getBean("dictService");
        }

        if(StrUtil.isEmpty(dict.getId())){
            dictService.save(dict);
        } else {
            dictService.update(dict);
        }
        return new Result(true);
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable("id") String id) {
        Dict dict = this.get(id);

        if (dictService == null) {
            dictService = (DictService)SpringContextUtil.getBean("dictService");
        }

        try {
            dictService.delete(dict);
            return new Result();
        }
        catch(Exception e) {
            return new Result(false,"该数据已经被引用，不可删除");
        }
    }
}
