package com.openparts.utils.mongodb;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.ArrayList;
import java.util.List;

public class MongoDBConfig {

    private String addresses;
    private List<MongoDBCredential> credentials;

    MongoDBConfig(String addresses, List<MongoDBCredential> credentials) {
        this.addresses = addresses;
        this.credentials = credentials;
    }

    public String getAddresses() {
        return addresses;
    }

    public List<MongoDBCredential> getCredentials() {
        return credentials;
    }

    /**
     * 连接副本集，MongoClient会自动识别出
     * MongoDB复制集里Primary节点是不固定的，当遇到复制集轮转升级、Primary宕机、网络分区等场景时，复制集可能会选举出一个新的Primary，
     * 而原来的Primary则会降级为Secondary，即发生主备切换。
     * 当连接复制集时，如果直接指定Primary的地址来连接，当时可能可以正确读写数据的，但一旦复制集发生主备切换，你连接的Primary会降级为Secondary，
     * 你将无法继续执行写操作，这将严重影响到你的线上服务。
     */

    public List<MongoCredential> buildCredentials() {
        List<MongoCredential> mongoCredentials = new ArrayList<MongoCredential>();
        for(MongoDBCredential item : this.credentials) {
            MongoCredential credential = MongoCredential.createCredential(item.getUsername(), item.getDatabaseName(), item.getPassword().toCharArray());
            mongoCredentials.add(credential);
        }
        return mongoCredentials;
    }

    public List<ServerAddress> buildAddresses() throws Exception {
        List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
        String[] sources = addresses.split(";");
        for(String item : sources) {
            String[] hp = item.split(":");
            serverAddresses.add(new ServerAddress(hp[0],Integer.valueOf(hp[1])));
        }
        return serverAddresses;
    }
}
