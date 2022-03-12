package cn.thinkit.controller;

import java.util.List;
import java.util.concurrent.Callable;

import org.elasticsearch.client.RestHighLevelClient;

import cn.thinkit.entity.bo.PhoneDataInfoBO;
import cn.thinkit.util.es.EsCurd;

public class BatchInsertEsCallable  implements Callable<List<PhoneDataInfoBO>> {
	  
	  private volatile   RestHighLevelClient restHighLevelClient;
	  
	  private  String  index;
	  private  String  type;
	  
	  private final  List<PhoneDataInfoBO> phoneDataInfoBOList; 
	  
	  private  int delayForNextThreadInMillis;
	  
	  
	public BatchInsertEsCallable( RestHighLevelClient restHighLevelClient, String index,
			String type, List<PhoneDataInfoBO> phoneDataInfoBOList, int delayForNextThreadInMillis) {
		super();
		this.restHighLevelClient = restHighLevelClient;
		this.index = index;
		this.type = type;
		this.phoneDataInfoBOList = phoneDataInfoBOList;
		 this.delayForNextThreadInMillis = delayForNextThreadInMillis;
	}

    @Override
	public List<PhoneDataInfoBO>  call() throws Exception {
		try {
		    if(delayForNextThreadInMillis>0) {
	            try {
	                System.out.println("run sleep " + delayForNextThreadInMillis);
	                Thread.sleep(delayForNextThreadInMillis);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
		    
			return EsCurd.bulkInsertBulkResponse(restHighLevelClient, index, type, phoneDataInfoBOList);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
