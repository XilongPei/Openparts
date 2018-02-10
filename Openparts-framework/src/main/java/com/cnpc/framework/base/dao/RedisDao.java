package com.cnpc.framework.base.dao;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import java.util.List;
import java.util.Set;

/**
 * Created by billJiang on 2017/4/10.
 * e-mail:475572229@qq.com  qq:475572229
 */
public interface RedisDao {
    <T> boolean add(final String key, final T obj);

    /**
     * setNx
     *
     * @param key
     * @param value
     * @return
     */
    boolean add(final String key, final String value);

    <T> boolean add(final String key, final List<T> list);

    void delete(final String key);

    void delete(final Set<String> keys);

    <T> boolean update(final String key, final T obj);

    boolean update(final String key, final String value);

    /**
     * 保存 不存在则新建，存在则更新
     *
     * @param key
     * @param value
     * @return
     */
    boolean save(final String key, final String value);

    <T> boolean save(final String key, final T obj);

    <T> T get(final String key, final Class clazz);

    <T> List<T> getList(final String key, final Class<T> clazz);

    byte[] getByte(final String key);

    String get(final String key);

    <T> void add(final String key, final long timeout, final T obj);

    void add(final String key, final long timeout, final byte[] object);

    Set<String> keys(String pattern);

    boolean exist(final String key);

    boolean set(final String key,final byte[] value);

    boolean flushDB();

    long dbSize();

    //==========================================================================

    /**
     * HashOperations<String,String,Object> opsForHash;
     * put, putAll, entries, ......
     */
    HashOperations getHashOperations();

    /**
     * ListOperations<String, Object> opsForList;
     * rightPush, leftPop, ......
     */
    ListOperations getListOperations();

    /**
     * SetOperations<String, Object> opsForSet;
     *
     */
    SetOperations getSetOperations();
}
