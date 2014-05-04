package com.wcb.test.componet;

import com.wcb.dao.BaseDao;

/**
 * @desc 
 * @author knight Wang
 * @Date 2014-5-3
 */
public class MyFnTag {
	
	private static BaseDao simpleDao;

	public static BaseDao getSimpleDao() {
		return simpleDao;
	}

	public static void setSimpleDao(BaseDao simpleDao) {
		MyFnTag.simpleDao = simpleDao;
	}
	
	
	/**
	 * 根据产品ID获取该产品的客户数量
	 * @param pid
	 * @return 
	 */
	public static java.lang.Integer getCustomerNum(java.lang.Integer pid){
		String sql = "SELECT COUNT(DISTINCT USER) from t_sale_record WHERE product = ?";
		return simpleDao.queryForInt(sql, new Object[]{pid});
	}
	
	/**
	 * 根据产品ID获取该产品销售额
	 * @param pid
	 * @return 
	 */
	public static Double getProductSumMonery(java.lang.Integer pid){
		String sql = " SELECT SUM(t.price*t.num)from t_sale_record t WHERE t.product = ?";
		return (Double)simpleDao.queryForObject(sql, new Object[]{pid});
	}
	
	/**
	 * 根据产品ID获取该产品利润
	 * @param pid
	 * @return
	 */
	public static Double getProductProfit(java.lang.Integer pid){
		String sql = " SELECT SUM((t.price-p.cost)*t.num)from t_sale_record t,t_product p WHERE t.product = p.id and p.id = ?";
		return (Double)simpleDao.queryForObject(sql, new Object[]{pid});
	}
	
	/**
	 * 根据用户ID获取用户信息
	 * @param uid
	 * @return
	 */
//	public static User getUserById(int uid){
//		 
//		 return simpleDao.getById(User.class, uid);
//	}
	
	public static String test(java.lang.Integer uid){
		
		return "hahahahha!"+uid;
	}
	
	
}

