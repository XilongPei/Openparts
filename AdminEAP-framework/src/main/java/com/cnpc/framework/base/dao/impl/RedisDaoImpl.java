package com.cnpc.framework.base.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.cnpc.framework.base.dao.RedisDao;
import com.cnpc.framework.utils.StrUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by billJiang on 2017/4/10.
 * e-mail:475572229@qq.com  qq:475572229
 * redis操作实体类
 */
@Service("redisDao")
public class RedisDaoImpl implements RedisDao {

    @Resource
    protected RedisTemplate<String, Serializable> redisTemplate;

    /**
     * 设置redisTemplate
     *
     * @param redisTemplate the redisTemplate to set
     */
    public void setRedisTemplate(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Serializable> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 获取 RedisSerializer
     */
    private RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }


    @Override
    public <T> boolean add(final String key, final T obj) {
        return add(key, JSON.toJSONString(obj));
    }

    @Override
    public <T> void add(final String key, final long timeout, final T obj) {
        redisTemplate.execute(new RedisCallback() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                final byte[] object = serializer.serialize(JSON.toJSONString(obj));
                add(key,timeout,object);
                return null;
            }


        });
    }


    @Override
    public void add(final String key, final long timeout,final byte[] object) {
        redisTemplate.execute(new RedisCallback() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                connection.setEx(keyStr, timeout, object);
                return null;
            }
        });
    }


    @Override
    public boolean add(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] object = serializer.serialize(value);
                return connection.setNX(keyStr, object);
            }
        });
        return result;
    }

    @Override
    public <T> boolean add(final String key, final List<T> list) {
        return this.add(key, JSON.toJSONString(list));
    }


    @Override
    public void delete(final String key) {
        List<String> list = new ArrayList<>();
        list.add(key);
        this.delete(list);
    }

    @Override
    public void delete(final List<String> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public <T> boolean update(final String key, final T obj) {
        return this.update(key, JSON.toJSONString(obj));
    }


    @Override
    public boolean update(final String key, final String value) {
        if (get(key) == null) {
            throw new NullPointerException("数据行不存在, key = " + key);
        }
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] valueStr = serializer.serialize(value);
                connection.set(keyStr, valueStr);
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean save(String key, String value) {
        if (StrUtil.isEmpty(get(key))) {
            return this.add(key, value);
        } else {
            return this.update(key, value);
        }
    }

    @Override
    public <T> boolean save(String key, T obj) {
        if (get(key, obj.getClass()) == null) {
            return this.add(key, obj);
        } else {
            return this.update(key, obj);
        }
    }

    @Override
    public <T> T get(final String key, final Class clazz) {
        T result = redisTemplate.execute(new RedisCallback<T>() {
            public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                if (value == null) {
                    return null;
                }
                String valueStr = serializer.deserialize(value);
                return (T) JSON.parseObject(valueStr, clazz);
            }
        });
        return result;
    }


    @Override
    public <T> List<T> getList(final String key, final Class<T> clazz) {
        List<T> result = redisTemplate.execute(new RedisCallback<List<T>>() {
            public List<T> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                if (value == null) {
                    return null;
                }
                String valueStr = serializer.deserialize(value);
                return JSON.parseArray(valueStr, clazz);
            }
        });
        return result;
    }



    @Override
    public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                if (value == null) {
                    return null;
                }
                String valueStr = serializer.deserialize(value);
                return valueStr;
            }
        });
        return result;
    }

    @Override
    public byte[] getByte(final String key) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            public byte[] doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                return value;
            }
        });
        return result;
    }

    @Override
    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public boolean exist(final String key){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                return connection.exists(keyStr);
            }
        });
        return result;
    }


    @Override
    public boolean set(final String key,final byte[] value){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String>  serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                connection.set(keyStr, value);
                return true;
            }
        });
        return result;
    }

    public boolean flushDB(){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                return true;
            }
        });
        return result;
    }

    public long dbSize(){
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.dbSize();
            }
        });
        return result;
    }

}

