package com.cnpc.demos.service;

import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.service.BaseService;

/**
 * Created by HANZO on 2016/6/17.
 */
public interface UserDemoService extends BaseService {

    User saveUser(User user);

}
