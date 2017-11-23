package com.cnpc.tool.message.service;

import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.BaseService;
import com.cnpc.tool.message.entity.Message;
import com.cnpc.tool.message.entity.MessageGroup;
import com.cnpc.tool.message.entity.MessageGroupUser;

import java.util.List;
import java.util.Map;

public interface NavbarService extends BaseService {

    Map getNavbarNumber();

    public void deleteCacheForNavbarNumber();
}
