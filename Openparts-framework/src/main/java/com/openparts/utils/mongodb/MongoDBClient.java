package com.openparts.utils.mongodb;

import com.openparts.utils.mongodb.MongoDBConfig;
import com.openparts.utils.mongodb.MongoDBCredential;
import com.openparts.utils.mongodb.MongoDBDriver;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDBClient {

    protected MongoDBDriver mongoDBDriver;
    protected String databaseName;

    public MongoDBClient(MongoDBDriver mongoDBDriver, String databaseName) {
        this.mongoDBDriver = mongoDBDriver;
        this.databaseName = databaseName;
    }

    public MongoDBDriver getMongoDBDriver() {
        return mongoDBDriver;
    }
}
