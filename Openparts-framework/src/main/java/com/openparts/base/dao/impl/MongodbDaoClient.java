package com.openparts.base.dao.impl;

import com.openparts.utils.mongodb.GridFSClient;
import com.openparts.utils.mongodb.MongoDBClient;
import com.openparts.utils.mongodb.MongoDBConfig;
import com.openparts.utils.mongodb.MongoDBCredential;
import com.openparts.utils.mongodb.MongoDBDriver;
import org.springframework.beans.factory.InitializingBean;
import java.util.ArrayList;
import java.util.List;

public class MongodbDaoClient extends MongoDBClient implements InitializingBean {

    private String dbname;
    private String username;
    private String password;
    private String addresses;

    public MongodbDaoClient() {
    	super(null, null);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    	databaseName = dbname;
    	
        List<MongoDBCredential> credentials = new ArrayList<MongoDBCredential>();
        MongoDBCredential credential = new MongoDBCredential(dbname, username, password);
        credentials.add(credential);

        MongoDBConfig mongoDBConfig = new MongoDBConfig(addresses, credentials);

        this.mongoDBDriver = new MongoDBDriver(mongoDBConfig);
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getDbname() {
        return dbname;
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
