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

    public MongoDBDriver(MongoDBConfig configuration) throws Exception {
        this.configuration = configuration;

        connectionsPerHost = PropertiesUtil.getIntValue("mongodb.driver.connectionsPerHost", 32);
        threadsAllowedToBlockForConnectionMultiplier = PropertiesUtil.getIntValue("mongodb.driver.maxWaitTime", 30000);
        connectionsPerHost = PropertiesUtil.getIntValue("mongodb.driver.connectTimeout", 30000);
        socketTimeout = PropertiesUtil.getIntValue("mongodb.driver.socketTimeout", 30000);
        maxConnectionIdle = PropertiesUtil.getIntValue("mongodb.driver.maxConnectionIdle", 30000);

        MongoClientOptions.Builder builder = MongoClientOptions.builder();

        builder.connectionsPerHost(this.connectionsPerHost);
        builder.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
        builder.socketKeepAlive(true);
        builder.maxWaitTime(this.maxWaitTime);
        builder.connectTimeout(this.connectTimeout);
        builder.socketTimeout(this.socketTimeout);
        builder.maxConnectionIdleTime(maxConnectionIdle);

        /**
         * 在options里添加readPreference=secondaryPreferred即可实现，读请求优先到Secondary节点，从而实现读写分离的功能
         * 在options里添加maxPoolSize=xx即可将客户端连接池限制在xx以内。
         * 在options里添加w= majority即可保证写请求成功写入大多数节点才向客户端确认
         */
        MongoClientOptions options = builder.build();

        this.mongo = new MongoClient(configuration.buildAddresses(), configuration.buildCredentials(), options);
    }


    public void close() {
        mongo.close();
    }

    public Mongo getMongo() {
        return mongo;
    }

    /**
     * com.mongodb.client.MongoDatabase
     */
    public MongoDatabase getDatabase(String dbName) {
        return mongo.getDatabase(dbName);
    }

    /**
     * com.mongodb.DB
     * a logical database on a server
     */
    public DB getDB(String dbName) {
        return mongo.getDB(dbName);
    }

}
