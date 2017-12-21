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

    MongoDBClient(MongoDBDriver mongoDBDriver, String databaseName) {
        this.mongoDBDriver = mongoDBDriver;
        this.databaseName = databaseName;
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        MongoDatabase db = mongoDBDriver.getDatabase(this.databaseName);
        return db.getCollection(collectionName);
    }

    public MongoDatabase getDatabase() {
        return mongoDBDriver.getDatabase(this.databaseName);
    }
}
