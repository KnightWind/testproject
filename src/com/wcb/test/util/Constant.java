package com.wcb.test.util;
/**
 *@desc 
 *@date 2012-9-29
 *@author wang-chaobo
 *
 */
public interface Constant {
	
	int DEF_PAGESIZE = 20;
	int DEF_PAGE = 1;
	
	//按年龄查询的条件
	String LESSTHEN20 = "lt20";
	String BT20AND40 = "20and40";
	String BT40AND60 = "40and60";
	String GREATERTHEN60 = "gt60";
	
	//按血糖高低来取
	String TOP20HIGH = "20H";
	String TOP20LOW = "20L";
	
	int YEAR20 = 20*365;
	int YEAR40 = 40*365;
	int YEAR60 = 60*365;
	
	int LOW = 10;
	int HIGH = 20;
	
	String USER = "user";
	
	String SUGERHIGHVALUE = "highvalue";
	String SUGERLOWVALUE = "lowvalue";
	
	/**
	 * 以下是血糖高低区间  应放置于配置文件或数据库中 暂存于此
	 */
	float HIGHSUGARVAL = 20.00f;
	float LOWSUGARVAL = 10.00f;
	float DANGERLOWSUGARVAL = 5.00f;
	float DANGERHIGHSUGARVAL = 25.00f;
	
	String TABLE_ZRBR = "(select t1.CSRQ, t1.BQDM, t1.SG, t1.TZ,  t1.XM, t1.ZYH, t1.CWH, t1.XB ,t1.ryyq "
						+" from VI_ZYBR t1,(select max(t.ryyq) as ryyq,t.zyh from VI_ZYBR t group by t.zyh) t2 "  
						+" where t1.ryyq = t2.ryyq and t1.zyh = t2.zyh) p";
}

