package com.openparts.base.service.impl;

import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import com.openparts.utils.mongodb.GridFSClient;
import com.openparts.utils.mongodb.MongoDBConfig;
import com.openparts.utils.mongodb.MongoDBCredential;
import com.openparts.utils.mongodb.MongoDBDriver;
import org.springframework.beans.factory.InitializingBean;
import com.cnpc.framework.utils.StrUtil;
import java.util.ArrayList;
import java.util.List;

public class MongodbDaoClient implements InitializingBean {

    // database 鉴权时，用户帐号所属的数据库
    private String adminDbName = null;

    // 用户数据所属的数据库
    private String dbName = null;

    private MongoDBDriver mongoDBDriver = null;
    private String username = null;
    private String password = null;
    private String addresses = null;

    @Override
    public void afterPropertiesSet() throws Exception {

        if (StrUtil.isBlank(adminDbName))
            return;

        List<MongoDBCredential> credentials = new ArrayList<MongoDBCredential>();
        MongoDBCredential credential = new MongoDBCredential(adminDbName, username, password);
        credentials.add(credential);

        MongoDBConfig mongoDBConfig = new MongoDBConfig(addresses, credentials);

        mongoDBDriver = new MongoDBDriver(mongoDBConfig);
    }


    public MongoDatabase getDatabase() {
        return mongoDBDriver.getDatabase(dbName);
    }

    public DB getDB() {
        return mongoDBDriver.getDB(dbName);
    }

    public void setAdminDbName(String adminDbName) {
        this.adminDbName = adminDbName;
    }

    public String getAdminDbName() {
        return adminDbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getAddresses() {
        return addresses;
    }
}
