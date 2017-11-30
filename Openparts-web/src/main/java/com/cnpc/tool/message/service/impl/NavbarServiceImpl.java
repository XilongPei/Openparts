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
import com.openparts.conf.OpenpartsProperties;
import java.util.*;

@Service("NavbarService")
public class NavbarServiceImpl extends BaseServiceImpl implements NavbarService {

    private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public Map getNavbarNumber() {
        Map<String, String> retMap = redisDao.get(RedisConstant.MESSAGE_PRE+"count:"+SecurityUtil.getUserId(),Map.class);

        retMap.put("msg_num", "128");
        retMap.put("msg_str", String.format(OpenpartsProperties.getValue("msg_str"), 128));
        retMap.put("wrn_num", "138");
        retMap.put("wrn_str", String.format(OpenpartsProperties.getValue("wrn_str"), 138));
        retMap.put("task_num", "");
        retMap.put("task_str", String.format(OpenpartsProperties.getValue("task_str"), 188));

        String msg_menu = OpenpartsProperties.getValue("msg_menu");

        String wrn_menu =
"                  <li>\n" +
"                    <a href=\"#\">\n" +
"                      <i class=\"fa fa-users text-aqua\"></i> 5 new members joined today\n" +
"                    </a>\n" +
"                  </li>\n";
/*
                  <li>
                    <a href="#">
                      <i class="fa fa-warning text-yellow"></i> Very long description here that may not fit into the
                      page and may cause design problems
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-users text-red"></i> 5 new members joined
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-shopping-cart text-green"></i> 25 sales made
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-user text-red"></i> You changed your username
                    </a>
                  </li>
*/

        String task_menu =
"                  <li>" +
"                    <a href=\"#\">\n" +
"                      <h3>" +
"                        Design some buttons" +
"                        <small class=\"pull-right\">20%</small>" +
"                      </h3>" +
"                      <div class=\"progress xs\">" +
"                        <div class=\"progress-bar progress-bar-aqua\" style=\"width: 20%\" role=\"progressbar\" aria-valuenow=\"20\" aria-valuemin=\"0\" aria-valuemax=\"100\">" +
"                          <span class=\"sr-only\">20% Complete</span>" +
"                        </div>" +
"                      </div>" +
"                    </a>" +
"                  </li>";
/*
                  <!-- end task item -->
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        Create a nice theme
                        <small class="pull-right">40%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-green" style="width: 40%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">40% Complete</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        Some task I need to do
                        <small class="pull-right">60%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-red" style="width: 60%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        Make beautiful transitions
                        <small class="pull-right">80%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-yellow" style="width: 80%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">80% Complete</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
*/

        retMap.put("msg_menu", msg_menu);
        retMap.put("wrn_menu", wrn_menu);
        retMap.put("task_menu", task_menu);

        if (retMap == null) {
            retMap = new HashMap<>();

            //收件箱
            String hql_inbox = "select count(id) from MessageReceiver where receiveUserID='" + SecurityUtil.getUserId() + "' and deleted=0";
            Long count_inbox = this.get(hql_inbox);
            retMap.put("inbox", count_inbox.toString());

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
