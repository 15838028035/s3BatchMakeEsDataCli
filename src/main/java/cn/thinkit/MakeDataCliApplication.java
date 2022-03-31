package cn.thinkit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import org.elasticsearch.client.RestHighLevelClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.thinkit.config.ConfigProp;
import cn.thinkit.controller.BatchInsertEsCallable;
import cn.thinkit.entity.bo.PhoneDataInfoBO;
import cn.thinkit.util.BeanCopierUtil;
import cn.thinkit.util.GLogger;
import cn.thinkit.util.GenerateSequenceUtil;
import cn.thinkit.util.MyJsonFactory;
import cn.thinkit.util.es.ClientFactory;
import cn.thinkit.util.es.EsCurd;
import cn.thinkit.util.file.FileUtil;
import cn.thinkit.util.properties.ApplicationProperties;
import cn.thinkit.util.thread.ThreadPoolMonitor;

public class MakeDataCliApplication {

	private static  ExecutorService executorService = ThreadPoolMonitor.threadPoolMonitor(Runtime.getRuntime().availableProcessors(), "makEsData");
	
	private static  ConfigProp configProp;
	
	private static  List<PhoneDataInfoBO> batchList = new ArrayList<>();
	    
     private static final int BATCH_SEND_COUNT = 20;
	
    public static void main(String[] args) {
    	
    	GLogger.info("===============================");
    	GLogger.info("====ES 多线程压测客户端 V1.0.1===");
    	GLogger.info("===============================");
    	
    	ApplicationProperties.reload();
    	MakeDataCliApplication.configProp = (ConfigProp) ApplicationProperties.getProperties().get("configProp");
    	
    	batchMakeData5();
		
    }
    
    /**
     *  批量处理， 边处理，边清理list内存
     *  CPU 占用率 瞬间过高， 不会出现长时间cpu占用率超过100%的情况
     *  
     * @return
     */
    public static  String batchMakeData5() {
        try {
            
            RestHighLevelClient restHighLevelClient = ClientFactory.getRestHighLevelClient();
            
            String idx =  configProp.getEsIndex();
            String type =  configProp.getEsType();
            
            // 若索引不存在则创建索引并进行mapping
            if (!EsCurd.indexExists(restHighLevelClient,idx)) {
                EsCurd.createIndex(restHighLevelClient,idx,40,1);
                EsCurd.formatMapping(restHighLevelClient, idx, type);
            }
            
            String phoneStr = FileUtil.readFile("phoneinfojson.txt");
            
            ObjectMapper mapper = new ObjectMapper(MyJsonFactory.factory);
            PhoneDataInfoBO phoneDataInfoBO = mapper.readValue(phoneStr, PhoneDataInfoBO.class);
            
           int minLoopTime = 0;
        
           int maxLoopTime = 100;
            
            long startTime = System.currentTimeMillis();
            
            GLogger.info("开始进行批量生成压测数据");
           
           while( minLoopTime< maxLoopTime) {
        	   
               batchMakeData5Loop(phoneDataInfoBO,restHighLevelClient,minLoopTime);
               
              /** 
    		  * 监控线程执行情况
    		  */
        	    monitor((ThreadPoolExecutor) executorService);
        	    minLoopTime ++;
           }
        
           GLogger.info("结束进行批量生成{}条测试数据",100000);
           
           long endTime = System.currentTimeMillis();
           
           GLogger.info("批量ES数据异步入库花费时间:开始时间:{}", startTime);
           GLogger.info("批量ES数据异步入库花费时间:结束时间:{}", endTime);
           GLogger.info("批量ES数据异步入库花费时间:{} 毫秒, 约{}秒", endTime-startTime , (endTime-startTime)/1000);
           
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return "ES数据添加成功";
    }
    
    /**
     * 监控线程执行情况
     * 一直阻塞到线程任务执行完毕
     */
    public static void monitor(ThreadPoolExecutor executor) {
        
        while (!isTerminated(executor)) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 线程池任务是否执行完毕
     * @Version: 1.0
     */
    public static boolean isTerminated(ThreadPoolExecutor executor) {
        return executor.getQueue().isEmpty() && executor.getActiveCount() == 0;
    }
    
    public static  void batchMakeData5Loop(PhoneDataInfoBO phoneDataInfoBO, RestHighLevelClient restHighLevelClient,int minLoopTime) {
        GLogger.info("开始进行第{}次批量生成压测数据",minLoopTime);
        
        PhoneDataInfoBO phoneDataInfoBO2My = new PhoneDataInfoBO();
        for(int i=0; i<10000; i++) {
            String uuid = GenerateSequenceUtil.generateSequenceNo()+String.valueOf(i);
           
            phoneDataInfoBO2My = new PhoneDataInfoBO();
            
            try {
				
				BeanCopierUtil.copy(phoneDataInfoBO, phoneDataInfoBO2My);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
            
            phoneDataInfoBO2My.setSerialNumber(uuid);
            phoneDataInfoBO2My.getAudioList().get(0).setAudioNumber(uuid+"_01");
            
            putDataAndSend(restHighLevelClient, phoneDataInfoBO2My, minLoopTime);
        }
    }
    
    public  static  void putDataAndSend(RestHighLevelClient restHighLevelClient,PhoneDataInfoBO phoneDataInfoBO, int minLoopTime) {
        
        synchronized (batchList) {
            
            batchList.add(phoneDataInfoBO);
            
            
            List<PhoneDataInfoBO> batchList2 = new ArrayList<>();
            
            int currentSize = batchList.size();
            
            boolean isSendNow = false;
            
            if(currentSize>=BATCH_SEND_COUNT) {
                isSendNow = true;
            }
            
            if(isSendNow) {
                
                batchList.forEach(str2-> 
                    batchList2.add(str2)
                );
                
                doBatch(restHighLevelClient,batchList2, minLoopTime);
                batchList.clear();
            }
            
        }
    }
    
    private static  void doBatch(RestHighLevelClient restHighLevelClient,List<PhoneDataInfoBO> list, int minLoopTime) {
        executorService.submit(new BatchInsertEsCallable(restHighLevelClient, configProp.getEsIndex(), configProp.getEsType(),list,0, minLoopTime));
    }
}
