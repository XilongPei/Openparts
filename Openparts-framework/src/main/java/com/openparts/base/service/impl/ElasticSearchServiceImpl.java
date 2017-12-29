package com.openparts.base.service.impl;

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
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.*;
import org.apache.http.HttpHost;
import org.springframework.stereotype.Service;
import com.openparts.utils.elasticsearch.ESClientContainer;
import com.cnpc.framework.base.service.impl.BaseServiceImpl;
import com.openparts.base.service.ElasticSearchService;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.cnpc.framework.utils.StrUtil;

@Service("elasticSearchService")
public class ElasticSearchServiceImpl extends BaseServiceImpl implements ElasticSearchService {

    @Resource
    private RestClientBuilder restClientBuilder;

    public RestHighLevelClient initialize() {

        // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-low-usage-initialization.html
        // restClientBuilder.setMaxRetryTimeoutMillis(10000);

    	RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        ESClientContainer.putRestHighLevelClient(restHighLevelClient);

        return restHighLevelClient;
    }

    /**
     * String fields: "user message"
     * String keywords: "kimchy elasticsearch"
     */
    public String queryGeneralRequest(String fields, String keywords, String scopeField, String startScope, String endScope,
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
            System.out.println(response);
            client.close();
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }

        return response.toString();
    }

    // 同步获取操作结果
    public IndexResponse postRequest(String index, String type, String id, String jsonSource)
            throws IOException {
        RestHighLevelClient client = ESClientContainer.getRestHighLevelClient();

        IndexRequest request = new IndexRequest(index, type, id);
        request.source(jsonSource, XContentType.JSON);
        IndexResponse response = client.index(request);
        client.close();

        return response;
    }

    // 异步获取操作结果
    public IndexResponse postRquestAsync(String index, String type, String id, String jsonSource) throws IOException {

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
                    // Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                RestHighLevelClient client = ESClientContainer.getRestHighLevelClient();
                try {
                    client.close();
                } catch (IOException ex) {
                    // Auto-generated catch block
                    ex.printStackTrace();
                }
            }
        });

        return null;
    }

    public static void main(String[] args) {
        String jsonString = "{" + "\"user\":\"kimchy\"," + "\"postDate\":\"2013-01-30\","
                + "\"message\":\"trying out Elasticsearch\"" + "}";

        ElasticSearchServiceImpl service = new ElasticSearchServiceImpl();

        try {
        	service.postRequest("posts", "doc", "1", jsonString);
            // postRquestAsync("posts", "doc", "1", jsonString);
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
    }
}
