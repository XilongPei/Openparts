package com.openparts.utils.mongodb;

public class MongoDBCredential {

    // mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
    // mongodb:// 前缀，代表这是一个Connection String
    // ?options 指定额外的连接选项

    // database 鉴权时，用户帐号所属的数据库
    private String databaseName;

    //username:password@ 如果启用了鉴权，需要指定用户密码
    private String username;
    private String password;

    public MongoDBCredential(String databaseName, String username, String password) {
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
