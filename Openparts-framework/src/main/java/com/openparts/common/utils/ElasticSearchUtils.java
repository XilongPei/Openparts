package com.openparts.common.utils;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.rest.RestStatus;

/**
 * https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html
 */
public class ElasticSearchUtils {

    /**
     * CREATED(201),
     */
    public static int getStatusIndexResponse(IndexResponse response) {
        if (response == null) {
            return 0;
        }

        RestStatus restStatus = response.status();
        return restStatus.getStatus();
    }


    /**
     *
     */
    public static boolean getStatusDeleteResponse(DeleteResponse response) {
        if (response == null) {
            return false;
        }

        DocWriteResponse.Result result = response.getResult();
        if (result == DocWriteResponse.Result.DELETED) {
            return true;
        }
        return false;
    }

    public static void testMain() {
        String jsonString = "{" + "\"user\":\"kimchy\"," + "\"postDate\":\"2013-01-30\","
                + "\"message\":\"trying out Elasticsearch\"" + "}";

        //System.out.println(getStatusIndexResponse(response));
    }
}
