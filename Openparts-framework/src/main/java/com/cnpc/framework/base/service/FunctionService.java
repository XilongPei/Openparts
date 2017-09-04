package com.cnpc.framework.base.service;

import java.util.List;
import java.util.Set;

import com.cnpc.framework.base.entity.Function;
import com.cnpc.framework.base.pojo.TreeNode;

public interface FunctionService extends BaseService {

    List<TreeNode> getTreeData();

    List<Function> getAll();

    Set<String> getFunctionCodeSet(Set<String> roleCodes,String userId);

    Set<String> getAllFunctionCode();

    List<Function> getFunctionList(Set<String> roleCodes,String userId);
}
