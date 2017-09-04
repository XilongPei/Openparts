package com.cnpc.demos.service.impl;

import org.springframework.stereotype.Service;

import com.cnpc.demos.service.UserDemoService;
import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.service.impl.BaseServiceImpl;

/**
 * Created by HANZO on 2016/6/17.
 */
@Service("userDemoService")
public class UserDemoServiceImpl extends BaseServiceImpl implements UserDemoService {

    @Override
    public User saveUser(User user) {
        User res = null;

        if (user != null) {
            String id = user.getId();
            if (id == null || "".equals(id.trim())) {
                this.save(user);
                res = user;
            } else {
                User old = this.get(User.class, id);
                if (old != null) {
                    old.setName(user.getName());
                    old.setSex(user.getSex());
                    old.setBirthday(user.getBirthday());
                    old.setLoginName(user.getLoginName());
                    old.setTelphone(user.getTelphone());
                    old.setMobile(user.getMobile());
                    old.setEmail(user.getEmail());
                    old.setQq(user.getQq());

                    this.update(old);

                    res = old;
                }
            }
        }

        return res;
    }
}
