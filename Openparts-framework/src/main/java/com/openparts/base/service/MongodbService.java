package com.openparts.base.service;

/**
 * 这里只是一些辅助函数，更复杂的功能直接操作: MongoCollection<Document> , 文档参见:
 * http://api.mongodb.com/java/current/com/mongodb/client/MongoCollection.html
 *
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.BooleanCodec;
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
import org.bson.conversions.Bson;

public interface MongodbService {

    void createCollection(String collectionName);

    MongoCollection<Document> getCollection(String collectionName);

    boolean collectionInsertOneJson(MongoCollection<Document> collection, String json);

    boolean collectionInsertManyJson(MongoCollection<Document> collection, String[] json);

    String[] listAllJsons(MongoCollection<Document> collection);

    String firstOneJson(MongoCollection<Document> collection);

    String findOneJson(MongoCollection<Document> collection, Bson filter);

    boolean beanToMongodb(MongoCollection<Document> collection, String className, Object object);

    <T> T beanFromMongodb(MongoCollection<Document> collection, Class<T> classOfT, String id);

    long beanDeleteMongodb(MongoCollection<Document> collection, Object object);

    long deleteByIdMongodb(MongoCollection<Document> collection, String id);
}
