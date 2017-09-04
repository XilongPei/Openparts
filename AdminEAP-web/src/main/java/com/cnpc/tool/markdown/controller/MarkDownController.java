package com.cnpc.tool.markdown.controller;

import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.BaseService;
import com.cnpc.framework.util.SecurityUtil;
import com.cnpc.framework.utils.StrUtil;
import com.cnpc.tool.markdown.entity.MarkDown;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by cnpc on 2016/12/7.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 */
@Controller
@RequestMapping(value = "markdown")
public class MarkDownController {

    @Resource
    private BaseService baseService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "tool/markdown/markdown_edit";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "tool/markdown/markdown_list";
    }

    //保存
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(MarkDown obj) {
        obj.setUserId(SecurityUtil.getUserId());
        if (StrUtil.isEmpty(obj.getId())) {
            obj.setCreateDateTime(new Date());
            obj.setDeleted(0);
            baseService.save(obj);
        } else {
            MarkDown md = baseService.get(MarkDown.class, obj.getId());
            md.setUpdateDateTime(new Date());
            md.setTitle(obj.getTitle());
            md.setKeywords(obj.getKeywords());
            md.setContent(obj.getContent());
            md.setUserId(obj.getUserId());
            baseService.update(md);
        }
        return new Result();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable("id") String id) {
        try {
            MarkDown markDown = baseService.get(MarkDown.class, id);
            baseService.delete(markDown);
            return new Result();
        } catch (Exception ex) {
            return new Result(false);
        }
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MarkDown get(@PathVariable("id") String id) {
        return baseService.get(MarkDown.class, id);
    }

    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public String preview(String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "tool/markdown/markdown_preview";
    }

    //使用说明
    @RequestMapping(value = "/preview/{code}", method = RequestMethod.GET)
    public String previewByCode(@PathVariable("code") String code,HttpServletRequest request) {
        MarkDown markDown=baseService.get("from MarkDown where code='"+code+"'");
        request.setAttribute("id", markDown.getId());
        return "tool/markdown/markdown_preview";
    }

}
