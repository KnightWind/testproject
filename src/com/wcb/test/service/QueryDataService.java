package com.wcb.test.service;

import org.springframework.stereotype.Service;

import com.wcb.test.bean.PageBean;
import com.wcb.test.bean.Product;
import com.wcb.test.bean.User;

/**
 * @desc 数据查询
 * @author knight Wang
 * @Date 2014-5-2
 */

@Service
public class QueryDataService extends BaseService {
	
//	private Log log = LogFactory.getLog(QueryDataService.class);
	
	/**
	 * 返回用户的分页对象
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean<User> getUserPage(int pageNo,int pageSize){
		
		String sql1 = " SELECT SUM(t.price*t.num) from t_sale_record t WHERE t.product = 1";
		double d =  (Double)simpleDao.queryForObject(sql1);
		System.out.println(d);
		
		
		String sql = "select * from t_user";
		return getPageBeans(sql, pageNo, pageSize);
	}
	
	
	/**
	 * 返回产品的分页对象
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean<Product> getProductPage(int pageNo,int pageSize){
		
		String sql = "select * from t_product";
		return getPageBeans(sql, pageNo, pageSize);
	}
	
}

