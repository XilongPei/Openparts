package com.openparts.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.openparts.base.service.MongodbService;
import com.cnpc.framework.utils.StrUtil;
import com.cnpc.framework.utils.SpringContextUtil;
import com.cnpc.framework.base.entity.BaseEntity;
import com.openparts.base.entity.OP_BaseEntity;
import org.springframework.stereotype.Service;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.MongoWriteException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoException;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.*;
import org.bson.types.ObjectId;
import com.cnpc.framework.constant.RedisConstant;
import com.openparts.base.service.impl.MongodbDaoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * reference:
 *
 * http://api.mongodb.com/java/current/com/mongodb/client/MongoCollection.html
 */

@Service("mongodbService")
public class MongodbServiceImpl implements MongodbService {

    private static final Logger logger = LoggerFactory.getLogger(MongodbServiceImpl.class);

    @Resource
    private MongodbDaoClient mongodbDaoClient;

    public void createCollection(String collectionName) {
        MongoDatabase db = mongodbDaoClient.getDatabase();
        db.createCollection(collectionName);
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        MongoDatabase db = mongodbDaoClient.getDatabase();
        return db.getCollection(collectionName);
    }

    /**
      * 将字符串转换成org.bson.Document
      */
    public boolean collectionInsertOneJson(MongoCollection<Document> collection, String json) {
        Document document = Document.parse(json);

        try {
            collection.insertOne(document);
        } catch (MongoWriteException e) {
            // the write failed due some other failure specific to the insert command
            logger.debug(ExceptionUtils.getStackTrace(e));
            return false;
        } catch (MongoWriteConcernException e) {
            // the write failed due being unable to fulfil the write concern
            logger.debug(ExceptionUtils.getStackTrace(e));
            return false;
        } catch (MongoException e) {
            // the write failed due some other failure
            logger.debug(ExceptionUtils.getStackTrace(e));
            return false;
        }

        return true;
    }


    /**
      * 将字符串转换成org.bson.Document
      */
    public boolean collectionInsertManyJson(MongoCollection<Document> collection, String[] jsons) {

        List<Document> documents = new ArrayList<Document>();
        Document document;

        for (int i = 0;  i < jsons.length;  i++) {
            document = Document.parse(jsons[i]);
            documents.add(document);
        }

        try {
            collection.insertMany(documents);
        } catch (MongoBulkWriteException e) {
            // the write failed due some other failure
            return false;
        } catch (MongoException e) {
            // the write failed due some other failure
            return false;
        }

        return true;
    }

