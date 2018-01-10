package com.openparts.common.utils;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.action.search.SearchResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html
 */
public class ElasticSearchUtils {

    private static Logger logger = LoggerFactory.getLogger(ElasticSearchUtils.class);

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

    /**
     *
     */
    public static String[] getStringsSearchResponse(SearchResponse response) {
        SearchHits searchHits = response.getHits();
        long l = searchHits.getTotalHits();
        String[] strs = new String[(int)l];

        for (int i = 0;  i < (int)l; i++) {
            String sourceAsString;

            try {
                sourceAsString = searchHits.getAt(i).getSourceAsString();
            } catch (Exception e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                sourceAsString = null;
            }

            strs[i] = sourceAsString;
        }

        return strs;
    }

    public static void testMain() {
        String jsonString = "{" + "\"user\":\"kimchy\"," + "\"postDate\":\"2013-01-30\","
                + "\"message\":\"trying out Elasticsearch\"" + "}";

        //System.out.println(getStatusIndexResponse(response));
    }
}
