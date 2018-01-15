package com.openparts.base.service.impl;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.*;
import org.elasticsearch.search.SearchHit;
import org.apache.http.HttpHost;
import org.springframework.stereotype.Service;
import com.openparts.utils.elasticsearch.ESClientContainer;
import com.openparts.utils.elasticsearch.RestClientAddressesBuilder;
import com.cnpc.framework.base.service.impl.BaseServiceImpl;
import com.openparts.base.service.ElasticSearchService;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.cnpc.framework.utils.StrUtil;
import com.cnpc.framework.constant.RedisConstant;
import com.cnpc.framework.base.entity.BaseEntity;
import com.openparts.base.entity.OP_BaseEntity;
import com.cnpc.framework.utils.AccessToken;
import com.cnpc.framework.utils.SpringContextUtil;
import com.cnpc.framework.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.openparts.common.utils.ElasticSearchUtils;

/**
 * https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html
 */

@Service("elasticSearchService")
public class ElasticSearchServiceImpl extends BaseServiceImpl implements ElasticSearchService {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Resource
    private RestClientAddressesBuilder restClientAddressesBuilder;

    public RestHighLevelClient initialize() {

        // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-low-usage-initialization.html
        // restClientBuilder.setMaxRetryTimeoutMillis(10000);

        if (restClientAddressesBuilder == null) {
            restClientAddressesBuilder = (RestClientAddressesBuilder)SpringContextUtil.getBean("restClientAddressesBuilder");
        }

    	RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientAddressesBuilder.builder());
        ESClientContainer.putRestHighLevelClient(restHighLevelClient);

