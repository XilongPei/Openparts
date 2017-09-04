package com.cnpc.framework.base.controller;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.base.entity.FunctionFilter;
import com.cnpc.framework.base.entity.RoleFunction;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.RoleFunctionService;
import com.cnpc.framework.utils.StrUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by billJiang on 2017/1/3.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 * 角色授权
 */
@Controller
@RequestMapping(value = "/rolefunc")
public class RoleFunctionController {

    @Resource
    private RoleFunctionService roleFunctionService;

    //角色授权管理主界面
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private String list() {
        return "base/auth/rolefunc_list";
    }

    //跳转到授权页面 restful风格
    @RequestMapping(value = "/select/{roleId}", method = RequestMethod.GET)
    private String select(@PathVariable("roleId") String roleId, HttpServletRequest request) {
        request.setAttribute("roleId", roleId);
        return "base/auth/rolefunc_select";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    private Result delete(@PathVariable("id") String id) {
        return roleFunctionService.deleteRoleFunction(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    private Result save(RoleFunction rfobj) {
        return roleFunctionService.saveRoleFunction(rfobj);
    }


    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    private RoleFunction get(String roleId, String functionId) {
        return roleFunctionService.getRoleFunction(roleId, functionId);
    }

    /**
     * 批量保存角色授权（角色绑定功能）
     *
     * @param rflist 角色绑定功能列表
     * @return
     */
    @RequestMapping(value = "/save_batch", method = RequestMethod.POST)
    @ResponseBody
    public Result saveBatch(String roleId, String rflist) {
        List<RoleFunction> roleFunctionList = JSON.parseArray(rflist, RoleFunction.class);
        return roleFunctionService.saveBatchRoleFunction(roleId, roleFunctionList);
    }

    /**
     * 批量保存
     *
     * @param roleId
     * @param functionId
     * @param fflist
     * @return
     */
    @RequestMapping(value = "/ff/save_batch", method = RequestMethod.POST)
    @ResponseBody
    public Result saveBatch_ff(String roleId, String functionId, String fflist) {
        List<FunctionFilter> functionFilterList = JSON.parseArray(fflist, FunctionFilter.class);
        return roleFunctionService.saveBatchFunctionFilter(roleId, functionId, functionFilterList);
    }

    /**
     * 数据权限编辑
     * @param roleId 角色ID
     * @param functionId 功能ID
     * @param id 编辑时实体ID
     * @param request
     * @return
     */
    @RequestMapping(value = "/ff/edit/{roleId}/{functionId}/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("roleId") String roleId, @PathVariable("functionId") String functionId, @PathVariable("id") String id, HttpServletRequest request) {
        request.setAttribute("roleId_ff",roleId);
        request.setAttribute("functionId_ff",functionId);
        request.setAttribute("id",id);
        return "base/auth/rolefunc_ff_edit";
    }

    /**
     * 保存数据权限
     * @param ff 数据权限实体
     * @return 执行结果
     */
    @RequestMapping(value="/ff/save",method = RequestMethod.POST)
    @ResponseBody
    public Result save_ff(FunctionFilter ff){
        if(StrUtil.isEmpty(ff.getId()))
            roleFunctionService.save(ff);
        else {
            roleFunctionService.update(ff);
            ff.setCreateDateTime(new Date());
        }
        return new Result();
    }

    /**
     * 删除数据权限
     * @param id
     * @return 执行结果
     */
    @RequestMapping(value="/ff/delete/{id}",method=RequestMethod.POST)
    @ResponseBody
    public Result delete_ff(@PathVariable("id") String id){
        FunctionFilter ff=roleFunctionService.get(FunctionFilter.class,id);
        roleFunctionService.delete(ff);
        return new Result();
    }

    /**
     * 获取数据权限
     * @param id
     * @return
     */
    @RequestMapping(value="/ff/get/{id}",method=RequestMethod.POST)
    @ResponseBody
    public FunctionFilter get_ff(@PathVariable("id") String id ){
        return roleFunctionService.get(FunctionFilter.class,id);
    }
}
