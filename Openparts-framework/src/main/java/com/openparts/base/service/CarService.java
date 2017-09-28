package com.openparts.base.service;

import com.cnpc.framework.base.entity.Dict;
import com.cnpc.framework.base.entity.Org;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.pojo.TreeNode;
import com.cnpc.framework.base.service.BaseService;

import java.util.List;

public interface CarService extends BaseService {

    List<TreeNode> getTreeData();

    List<Org> getOrgsByCode(String code);

    boolean referByUser(String orgId);

    Result getOrgNames(String id);
}
