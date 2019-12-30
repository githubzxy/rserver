package com.enovell.yunwei.km_micor_service.util;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class GetMongoClientParam {
	
    private String mongoHost ="172.23.1.4";
   
    private int mongoPort=27000;

    private String mongoDatabase="kmad";
    
    private String mongoUserName="rinms2";
    
    private String mongoPassWord="rinms2";
    
    public MongoClient createMongoClient(){
    	  ServerAddress serverAddress = new ServerAddress(mongoHost,mongoPort);
          List<ServerAddress> addrs = new ArrayList<ServerAddress>();
          addrs.add(serverAddress);
          
          MongoCredential credential = MongoCredential.createScramSha1Credential(mongoUserName,mongoDatabase,mongoPassWord.toCharArray());
          List<MongoCredential> credentials = new ArrayList<MongoCredential>();
          credentials.add(credential);
          
    	  MongoClient mc = new MongoClient(addrs,credentials);
    	  return mc;
    			
    }
   
}
