package com.openparts.utils.mongodb;

public class MongoDBCredential {

    private String databaseName;
    private String username;
    private String password;

    MongoDBCredential(String databaseName, String username, String password) {
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
