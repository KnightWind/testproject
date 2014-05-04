package com.wcb.test.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @desc 销售记录
 * @author knight Wang
 * @Date 2014-5-1
 */

@Entity
@Table(name = "T_SALE_RECORD")
public class SaleRecord {
	
	
	private int id;
	//交易日期
	private Date saledate;
	//用户
	private int user;
	//产品
	private int product;
	//售价
	private double price;
	//销售数量
	private double num;
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getSaledate() {
		return saledate;
	}
	public void setSaledate(Date saledate) {
		this.saledate = saledate;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public int getProduct() {
		return product;
	}
	public void setProduct(int product) {
		this.product = product;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getNum() {
		return num;
	}
	public void setNum(double num) {
		this.num = num;
	}
	
	@Override
	public String toString() {
		return "SaleRecord [id=" + id + ", saledate=" + saledate + ", user="
				+ user + ", product=" + product + ", price=" + price + ", num="
				+ num + "]";
	}
	
	
}

