package com.wcb.test.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @desc 
 * @author knight Wang
 * @Date 2014-5-1
 */
@Entity
@Table(name = "T_PRODUCT")
public class Product {
	
	private int id;
	
	//产品名称
	private String pname;
	
	//产品类型
	private String type;
	
	//产品成本？
	private double cost;
	
	//产品单位
	private String unit;
	
	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", pname=" + pname + ", type=" + type
				+ ", cost=" + cost + ", unit=" + unit + "]";
	}
	
	
	
	
}

