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

/**
 * Author: qing
 * Date: 14-10-11
 */
public class MongoDBClient {

    protected MongoDBDriver mongoDBDriver;

    protected String databaseName;

    public void setMongoDBDriver(MongoDBDriver mongoDBDriver) {
        this.mongoDBDriver = mongoDBDriver;
    }



    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        MongoDatabase db = mongoDBDriver.getDatabase(this.databaseName);
        return db.getCollection(collectionName);
    }

    public MongoDatabase getDatabase() {
        return mongoDBDriver.getDatabase(this.databaseName);
    }


    public static void main(String[] args) throws Exception{

        MongoDBDriver mongoDBDriver = new MongoDBDriver();
        try {
            MongoDBConfig mongoDBConfig = new MongoDBConfig();
            //mongoDBConfig.setAddresses("61.171.123.234:27017");
            mongoDBConfig.setAddresses("61.171.123.234:27017");
            List<MongoDBCredential> credentials = new ArrayList<MongoDBCredential>();
            MongoDBCredential credential = new MongoDBCredential();
            credential.setDatabaseName("whatsmars-common");
            credential.setUsername("whatsmars");
            //credential.setPassword("haodai.com");
            credential.setPassword("passwordiscommon");
            credentials.add(credential);
            mongoDBConfig.setCredentials(credentials);
            mongoDBDriver.setConfiguration(mongoDBConfig);
            mongoDBDriver.init();
            MongoDBClient client = new MongoDBClient();
            client.setDatabaseName("whatsmars-common");
            client.setMongoDBDriver(mongoDBDriver);
            ListCollectionsIterable<Document> documents = client.getDatabase().listCollections();
            MongoCursor<Document> it = documents.iterator();
            while (it.hasNext()) {
                Document item = it.next();
                System.out.println(item.toJson());
            }
            it.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoDBDriver.close();
        }
    }


}
