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
	public static int getCostomerNum(int pid){
		String sql = "SELECT COUNT(DISTINCT USER) from t_sale_record WHERE product = ?";
		return simpleDao.queryForInt(sql, new Object[]{pid});
	}
	
	/**
	 * 根据产品ID获取该产品的客户数量
	 * @param pid
	 * @return
	 */
	public static double getProductSumMonery(int pid){
		String sql = " SELECT SUM(t.price*t.num)from t_sale_record t WHERE t.product = ?";
		return (Double)simpleDao.queryForObject(sql, new Object[]{pid});
	}
}

