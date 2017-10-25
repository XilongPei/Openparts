package com.openparts.base.service;

import java.util.List;

import com.cnpc.framework.base.entity.Dict;
import com.cnpc.framework.base.pojo.TreeNode;
import com.cnpc.framework.base.service.BaseService;

public interface UtilsService extends BaseService {

    void saveVerifyString(String mobile, String strVerify);

    String getVerifyString(String mobile);
}
