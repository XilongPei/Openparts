package com.openparts.utils.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import com.openparts.base.service.impl.ElasticSearchServiceImpl;

public class ESClientContainer {

    private static ThreadLocal<RestHighLevelClient> clientContainer = new ThreadLocal<>();

    public static void putRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        clientContainer.set(restHighLevelClient);
    }

    public static RestHighLevelClient getRestHighLevelClient() {
        RestHighLevelClient restHighLevelClient = clientContainer.get();

        if (restHighLevelClient == null) {
            ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
            restHighLevelClient = elasticSearchServiceImpl.initialize();
        }

        return restHighLevelClient;
    }

    public static void clear(){
        clientContainer.remove();
    }
}
