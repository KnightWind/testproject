package com.wcb.test.util;

public class BaseUtil {
	
	/**
	 * 生成加密密码
	 * @param pwd
	 * @return
	 */
	public static String getMiPwd(String pwd){
		StringBuffer gpwd = new StringBuffer("");
		if(pwd!=null){
			char[] chars = pwd.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				 if(i<2 ||i==3||i==4){
					 gpwd.append((int)chars[i]);
				 }else{
					 gpwd.append(chars[i]);
				 } 
			}
		}else{
			
			gpwd.append(pwd);
		}
		return gpwd.toString();
	}
	
	/**
	 * 解码
	 * @param mmPwd
	 * @return
	 */
	public static String parsePwd(String mmPwd){
		StringBuffer gpwd = new StringBuffer("");
		String unicode = "";
		int codeint;
		if(mmPwd!=null){
			
			if(mmPwd.length()>1){
				unicode = mmPwd.substring(0, 2);
				codeint = Integer.parseInt(unicode);
				gpwd.append((char)codeint);
			}
			
			if(mmPwd.length()>2){
				unicode = mmPwd.substring(2, 3);
				gpwd.append(unicode);
			}
			
			if(mmPwd.length()>4){
				unicode = mmPwd.substring(3, 5);
				codeint = Integer.parseInt(unicode);
				gpwd.append((char)codeint);
			}
			
			if(mmPwd.length()>5){
				unicode = mmPwd.substring(5);
				gpwd.append(unicode);
			}
		}else{
			
			gpwd.append(mmPwd);
		}
		return gpwd.toString();
	}
	
	public static void main(String...args){
		
		System.out.println(getMiPwd("123456"));
	}
}
