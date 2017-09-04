package com.cnpc.framework.base.pojo;

import com.cnpc.framework.base.dao.RedisDao;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by billJiang on 2017/4/15.
 * e-mail:475572229@qq.com  qq:475572229
 */
public class RedisCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    @Resource
    private RedisDao redisDao;

    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");
        Cache c = caches.get(name);
        if (c == null) {
            c = new RedisCache<K, V>(redisDao);
            caches.put(name, c);
        }
        return (Cache<K, V>) c;
    }
}
