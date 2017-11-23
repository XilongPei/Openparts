package com.cnpc.tool.message.service.impl;

import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.impl.BaseServiceImpl;
import com.cnpc.framework.constant.RedisConstant;
import com.cnpc.framework.message.SimpleMailSender;
import com.cnpc.framework.util.SecurityUtil;
import com.cnpc.framework.utils.StrUtil;
import com.cnpc.tool.message.entity.Message;
import com.cnpc.tool.message.entity.MessageGroup;
import com.cnpc.tool.message.entity.MessageGroupUser;
import com.cnpc.tool.message.entity.MessageReceiver;
import com.cnpc.tool.message.pojo.MessageConstant;
import com.cnpc.tool.message.service.NavbarService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("NavbarService")
public class NavbarServiceImpl extends BaseServiceImpl implements NavbarService {

    private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public Map getNavbarNumber() {
        Map<String, Integer> retMap = redisDao.get(RedisConstant.MESSAGE_PRE+"count:"+SecurityUtil.getUserId(),Map.class);
        if (retMap == null) {
            retMap = new HashMap<>();

            //收件箱
            String hql_inbox = "select count(id) from MessageReceiver where receiveUserID='" + SecurityUtil.getUserId() + "' and deleted=0";
            Long count_inbox = this.get(hql_inbox);
            retMap.put("inbox", count_inbox.intValue());

            redisDao.add(RedisConstant.MESSAGE_PRE+"navbarnumber:"+SecurityUtil.getUserId(), retMap);
            return retMap;
        }
        else {
            return retMap;
        }
    }

    @Override
    public void deleteCacheForNavbarNumber() {
        redisDao.delete(RedisConstant.MESSAGE_PRE+"navbarnumber:"+SecurityUtil.getUserId());
    }


}
