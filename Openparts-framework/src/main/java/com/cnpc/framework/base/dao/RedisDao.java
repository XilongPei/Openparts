package com.cnpc.framework.base.dao;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
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
    //
    // function like Bound...
    // 对key的“bound”(绑定)便捷化操作API，可以通过bound封装指定的key，
    // 然后进行一系列的操作而无须“显式”的再次指定Key，即BoundKeyOperations.


    /**
     * HashOperations<String,String,Object> opsForHash;
     * put, putAll, entries, ......
     */
    HashOperations getHashOperations();
    BoundHashOperations getBoundHashOperations(String certainKey);

    /**
     * ListOperations<String, Object> opsForList;
     * rightPush, leftPop, ......
     */
    ListOperations getListOperations();
    BoundListOperations getBoundListOperations(String certainKey);

    /**
     * SetOperations<String, Object> opsForSet;
     *
     */
    SetOperations getSetOperations();
    BoundSetOperations getBoundSetOperations(String certainKey);

    /**
     * ZSetOperations<String, Object, Long> opsForZSet;
     *
     */
    ZSetOperations getZSetOperations();
    BoundZSetOperations getBoundZSetOperations(String certainKey);
}