        return restHighLevelClient;
    }

    /**
     * String fields: "user message"
     * String keywords: "kimchy elasticsearch"
     */
    public SearchResponse queryGeneralRequest(String fields, String keywords, String scopeField, String startScope, String endScope,
            int start, int size) {

        RestHighLevelClient client = ESClientContainer.getRestHighLevelClient();

        /*
            // 以下代码等同于以下的查询语句。至于ES查询语句如何使用那就要看ES的语法了
            GET /xx/xx/_search
            {
             "from":0,
             "size": 100,
             "query": {
               "bool":{
                 "must":{
                   "match":{
                     "msg":{
                       "query":"keyword1 keyword2",
                       "operator":"and"
                     }
                   }
                 },
                 "must": {
                   "range": {
                     "log_time": {
                         "lte":"2017-12-04T16:47:47,691",
                         "gte":"2017-10-17T16:54:43,847"
                     }
                   }
                 }
               }
             },
             "sort":{
               "log_time":{
                 "order":"desc"
               }
             }
            }
        */

        // 这个sourcebuilder就类似于查询语句中最外层的部分。包括查询分页的起始，
        // 查询语句的核心，查询结果的排序，查询结果截取部分返回等一系列配置
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 结果开始处
        sourceBuilder.from(start);
        // 查询结果终止处
        sourceBuilder.size(size);
        // 查询的等待时间
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        MultiMatchQueryBuilder matchbuilder;
        String[] strs = StrUtil.split(fields, ' ');
        matchbuilder = QueryBuilders.multiMatchQuery(keywords, strs);
        // 同时满足多个关键字
        matchbuilder.operator(Operator.AND);

        // 等同于bool，将两个查询合并
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(matchbuilder);

        if (!StrUtil.isBlank(scopeField)) {
            // 查询在时间区间范围内的结果
            RangeQueryBuilder matchbuilder2 = QueryBuilders.rangeQuery(scopeField);
            if (!StrUtil.isBlank(startScope)) {
                matchbuilder2.gte(startScope);
            }
            if (!StrUtil.isBlank(endScope)) {
                matchbuilder2.lte(endScope);
            }
            boolBuilder.must(matchbuilder2);

            // 排序
            FieldSortBuilder fsb = SortBuilders.fieldSort(scopeField);
            fsb.order(SortOrder.DESC);
            sourceBuilder.sort(fsb);
        }

        sourceBuilder.query(boolBuilder);

        // System.out.println(sourceBuilder);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder);

        SearchResponse response = null;
        try {
            response = client.search(searchRequest);
            //System.out.println(response);
        } catch (IOException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }

        return response;
    }

    // 同步获取操作结果
    public IndexResponse postRequest(String index, String type, String id, String jsonSource) {

        RestHighLevelClient client = ESClientContainer.getRestHighLevelClient();

        IndexRequest request = new IndexRequest(index, type, id);
        request.source(jsonSource, XContentType.JSON);

        IndexResponse response = null;
        try {
            response = client.index(request);
        } catch (IOException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }

        return response;
    }

    // 异步获取操作结果
    public IndexResponse postRquestAsync(String index, String type, String id, String jsonSource) {

        IndexRequest request = new IndexRequest(index, type, id);
        request.source(jsonSource, XContentType.JSON);

        RestHighLevelClient client = ESClientContainer.getRestHighLevelClient();

        client.indexAsync(request, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println(indexResponse);
                RestHighLevelClient client = ESClientContainer.getRestHighLevelClient();
                try {
                    client.close();
                } catch (IOException e) {
                    logger.debug(ExceptionUtils.getStackTrace(e));
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

        return null;
    }

    /**
     *
     */
    public IndexResponse beanToES(String index, String type, String id, Object object) {

        RestHighLevelClient client = ESClientContainer.getRestHighLevelClient();
        String jsonSource =  JSON.toJSONString(object);

        if (index == null) {
            index = PropertiesUtil.getValue("elasticSearch.indexName");
        }
        if (type == null) {
            type = RedisConstant.NOSQL_TABLE_PRE + object.getClass().getName();
        }

        if (id == null) {
            if (object instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity)object;
                id = baseEntity.getId();
            } else if (object instanceof OP_BaseEntity) {
                OP_BaseEntity oP_BaseEntity = (OP_BaseEntity)object;
                id = oP_BaseEntity.getId().toString();
            } else {
                AccessToken accessToken= new AccessToken(null);
                id = accessToken.getKey();
            }

        }

        IndexRequest request = new IndexRequest(index, type, id);
        request.source(jsonSource, XContentType.JSON);

        IndexResponse response = null;
        try {
            response = client.index(request);
        } catch (IOException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }

        return response;
    }

    /**
     *
     */
    public <T> T beanFromES(String index, String type, String id, Class<T> classOfT) {

        RestHighLevelClient client = ESClientContainer.getRestHighLevelClient();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        if (index == null) {
            index = PropertiesUtil.getValue("elasticSearch.indexName");
        }
        if (type == null) {
            type = RedisConstant.NOSQL_TABLE_PRE + classOfT.getName();
        }

        /*
            curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
            {
                "query": {
                    "ids" : {
                        "type" : "my_type",
                        "values" : ["1", "4", "100"]
                    }
                }
            }
            '

            GET /_search
            {
                "query": {
                    "ids" : {
                        "type" : "my_type",
                        "values" : ["1", "4", "100"]
                    }
                }
            }
         */

        String[] types = new String[1];
        String[] ids = new String[1];
        types[0] = type;
        ids[0] = id;

        sourceBuilder.query(new IdsQueryBuilder().types(types).addIds(ids));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        SearchResponse response = null;
        try {
            response = client.search(searchRequest);
            //System.out.println(response);
        } catch (IOException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            return null;
        }

        SearchHit[] results = response.getHits().getHits();
        String sourceAsString = results[0].getSourceAsString();
        if (sourceAsString != null) {
            return JSON.parseObject(sourceAsString, classOfT);
        }

        return null;
    }

    /**
     *
     */
    public DeleteResponse deleteRequest(String index, String type, String id) {
        RestHighLevelClient client = ESClientContainer.getRestHighLevelClient();

        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
        DeleteResponse response = null;
        try {
            response = client.delete(deleteRequest);
        } catch (IOException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }

        return response;
    }

    public static void testMain() {
        String jsonString = "{" + "\"user\":\"kimchy\"," + "\"postDate\":\"2013-01-30\","
                + "\"message\":\"trying out Elasticsearch\"" + "}";

        ElasticSearchServiceImpl service = new ElasticSearchServiceImpl();

    	IndexResponse response = service.postRequest("posts", "doc", "1", jsonString);
        // postRquestAsync("posts", "doc", "1", jsonString);

        System.out.println(ElasticSearchUtils.getStatusIndexResponse(response));
    }
}
