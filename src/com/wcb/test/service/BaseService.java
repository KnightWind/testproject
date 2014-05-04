package com.wcb.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wcb.dao.BaseDao;
import com.wcb.test.bean.PageBean;

/**
 * @desc 
 * @author knight Wang
 * @Date 2014-5-2
 */
@Service
public class BaseService {
	
	@Autowired
	protected BaseDao simpleDao;
	
	/**
	 * 一种可复用性直接查询分页对象的查询方法
	 * @param c 查询对象的类
	 * @param sql 查询sql 如select * from  t_tableName 
	 * @param pageNo 当前页
	 * @param pageSize  每页显示条
	 * @param objs  查询参数
	 * @return PageBean pageModel类的改装 兼容pageModel
	 */
	@SuppressWarnings("unchecked")
	public <T>PageBean<T> getPageBeans(String sql,int pageNo,int pageSize,Object...objs){
		PageBean<T> page = new PageBean<T>();
		if(pageNo<1){
			pageNo = 1;
		}
		
		if(pageSize<1){
			pageSize = 10;
		}
		try {
			sql = sql.toLowerCase();
			String countsql = " select count(*) " + sql.substring(sql.indexOf("from"));
			int count = simpleDao.queryForInt(countsql, objs);
			if(pageNo>1 && count<=(pageNo-1)*pageSize){
				pageNo = pageNo - 1;
			}
			String limitsql = sql+" limit "+(pageNo-1)*pageSize +","+pageSize;
			List<T> dataList = (List<T>) simpleDao.queryForList(limitsql,objs);
			
			page.setPageNo(pageNo+"");
			page.setPageSize(pageSize);
			page.setRowsCount(count);
			page.setDatas(dataList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return page;
	}
	
	public <T>PageBean<T> getPageBeans(String sql,Object...objs){
		return getPageBeans(sql, 0, 0,objs);
	}
	
	public <T>PageBean<T> getPageBeans(String sql,int pageNo,Object...objs){
		return getPageBeans(sql, pageNo, 0,objs);
	}
	
}