    public String[] listAllJsons(MongoCollection<Document> collection) {
        String[] jsons;

        int jsonNum = (int)collection.count();
        jsons = new String[jsonNum];

        int i = 0;
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                jsons[i++] = cursor.next().toJson();
            }
        } finally {
            cursor.close();
        }

        return jsons;
    }

    public String firstOneJson(MongoCollection<Document> collection) {

        String json = collection.find().first().toJson();

        return json;
    }

    public String findOneJson(MongoCollection<Document> collection, Bson filter) {

        String json = collection.find(filter).first().toJson();

        return json;
    }

    public boolean beanToMongodb(MongoCollection<Document> collection, String className, Object object) {

        if (className == null) {
            className = object.getClass().getName();
        }
        if (collection == null) {
            collection = getCollection(RedisConstant.NOSQL_TABLE_PRE + className);
        }

        String json =  JSON.toJSONString(object);
        Document document = Document.parse(json);

        /*
            _id Field
            If the document does not specify an _id field, then mongod will add the _id field and assign
            a unique ObjectId for the document before inserting. Most drivers create an ObjectId and
            insert the _id field, but the mongod will create and populate the _id if the driver or
            application does not.

            If the document contains an _id field, the _id value must be unique within the collection
            to avoid duplicate key error.
        */
        String idStr = document.get("id").toString();
        if (idStr != null) {
            document.remove("id");
            document.put("_id", idStr);
        }

        try {
            collection.insertOne(document);
        } catch (MongoWriteException e) {
            // the write failed due some other failure specific to the insert command
            logger.debug(ExceptionUtils.getStackTrace(e));
            return false;
        } catch (MongoWriteConcernException e) {
            // the write failed due being unable to fulfil the write concern
            logger.debug(ExceptionUtils.getStackTrace(e));
            return false;
        } catch (MongoException e) {
            // the write failed due some other failure
            logger.debug(ExceptionUtils.getStackTrace(e));
            return false;
        }

        return true;
    }

    public <T> T beanFromMongodb(MongoCollection<Document> collection, Class<T> classOfT, String id) {

        if (collection == null) {
            collection = getCollection(RedisConstant.NOSQL_TABLE_PRE + classOfT.getName());
        }

        Document document = collection.find(eq("_id", id)).first();
        String idStr = document.get("_id").toString();
        document.remove("_id");
        document.put("id", idStr);

        String jsonInString = document.toString();

        return JSON.parseObject(jsonInString, classOfT);
    }

    public long beanDeleteMongodb(MongoCollection<Document> collection, Object object) {

        if (collection == null) {
            collection = getCollection(RedisConstant.NOSQL_TABLE_PRE + object.getClass().getName());
        }

        String id;
        if (object instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity)object;
            id = baseEntity.getId();
        } else if (object instanceof OP_BaseEntity) {
            OP_BaseEntity oP_BaseEntity = (OP_BaseEntity)object;
            id = oP_BaseEntity.getId().toString();
        } else {
            return 0;
        }

        DeleteResult deleteResult;
        try {
            deleteResult = collection.deleteMany(new Document("_id", id));
        } catch (Exception e) {
            // the write failed due some other failure
            logger.debug(ExceptionUtils.getStackTrace(e));
            return 0;
        }

        return deleteResult.getDeletedCount();
    }

    public long deleteByIdMongodb(MongoCollection<Document> collection, String id) {

        if (collection == null) {
            return 0;
        }

        DeleteResult deleteResult;
        try {
            deleteResult = collection.deleteMany(new Document("_id", id));
        } catch (Exception e) {
            // the write failed due some other failure
            logger.debug(ExceptionUtils.getStackTrace(e));
            return 0;
        }

        return deleteResult.getDeletedCount();
    }

    /**
     * 读取mongo库内所有的数据库名称
     */
    /*
            MongoCursor<String> dbs=client.listDatabaseNames().iterator();
            System.out.println("mongodb中数据库有：");
            while(dbs.hasNext())
            {
                System.out.println(dbs.next());
            }
    */

    /*
     * curl -d "className=com.openparts.base.service.impl.MongodbServiceImpl&methodName=testMain" -X POST http://localhost:8081/Openparts-web/api/test
     */
    public static void testMain() {
        MongodbServiceImpl mongodbServiceImpl = new MongodbServiceImpl();
        if (mongodbServiceImpl.mongodbDaoClient == null) {
            mongodbServiceImpl.mongodbDaoClient = (MongodbDaoClient)SpringContextUtil.getBean("mongodbDaoClient");
        }

        //mongodbServiceImpl.createCollection("test");
        MongoCollection<Document> collection = mongodbServiceImpl.getCollection("TBL_com.cnpc.framework.base.entity.Dict");

        mongodbServiceImpl.deleteByIdMongodb(collection, "402881ec54b7493f0154b7514e8f0008");

        String json = "{\"a\": 3}";
        mongodbServiceImpl.collectionInsertOneJson(collection, json);

        String[] jsons = new String[3];
        jsons[0] = "{\"name\": \"Tongji\"}";
        jsons[1] = "{\"name\": \"Shanghai\"}";
        jsons[2] = "{\"name\": \"China\"}";
        mongodbServiceImpl.collectionInsertManyJson(collection, jsons);
    }

}
