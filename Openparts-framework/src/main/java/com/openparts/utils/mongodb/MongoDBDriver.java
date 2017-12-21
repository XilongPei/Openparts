package com.openparts.utils.mongodb;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang.StringUtils;
import java.util.Properties;
import com.cnpc.framework.utils.PropertiesUtil;

public class MongoDBDriver {

    private MongoClient mongo = null;

    private Integer connectionsPerHost = 32;
    private Integer threadsAllowedToBlockForConnectionMultiplier = 5;
    private Integer maxWaitTime = 30000;
    private Integer connectTimeout = 30000;
    private Integer socketTimeout = 30000;

    private Integer maxConnectionIdle = 6000;


    private MongoDBConfig configuration;

    MongoDBDriver(MongoDBConfig configuration) throws Exception {
        this.configuration = configuration;

        connectionsPerHost = PropertiesUtil.getIntValue("mongodb.driver.connectionsPerHost", 32);
        threadsAllowedToBlockForConnectionMultiplier = PropertiesUtil.getIntValue("mongodb.driver.maxWaitTime", 30000);
        connectionsPerHost = PropertiesUtil.getIntValue("mongodb.driver.connectTimeout", 30000);
        socketTimeout = PropertiesUtil.getIntValue("mongodb.driver.socketTimeout", 30000);
        maxConnectionIdle = PropertiesUtil.getIntValue("mongodb.driver.maxConnectionIdle", 30000);

        ////init,db check and connected.
        MongoClientOptions.Builder builder = MongoClientOptions.builder();

        builder.connectionsPerHost(this.connectionsPerHost);
        builder.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
        builder.socketKeepAlive(true);
        builder.maxWaitTime(this.maxWaitTime);
        builder.connectTimeout(this.connectTimeout);
        builder.socketTimeout(this.socketTimeout);
        builder.maxConnectionIdleTime(maxConnectionIdle);

        MongoClientOptions options = builder.build();

        this.mongo = new MongoClient(configuration.buildAddresses(), configuration.buildCredentials(), options);
    }


    public void close() {
        mongo.close();
    }

    public Mongo getMongo() {
        return mongo;
    }

    public MongoDatabase getDatabase(String dbName) {
        return mongo.getDatabase(dbName);
    }

    /**
     * old api
     * @param dbName
     * @return
     */
    public DB getDB(String dbName) {
        return mongo.getDB(dbName);
    }

}
