package com.openparts.utils.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClient;
import org.apache.http.HttpHost;

/*
 * Java REST Client [6.1] » Java High Level REST Client » Supported APIs
 * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-supported-apis.html
 */

class ElasticsearchClient {

    private RestHighLevelClient client;

    ElasticsearchClient() {
        client = new RestHighLevelClient(
            RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http")));
    }

    public void close() {
        try {
            client.close();
        } catch (Exception ex) {
            return;
        }

    }
}
