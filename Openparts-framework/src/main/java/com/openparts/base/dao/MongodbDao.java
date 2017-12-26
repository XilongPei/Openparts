package com.openparts.base.dao;

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
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface MongodbDao {

    void createCollection(String collectionName);

    MongoCollection<Document> getCollection(String collectionName);

    boolean collectionInsertOneJson(MongoCollection<Document> collection, String json);

    boolean collectionInsertManyJson(MongoCollection<Document> collection, String[] json);

    String[] listAllJsons(MongoCollection<Document> collection);
}
