package cn.thinkit.util.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.ResourceAlreadyExistsException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.thinkit.entity.bo.PhoneDataInfoBO;
import cn.thinkit.util.GLogger;
import cn.thinkit.util.MyJsonFactory;

/**
 * Elasticsearch CURD?????????
 *
 */
public class EsCurd {
	
	private EsCurd() {
		// null
	}

    /**
     * ????????????????????????
     *
     * @param restHighLevelClient
     * @param
     */
    public static boolean indexExists(RestHighLevelClient restHighLevelClient, String index) {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(index);

        try {
            boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            if (exists) {
            	GLogger.info("??????{}??????", index);
                return true;
            } else {
            	GLogger.info("??????{}?????????", index);
                return false;
            }
        } catch (ElasticsearchException | IOException e) {
        	GLogger.error("", e);
        }

        return false;
    }

    /**
     * ????????????
     *
     * @param restHighLevelClient
     * @param index
     * @param shards
     * @param replicas
     */
    public static void createIndex(RestHighLevelClient restHighLevelClient, String index, Integer shards, Integer replicas) {
        // ????????????
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        // ????????????????????? from+size ???????????????????????? 10000
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas)
                .put("index.max_result_window", 2000000000));

        // ????????????
        try {
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

            // ?????????CreateIndexResponse?????????????????????????????????????????????????????????
            // ??????????????????????????????????????????
            boolean acknowledged = createIndexResponse.isAcknowledged();

            if (acknowledged) {
            	GLogger.info("??????{}????????????", index);
            } else {
            	GLogger.info("??????{}????????????", index);
            }
        } catch (ResourceAlreadyExistsException | IOException e) {
        	GLogger.error("??????{}???????????????????????????", e);
        }
    }

    /**
     * ??????????????????????????????Mapping
     *
     * @param restHighLevelClient
     * @param index
     * @param type
     */
    public static void formatMapping(RestHighLevelClient restHighLevelClient, String index, String type) {
        PutMappingRequest putMappingRequest = new PutMappingRequest(index);
        putMappingRequest.type(type);

        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
            xContentBuilder.startObject()
                    .startObject("properties")
                    .startObject("serialNumber").field("type", "keyword").endObject()
                    .startObject("insertTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("callStartTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("callEndTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("callNumber").field("type", "keyword").endObject()
                    .startObject("calledNumber").field("type", "keyword").endObject()
                    .startObject("callDirection").field("type", "long").endObject()
                    .startObject("isEachRecord").field("type", "long").endObject()
                    .startObject("lineType").field("type", "long").endObject()
                    .startObject("channelNumber").field("type", "long").endObject()
                    .startObject("signalType").field("type", "long").endObject()
                    .startObject("holdDuration").field("type", "double").endObject()
                    .startObject("isImportance").field("type", "long").endObject()
                    .startObject("handleState").field("type", "long").endObject()
                    .startObject("reserve1").field("type", "keyword").endObject()
                    .startObject("reserve2").field("type", "keyword").endObject()
                    .startObject("reserve3").field("type", "keyword").endObject()
                    .startObject("reserve4").field("type", "keyword").endObject()
                    .startObject("reserve5").field("type", "keyword").endObject()
                    .startObject("reserve6").field("type", "long").endObject()
                    .startObject("reserve7").field("type", "long").endObject()
                    .startObject("reserve8").field("type", "double").endObject()
                    .startObject("reserve9").field("type", "double").endObject()
                    .startObject("reserve10").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("reserve11").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("audioList").field("type", "nested")
                    .startObject("properties")
                    .startObject("audioNumber").field("type", "keyword").endObject()
                    .startObject("audioPath").field("type", "keyword").endObject()
                    .startObject("audioLength").field("type", "double").endObject()
                    .startObject("reserve1").field("type", "keyword").endObject()
                    .startObject("reserve2").field("type", "keyword").endObject()
                    .startObject("reserve3").field("type", "keyword").endObject()
                    .startObject("reserve4").field("type", "long").endObject()
                    .startObject("reserve5").field("type", "double").endObject()
                    .startObject("reserve6").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("engineList").field("type", "nested")
                    .startObject("properties")
                    .startObject("engineName").field("type", "keyword").endObject()
                    .startObject("engineStatus").field("type", "long").endObject()
                    .startObject("confirmTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("confirmEndTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("reserve1").field("type", "keyword").endObject()
                    .startObject("reserve2").field("type", "keyword").endObject()
                    .startObject("reserve3").field("type", "long").endObject()
                    .startObject("reserve4").field("type", "double").endObject()
                    .startObject("reserve5").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("engineResult").field("type", "nested")
                    .startObject("properties")
                    .startObject("serialNumber").field("type", "long").endObject()
                    .startObject("result").field("type", "keyword").endObject()
                    .startObject("remark").field("type", "keyword").endObject()
                    .startObject("startTime").field("type", "double").endObject()
                    .startObject("duration").field("type", "double").endObject()
                    .startObject("confidence").field("type", "double").endObject()
                    .startObject("judgeState").field("type", "long").endObject()
                    .startObject("reserve1").field("type", "keyword").endObject()
                    .startObject("reserve2").field("type", "long").endObject()
                    .startObject("reserve3").field("type", "double").endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();

            putMappingRequest.source(xContentBuilder);

            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
            if (acknowledgedResponse.isAcknowledged()) {
            	GLogger.info("{}.{}???????????????", index, type);
            } else {
            	GLogger.info("{}.{}???????????????", index, type);
            }
        } catch (ElasticsearchException | IOException e) {
        	GLogger.error("{}.{}??????????????????????????????", e);
        }
    }

    /**
     * ??????id????????????
     */
    public static String get(RestHighLevelClient restHighLevelClient, String index, String type, String id) {
        GetRequest getRequest = new GetRequest(index, type, id);
        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

            if (getResponse.isExists()) {
                GLogger.info("???{}??????????????????", id);
                return getResponse.getSourceAsString();
            } else {
                boolean selected = false;
                for (int i = 0; i < 3; i++) {
                    getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
                    if (getResponse.isExists()) {
                        selected = true;
                        break;
                    } else {
                        Thread.sleep(1000);
                    }
                }
                if (selected) {
                    GLogger.info("???{}??????????????????", id);
                    return getResponse.getSourceAsString();
                } else {
                    GLogger.info("????????????{}???", id);
                    return null;
                }
            }
        } catch (ElasticsearchException | IOException | InterruptedException e) {
            GLogger.error("???{}??????????????????????????????", e);

            return null;
        }
    }
    
    /**
     * ??????index???type???id ??????ES??????
     */
    public static boolean  updateDoc(RestHighLevelClient restHighLevelClient, String index, String type, String id, String json) {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
        updateRequest.doc(json, XContentType.JSON);

        UpdateResponse updateResponse;
        try {
        	 updateRequest.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
        	 
            updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                GLogger.info("ES???????????????version :{} " , updateResponse.getVersion());
            } else {
                GLogger.info("ES???????????????version {} " , updateResponse.getVersion());
            }
        } catch (ElasticsearchStatusException e) {
            if (e.status().getStatus() == 409) {
                GLogger.info("?????????????????????????????????????????????????????????");
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(200);
                        updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
                        GLogger.info("???{}????????????????????????", i);
                        if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                            GLogger.info("???????????????");
                            return true;
                        } else {
                            GLogger.info("????????????");
                        }
                    } catch (InterruptedException | IOException e1) {
                        GLogger.error("ES???????????????????????????");
                        GLogger.error(e1.getMessage());
                    }
                }
                
                return false;
            } else {
                GLogger.info("?????????ES???????????????????????????{};???????????????{}", e.status().getStatus(), e.status().name());
                return false;
            }

        } catch (IOException e) {
            GLogger.error("ES???????????????????????????");
            GLogger.error("", e);
            
            return false;
        }
        
        return true;
    }
    
    /**
     * ????????????index????????????????????????mapping
     *
     *  ????????????
     *  2020/5/12 15:01
     */
    public static  boolean bulkInsert(RestHighLevelClient restHighLevelClient,String index, String type, List<PhoneDataInfoBO> phoneDataInfoBOList){
        BulkRequest bulkRequest = new BulkRequest();
        
        ObjectMapper mapper = new ObjectMapper(MyJsonFactory.factory);
        
        if(phoneDataInfoBOList!=null) {
	        phoneDataInfoBOList.forEach(phoneDataInfoBO-> {
	        	String serialNumber = phoneDataInfoBO.getSerialNumber();
	        	
	        	try {
					String json = mapper.writeValueAsString(phoneDataInfoBO);
					
					GLogger.info("json:{}",json);
					 /** bulkRequest.add( new IndexRequest(index, type, serialNumber).source(json, XContentType.JSON)); */
					
					IndexRequest indexRequest =   new IndexRequest(index, type, serialNumber).source(json, XContentType.JSON);
					 
					// upsert--id?????????????????????
					UpdateRequest updateRequest = new UpdateRequest(index,type,serialNumber).doc(json, XContentType.JSON).upsert(indexRequest);
					
					 bulkRequest.add(updateRequest);
				} catch (JsonProcessingException e) {
					GLogger.error("json????????????{}",e);
				} catch (Exception e) {
					GLogger.error("ES????????????????????????{}",e);
				}
	        	
	        	
	        });
        
        }
        
      /**  bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE); */
        try {
        	
        	  // ?????????????????????????????????????????????????????????????????????
            ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(BulkResponse bulkItemResponses) {
                    if (bulkItemResponses.hasFailures()) {
                        GLogger.error("?????????????????????Bulk????????????????????????{}");
                    }else {
                    	 GLogger.info("??????????????????, ????????????:{}", bulkItemResponses.getItems().length);
                    }
                    
                }
                @Override
                public void onFailure(Exception e) {
                    GLogger.error("", e);
                    GLogger.error("?????????????????????Bulk??????????????????");
                }
            };
            
            if(!bulkRequest.requests().isEmpty()) {
            	restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, listener);
            }
            
          /**  BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT); */
              
              return true;
            
			/**
			 * if(!bulkResponse.hasFailures()){ GLogger.debug("bulk insert successfully.");
			 * return true; }else { return false; }
			 */
        } catch (Exception e) {
        	GLogger.error("es????????????????????????",e);
        	 return false;
        }
        
    }
    
    /**
     * ????????????index????????????????????????mapping
     *
     *  ????????????
     *  2020/5/12 15:01
     */
    public static  List<PhoneDataInfoBO> bulkInsertBulkResponse(RestHighLevelClient restHighLevelClient,String index, String type, List<PhoneDataInfoBO> phoneDataInfoBOList , int minLoopTime){
        BulkRequest bulkRequest = new BulkRequest();
        
        ObjectMapper mapper = new ObjectMapper(MyJsonFactory.factory);
        
        if(phoneDataInfoBOList!=null) {
            phoneDataInfoBOList.forEach(phoneDataInfoBO-> {
                String serialNumber = phoneDataInfoBO.getSerialNumber();
                
                try {
                    String json = mapper.writeValueAsString(phoneDataInfoBO);
                    
                    GLogger.info("json:{}",json);
                     /** bulkRequest.add( new IndexRequest(index, type, serialNumber).source(json, XContentType.JSON)); */
                    
                    IndexRequest indexRequest =   new IndexRequest(index, type, serialNumber).source(json, XContentType.JSON);
                     
                    // upsert--id?????????????????????
                    UpdateRequest updateRequest = new UpdateRequest(index,type,serialNumber).doc(json, XContentType.JSON).upsert(indexRequest);
                    
                     bulkRequest.add(updateRequest);
                } catch (JsonProcessingException e) {
                    GLogger.error("json????????????{}",e);
                } catch (Exception e) {
                    GLogger.error("ES????????????????????????{}",e);
                }
                
                
            });
        
        }
        
      /**  bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE); */
        try {
            
              // ?????????????????????????????????????????????????????????????????????
            ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(final BulkResponse bulkItemResponses) {
                    if (bulkItemResponses.hasFailures()) {
                        GLogger.error("?????????????????????Bulk????????????????????????,??????????????? "+minLoopTime);
                    }else {
                         GLogger.info("??????????????????, ????????????:{}, ???????????????{} ", bulkItemResponses.getItems().length, minLoopTime);
                    }
                }
                @Override
                public void onFailure(Exception e) {
                    GLogger.error("?????????????????????Bulk????????????????????????????????? "+minLoopTime, e);
                }
            };
            
            if(!bulkRequest.requests().isEmpty()) {
                restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, listener);
            }
            
            return phoneDataInfoBOList;
            
        } catch (Exception e) {
            GLogger.error("es????????????????????????",e);
             return new ArrayList<>();
        }
        
    }
    
    /**
     * ????????????index????????????????????????mapping
     *
     *  ????????????
     *  2020/5/12 15:01
     */
    public static  List<PhoneDataInfoBO> bulkInsertBulkResponse2(RestHighLevelClient restHighLevelClient,String index, String type, List<PhoneDataInfoBO> phoneDataInfoBOList , int minLoopTime){
        BulkRequest bulkRequest = new BulkRequest();
        
        ObjectMapper mapper = new ObjectMapper(MyJsonFactory.factory);
        
        BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer =
                (request, bulkListener) -> restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);

                BulkProcessor  bulkProcessor =   BulkProcessor.builder(bulkConsumer, new BulkProcessor.Listener() {

            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
            	GLogger.info("??????????????????:{}",String.valueOf(minLoopTime));
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
            	GLogger.info("??????????????????:{}",String.valueOf(minLoopTime));
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
            	GLogger.error("??????????????????:"+minLoopTime);

            }
        }).setBulkActions(100).setFlushInterval(TimeValue.timeValueSeconds(10)).build();
                
        if(phoneDataInfoBOList!=null) {
            phoneDataInfoBOList.forEach(phoneDataInfoBO-> {
                String serialNumber = phoneDataInfoBO.getSerialNumber();
                
                try {
                    String json = mapper.writeValueAsString(phoneDataInfoBO);
                    
                    GLogger.info("json:{}",json);
                     /** bulkRequest.add( new IndexRequest(index, type, serialNumber).source(json, XContentType.JSON)); */
                    
                    IndexRequest indexRequest =   new IndexRequest(index, type, serialNumber).source(json, XContentType.JSON);
                     
                    // upsert--id?????????????????????
                    UpdateRequest updateRequest = new UpdateRequest(index,type,serialNumber).doc(json, XContentType.JSON).upsert(indexRequest);
                    
                     bulkRequest.add(updateRequest);
                     
                     bulkProcessor.add(updateRequest);
                } catch (JsonProcessingException e) {
                    GLogger.error("json????????????{}",e);
                } catch (Exception e) {
                    GLogger.error("ES????????????????????????{}",e);
                }
            });
        }
        
        return phoneDataInfoBOList;
    }
    
    /**
     * ?????????????????????????????????????????????
     */
    public static Map<String, Object> queryByFilter(RestHighLevelClient restHighLevelClient, String index, QueryBuilder queryBuilder) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);

        try {
        	
        	GLogger.info("ES??????DSL??????:{} ", searchSourceBuilder);
        	
        	
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits searchHits = searchResponse.getHits();

            List<String> docList = new ArrayList<>();

            for (SearchHit hit : searchHits) {
                docList.add(hit.getSourceAsString());
            }

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("content", docList);
            resultMap.put("total", searchResponse.getHits().getTotalHits());

            return resultMap;
        } catch (ElasticsearchException | IOException e) {
            GLogger.error("??????????????????{}???????????????", e);
            return null;
        }
    }
    
    /**
     * ??????id????????????
     */
    public static boolean deleteDoc(RestHighLevelClient restHighLevelClient, String index, String type, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);

        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);

            if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
                GLogger.info("ES???????????????version = " + deleteResponse.getVersion());
                return true;
            } else {
                GLogger.info("ES???????????????version = " + deleteResponse.getVersion());
                return false;
            }
        } catch (ElasticsearchException | IOException e) {
            GLogger.error("ES??????????????????", e);
            return false;
        }
    }

}
