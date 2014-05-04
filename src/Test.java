import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * @desc 
 * @author knight Wang
 * @Date 2014-5-2
 */
public class Test {
	
	
	

	       //声明一个类，可以被调用，类似于线程，但它可以拥有返回值

	       static class MyCall implements Callable<String>{

	           private int seq;

	           public MyCall(int seq){

	              this.seq=seq;

	           }

	           //抛出异常并可以拥有返回值

	           public String call() throws Exception {

	              System.err.println("执行"+seq+","+Thread.currentThread().getName());

	              if(seq == 2){
	            	  throw new RuntimeException("done!");
	              }
	              Thread.sleep(3000);

	              System.err.println("Weak up "+seq);
	              return "完成"+seq;//这是返回值

	           }

	       }
	       
	       
	       
	       static class MyRun implements Runnable{
	    	   
	    	   private int seq;
	    	   
	    	   public MyRun(int seq){
	    		   
	    		   this.seq=seq;
	    		   
	    	   }
	    	   

				public void run() {
					 
					 System.err.println("执行"+seq+","+Thread.currentThread().getName());
		    		   
		    		   try {
		    			   
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						 
					}
		    		   
		    		 System.err.println("Weak up "+seq);
		    		   
		    		    
				}
	    	   
	       }
	       
	       
	       public static void main(String ...args) throws Exception{
	    	   
//		       ExecutorService es = Executors.newCachedThreadPool();//创建线程池对象
//		       List<Future<?>> result =new ArrayList<Future<?>>();//放结果用的集合
//		       for(int i=0;i<3;i++){
//		           Future<?> f=es.submit(new MyRun(i));//线程执行完成以后可以通过引用获取返回值
//		           result.add(f);
//	
//		       }
//	
//		       for(Future<?> f:result){
//		         Object o =  f.get();
//		         o.toString();
//		       }
//		       System.err.println("完成....");

		       
		       ExecutorService es = Executors.newCachedThreadPool();//创建线程池对象
		       List<Future<String>> result =new ArrayList<Future<String>>();//放结果用的集合
		       for(int i=0;i<3;i++){
		           Future<String> f=es.submit(new MyCall(i));//线程执行完成以后可以通过引用获取返回值
		           result.add(f);
		       }
//		       for(Future<String> f:result){
		       try{
		    	  
		    	   System.err.println("返回值："+result.get(1).get(1000,TimeUnit.MILLISECONDS));//输出返回的值
		    	   
		       }catch(Exception e){
		    	   e.printStackTrace();
		       }
//		       }
		       System.err.println("完成....");
	       }
	    
}

