package cn.thinkit.controller;

import java.util.List;

import org.elasticsearch.client.RestHighLevelClient;

import cn.thinkit.entity.bo.PhoneDataInfoBO;
import cn.thinkit.util.es.EsCurd;

public class BatchInsertEsThread  implements Runnable{
	  
	  private volatile   RestHighLevelClient restHighLevelClient;
	  
	  private  String  index;
	  private  String  type;
	  
	  private final  List<PhoneDataInfoBO> phoneDataInfoBOList; 
	  
	public BatchInsertEsThread( RestHighLevelClient restHighLevelClient, String index,
			String type, List<PhoneDataInfoBO> phoneDataInfoBOList) {
		super();
		this.restHighLevelClient = restHighLevelClient;
		this.index = index;
		this.type = type;
		this.phoneDataInfoBOList = phoneDataInfoBOList;
	}

	@Override
	public void run() {
		try {
			EsCurd.bulkInsert(restHighLevelClient, index, type, phoneDataInfoBOList);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
