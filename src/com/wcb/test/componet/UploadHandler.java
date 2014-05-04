package com.wcb.test.componet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wcb.test.service.UploadDataService;
import com.wcb.test.util.SpringContextUtil;

/**
 * @desc 
 * @author knight Wang
 * @Date 2014-5-2
 */

@Component
@Scope("singleton")
public class UploadHandler {
	
	private static final Map<String,FutureWapper> CONTEXT = new HashMap<String,FutureWapper>(); 
	
	private static final ExecutorService exec =  Executors.newSingleThreadExecutor();
	
	 public synchronized boolean handler(InputStream in, String sessionID){
		 try{
			
			 Future<String> taskFuture = exec.submit(new UploadTask(in,sessionID));
			 FutureWapper taskFutureWapper = new FutureWapper();
			 
			 taskFutureWapper.setFuture(taskFuture);
			 //clear the CONTEXT,Prevent out of memory 
			 if(CONTEXT.size()>60){
				 CONTEXT.values();
				 
				FutureWapper lfw = null;
				for(FutureWapper fw:CONTEXT.values()){
					if(lfw==null){
						lfw = fw;
						continue;
					}
					
					if(fw.getTimemsec()<lfw.getTimemsec()){
						lfw = fw;
					}
				}
				for(String key: CONTEXT.keySet()){
					if(lfw.equals(CONTEXT.get(key))){
						CONTEXT.remove(key);
					}
				}
			 }
			 CONTEXT.put(sessionID, taskFutureWapper);
		 }catch(Exception e){
			 e.printStackTrace();
			 return false;
		 }
		 return true;
	 }
	 
	 /**
	  * 获取上传状态
	  * @param sessionId
	  * @return
	  */
	 public synchronized String getUploadStatus(String sessionId){
		 
		 if(!CONTEXT.containsKey(sessionId)){
			 return "failed";
		 }
		 
		 FutureWapper fw = CONTEXT.get(sessionId);
		 if(fw==null){
			 return "failed";
		 }
		 Future<String> f = fw.getFuture();
		try {
			return f.get(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			f.cancel(true);
			
			CONTEXT.remove(sessionId);
			return "failed";
		} catch (ExecutionException e) {
			e.printStackTrace();
			f.cancel(true);
			CONTEXT.remove(sessionId);
			
			return "failed";
		} catch (TimeoutException e) {
			e.printStackTrace();
			return "lodding";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	 }
	
	public static class UploadTask implements Callable<String>{
		
		
		UploadDataService uploadDataService = (UploadDataService)SpringContextUtil.getBean("uploadDataService");
		
		public UploadTask(InputStream in,String sessionId){
			this.in = in;
			this.sessionId = sessionId;
		}
		
		private InputStream in;
		
		private String sessionId;
		
		public InputStream getIn() {
			return in;
		}

		public void setIn(InputStream in) {
			this.in = in;
		}

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public String call() throws Exception {
				
			 
			if(uploadDataService==null){
				return "error";
			}
			boolean flag = uploadDataService.importDataFromExcel(in);
			if(flag){
				return "success";
			}
			return "fatal";
		}
		 
	 }
	
	
	public static class FutureWapper implements Comparable<FutureWapper>{
		
		private Future<String> future;
		
		private long timemsec = System.currentTimeMillis();

		
		public Future<String> getFuture() {
			return future;
		}

		public void setFuture(Future<String> future) {
			this.future = future;
		}

		public long getTimemsec() {
			return timemsec;
		}

		public void setTimemsec(long timemsec) {
			this.timemsec = timemsec;
		}

		public int compareTo(FutureWapper o) {
			if(timemsec>o.getTimemsec()){
				return -1;
			}else {
				return 1;
			}
		}
		
		@Override
		public boolean equals(Object o){
			if(o == null){
				return false;
			}
			if(!(o instanceof FutureWapper)){
				return false;
			}
			FutureWapper fw = (FutureWapper)o;
			if(fw.getTimemsec()==this.getTimemsec()){
				return true;
			}
			return false;
		}
		
	}
}

