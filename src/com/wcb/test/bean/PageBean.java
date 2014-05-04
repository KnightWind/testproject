package com.wcb.test.bean;


import java.util.List;


/**
 * @desc pageModel变体  兼容pageModel 
 * @author martin
 * @date 2013-2-19
 */
public class PageBean<T> {
	private Integer beginRow=0;//从第几行开始显示
	private Integer rowsCount=0;//总记录数
	private Integer pageSize=10;//每页显示条数
	private String pageNo = "1";//当前页码数
	private Integer pageCount=1;//总页数
	private List<T> datas;
	public Integer getRowsCount() {
		return rowsCount;
	}
	public void setRowsCount(Integer rowsCount) {
		this.rowsCount = rowsCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getBeginRow() {
		return beginRow;
	}
	public void setBeginRow(Integer beginRow) {
		this.beginRow = beginRow;
	}
	
	public String getPageNo() {
		if((pageNo==null || "".equals(pageNo.trim())) && (beginRow == null || beginRow.intValue() <0)){
			this.pageNo="1";
			return this.pageNo;
		}
		
		if(pageNo!=null){
			if(Integer.valueOf(pageNo).intValue()<=0){
				this.pageNo="1";
				return this.pageNo;
			}
			Integer tmpPageCount= getPageCount();
			if(Integer.valueOf(pageNo).intValue() > tmpPageCount.intValue()){
				this.pageNo=String.valueOf(tmpPageCount);
			}
			return this.pageNo;
		}
		
		if(beginRow<=0){
			return "1";
		}else{
			int tmpPageNum=(beginRow+1)/pageSize;
			if((beginRow+1)%pageSize >0){
				tmpPageNum++;
			}
			Integer tmpPageCount= getPageCount();
			if(tmpPageNum<=0){
				this.pageNo="1";
				return this.pageNo;
			}else{
				pageNo=String.valueOf(tmpPageNum);
				if(tmpPageNum > tmpPageCount.intValue()){
					pageNo = String.valueOf(tmpPageCount);
				}
			}
		}
		
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageCount() {
		if(rowsCount>0){
			if (pageSize<=0){
				pageSize=10;
			}
			pageCount=Math.round(rowsCount/pageSize);
			if(rowsCount % pageSize >0){
				pageCount++;
			}
		}
		return pageCount;
	}
	
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

}
