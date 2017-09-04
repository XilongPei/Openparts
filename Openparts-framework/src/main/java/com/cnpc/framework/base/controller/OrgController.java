package com.cnpc.framework.base.controller;

import com.cnpc.framework.base.entity.Org;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.pojo.TreeNode;
import com.cnpc.framework.base.service.OrgService;
import com.cnpc.framework.constant.RedisConstant;
import com.cnpc.framework.utils.StrUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by billJiang on 2017/6/19.
 * e-mail:475572229@qq.com  qq:475572229
 * 组织结构管理控制器
 */
@Controller
@RequestMapping("/org")
public class OrgController {

    @Resource
    private OrgService orgService;

    /**
     * 用户列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tree")
    private String list() {

        return "base/org/org_tree";
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public List<Org> getAll() {

        String hql = "from Org order by levelCode asc";
        return orgService.find(hql.toString());
    }

    /**
     * getTreeData 构造bootstrap-treeview格式数据
     *
     * @return
     */
    @RequestMapping(value = "/treeData", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeNode> getTreeData() {
        return orgService.getTreeData();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Org get(@PathVariable("id") String id) {
        Org org = orgService.get(Org.class, id);
        if (!StrUtil.isEmpty(org.getParentId())) {
            org.setParentName(orgService.get(Org.class, org.getParentId()).getName());
        } else {
            org.setParentName("组织机构维护");
        }
        return org;
    }


    @RequestMapping(value = "/show/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result show(@PathVariable("id") String id) {
        return orgService.getOrgNames(id);
    }

    @RequestMapping(value = "/getList/{code}", method = RequestMethod.POST)
    @ResponseBody
    public List<Org> getOrgsByCode(String code) {
        return orgService.getOrgsByCode(code);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Org org) {

        org.setUpdateDateTime(new Date());
        orgService.saveOrUpdate(org);
        if (!StrUtil.isEmpty(org.getParentId())) {
            Org parent = orgService.get(Org.class, org.getParentId());
            orgService.deleteCacheByKey(RedisConstant.ORG_PRE + parent.getCode());
        }
        orgService.deleteCacheByKey(RedisConstant.ORG_PRE + "tree");
        return new Result(true);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable("id") String id) {

        try {
            Org org = orgService.get(Org.class, id);
            if(orgService.referByUser(org.getId())) {
               return new Result(false,"该机构或子机构被用户表引用，不可删除");
            }
            orgService.delete(org);
            if (!StrUtil.isEmpty(org.getParentId())) {
                Org parent = orgService.get(Org.class, org.getParentId());
                orgService.deleteCacheByKey(RedisConstant.ORG_PRE + parent.getCode());
            }
            orgService.deleteCacheByKey(RedisConstant.ORG_PRE + "tree");
            return new Result(true);
        } catch (Exception e) {
            return new Result(false, "该组织机构已经被其他数据引用，不可删除");
        }
    }
}
