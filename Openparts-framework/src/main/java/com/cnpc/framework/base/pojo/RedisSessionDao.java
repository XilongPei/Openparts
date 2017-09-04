package com.cnpc.framework.base.pojo;


import com.cnpc.framework.base.dao.RedisDao;
import com.cnpc.framework.base.entity.BaseEntity;
import com.cnpc.framework.constant.RedisConstant;
import com.cnpc.framework.utils.StrUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by billJiang on 2017/4/13.
 * e-mail:475572229@qq.com  qq:475572229
 * 通过如下方式调用
 * RedisSessionDao redisSession = (RedisSessionDao)SpringContextUtil.getBean("redisSessionDao");
 */
//@Service("redisSessionDao")
public class RedisSessionDao extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    private long expire;

    @Resource
    private RedisDao redisDao;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }

        logger.debug("Read Redis.SessionId=" + new String(getKey(RedisConstant.SHIRO_REDIS_SESSION_PRE, sessionId.toString())));

        Session session = (Session) SerializationUtils.deserialize(redisDao.getByte(getKey(RedisConstant.SHIRO_REDIS_SESSION_PRE, sessionId.toString())));
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    public void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        session.setTimeout(expire);
        long timeout = expire / 1000;
        //保存用户会话
        redisDao.add(this.getKey(RedisConstant.SHIRO_REDIS_SESSION_PRE, session.getId().toString()), timeout, SerializationUtils.serialize(session));
        //获取用户id
        String uid = getUserId(session);
        if (!StrUtil.isEmpty(uid)) {
            //保存用户会话对应的UID
            try {
                redisDao.add(this.getKey(RedisConstant.SHIRO_SESSION_PRE, session.getId().toString()), timeout, uid.getBytes("UTF-8"));
                //保存在线UID
                redisDao.add(this.getKey(RedisConstant.UID_PRE, uid), timeout, "online".getBytes("UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                logger.error("getBytes error:" + ex.getMessage());
            }
        }

    }


    public String getUserId(Session session) {
        SimplePrincipalCollection pricipal = (SimplePrincipalCollection) session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
        if (null != pricipal) {
            return pricipal.getPrimaryPrincipal().toString();
        }
        return null;
    }

    public String getKey(String prefix, String keyStr) {
        return prefix + keyStr;
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        //删除用户会话
        redisDao.delete(this.getKey(RedisConstant.SHIRO_REDIS_SESSION_PRE, session.getId().toString()));
        //获取缓存的用户会话对应的UID
        String uid = redisDao.get(this.getKey(RedisConstant.SHIRO_SESSION_PRE, session.getId().toString()));
        //删除用户会话sessionid对应的uid
        redisDao.delete(this.getKey(RedisConstant.SHIRO_SESSION_PRE, session.getId().toString()));
        //删除在线uid
        redisDao.delete(this.getKey(RedisConstant.UID_PRE, uid));
        //删除用户缓存的角色
        redisDao.delete(this.getKey(RedisConstant.ROLE_PRE, uid));
        //删除用户缓存的权限
        redisDao.delete(this.getKey(RedisConstant.PERMISSION_PRE, uid));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();

        Set<String> keys = redisDao.keys(RedisConstant.SHIRO_REDIS_SESSION_PRE + "*");
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Session s = (Session) SerializationUtils.deserialize(redisDao.getByte(key));
                sessions.add(s);
            }
        }
        return sessions;
    }

    /**
     * 当前用户是否在线
     *
     * @param uid 用户id
     * @return
     */
    public boolean isOnLine(String uid) {
        Set<String> keys = redisDao.keys(RedisConstant.UID_PRE + uid);
        if (keys != null && keys.size() > 0) {
            return true;
        }
        return false;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
