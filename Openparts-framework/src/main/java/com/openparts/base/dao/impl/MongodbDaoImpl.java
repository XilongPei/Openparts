package com.openparts.base.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.openparts.base.dao.MongodbDao;
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

@Service("mongodbDao")
public class MongodbDaoImpl implements MongodbDao {

    @Resource
    protected RedisTemplate<String, Serializable> redisTemplate;

    /*
    public MongoCollection<Document> getCollection(String collectionName) {
        MongoDatabase db = mongoDBDriver.getDatabase(this.databaseName);
        return db.getCollection(collectionName);
    }

    public MongoDatabase getDatabase() {
        return mongoDBDriver.getDatabase(this.databaseName);
    }
    */
}

