package com.cnpc.framework.base.service.impl;

import com.cnpc.framework.base.dao.RedisDao;
import com.cnpc.framework.base.entity.Function;
import com.cnpc.framework.base.entity.FunctionFilter;
import com.cnpc.framework.base.entity.RoleFunction;
import com.cnpc.framework.base.pojo.TreeNode;
import com.cnpc.framework.base.service.FunctionService;
import com.cnpc.framework.constant.RedisConstant;
import com.cnpc.framework.utils.TreeUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("functionService")
public class FunctionServiceImpl extends BaseServiceImpl implements FunctionService {

    @Resource
    private RedisDao redisDao;


    @Override
    public List<TreeNode> getTreeData() {

        // 获取数据
        String hql = "from Function order by levelCode asc";
        List<Function> funcs = this.find(hql);
        Map<String, TreeNode> nodelist = new LinkedHashMap<String, TreeNode>();
        for (Function func : funcs) {
            TreeNode node = new TreeNode();
            node.setText(func.getName());
            node.setId(func.getId());
            node.setParentId(func.getParentId());
            node.setLevelCode(func.getLevelCode());
            node.setIcon(func.getIcon());
            nodelist.put(node.getId(), node);
        }
        // 构造树形结构
        return TreeUtil.getNodeList(nodelist);
    }

    @Override
    public List<Function> getAll() {
        String hql = "from Function where (deleted=0 or deleted is null) order by levelCode";
        return this.find(hql);
    }

    @Override
    public Set<String> getFunctionCodeSet(Set<String> roleCodes,String userId) {
        /*int len = roleCodes.size();
        if (len == 0)
            return null;
        String sql = PropertiesUtil.getValue("shiro.sql.permissions");
        Map<String, Object> params = new HashMap<String, Object>();
        String[] strs = new String[len];
        int i = 0;
        for (String roleCode : roleCodes) {
            strs[i++] = "'" + roleCode + "'";
        }
        //无法注入进去参数
        //params.put("param",StrUtil.join(strs));
        sql = sql.replace(":param", StrUtil.join(strs));
        List<Map<String, Object>> list = super.findMapBySql(sql, params);
        Set<String> retSet = new HashSet<String>();
        for (Map map : list) {
            retSet.add(map.get("code").toString());
        }
        return retSet;*/
        //以上注释，如放开需要将shiro.sql.permissions f.* 改为f.code
        if(roleCodes.size()==0)
            return null;
        List<Function> functions = getFunctionList(roleCodes,userId);
        Set<String> retSet = new HashSet<>();
        for (Function function : functions) {
            retSet.add(function.getCode());
        }
        return retSet;
    }

    @Override
    public Set<String> getAllFunctionCode() {
        Set<String> sets = new HashSet<>();
        List<Function> functions = this.getAll();
        for (Function function : functions) {
            sets.add(function.getCode());
        }
        return sets;
    }


    @Override
    public List<Function> getFunctionList(Set<String> roleCodes,String userId) {
        String key = RedisConstant.PERMISSION_PRE + userId;
        List<Function> functionList = redisDao.getList(key,Function.class);
        if (functionList == null) {
            String sql = "select rf.* from tbl_role_function rf left join tbl_role r on rf.roleId=r.id where r.code in (:roleCodes)";
            //String[] strs = (String[])roleCodes.toArray();
            Map<String, Object> params = new HashMap<>();
            params.put("roleCodes", roleCodes);
            List<RoleFunction> roleFunctionList = super.findBySql(sql, params, RoleFunction.class);
            functionList = this.getFunctionListWithoutRepeat(roleFunctionList);
            redisDao.add(key, functionList);
        }

        return functionList;

    }

    //functionList去重，并注入数据权限FunctionFilter
    public List<Function> getFunctionListWithoutRepeat(List<RoleFunction> roleFunctions){
        List<Function> list=new ArrayList<Function>();
        Map<String,Boolean> map=new HashMap<>();
        for (RoleFunction roleFunction : roleFunctions) {
           if(map.containsKey(roleFunction.getFunctionId())){
               Function function=getFunctionById(roleFunction.getFunctionId(),list);
               List<FunctionFilter> fflist=getFunctionFilters(roleFunction.getRoleId(),roleFunction.getFunctionId());
               // 数据权限合并，以大数据范围优先，
               // TODO 数据权限选择逻辑还需要进一步优化，可设置优先级
               if(fflist.size()<function.getFflist().size()){
                 function.setFflist(fflist);
               }
           }else{
               map.put(roleFunction.getFunctionId(),true);
               Function function=this.get(Function.class,roleFunction.getFunctionId());
               //获取数据权限
               List<FunctionFilter> fflist=getFunctionFilters(roleFunction.getRoleId(),roleFunction.getFunctionId());
               function.setFflist(fflist);
               list.add(function);
           }

        }
        return list;
    }

    public Function getFunctionById(String id,List<Function> functions){
        for (Function function : functions) {
            if(function.getId().equals(id)){
                return function;
            }
        }
        return null;
    }

    public List<FunctionFilter> getFunctionFilters(String roleId,String functionId){
        String hql="from FunctionFilter where roleId=:roleId and functionId=:functionId order by sort";
        Map<String,Object> param=new HashMap<>();
        param.put("roleId",roleId);
        param.put("functionId",functionId);
        return this.find(hql,param);
    }
}
