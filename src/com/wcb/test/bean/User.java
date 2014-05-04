package com.wcb.test.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *@desc 用户表
 *@date 2014-05-01
 *@author knight Wang
 *
 */

@Entity
@Table(name = "T_USER")
public class User {
	
	private int id;
	private String name;
	private String address;
	private Date birthday;
	private String gender = "女" ;
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", address=" + address
				+ ", birthday=" + birthday + ", gender=" + gender + "]";
	}
	
	
}

