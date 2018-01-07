package com.openparts.base.service;

import com.cnpc.framework.base.service.BaseService;
import java.io.IOException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;

public interface ElasticSearchService extends BaseService {

    /**
     * JSONObject SRJSON = new JSONObject(String);
     *
     * String fields: "user message"
     * String keywords: "kimchy elasticsearch"
     */
    public SearchResponse queryGeneralRequest(String fields, String keywords, String scopeField, String startDate, String endDate,
            int start, int size);

    /**
     * if (indexResponse != null && indexResponse.isCreated()) {
     *    System.out.println("Index has been created !");
     *
     *    // read report from response
     *    System.out.println("Index name: " + indexResponse.getIndex());
     *    System.out.println("Type name: " + indexResponse.getType());
     *    System.out.println("ID(optional): " + indexResponse.getId());
     *    System.out.println("Version: " + indexResponse.getVersion());
     * } else {
     *    System.err.println("Index creation failed.");
     * }
    */
    IndexResponse postRequest(String index, String type, String id, String jsonSource) throws IOException;

    IndexResponse postRquestAsync(String index, String type, String id, String jsonSource) throws IOException;

    IndexResponse beanToES(String index, String type, String id, Object object) throws IOException;

    <T> T beanFromES(String index, String type, String id, Class<T> classOfT) throws IOException;
}
