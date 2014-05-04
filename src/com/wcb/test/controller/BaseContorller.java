package com.wcb.test.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

public class BaseContorller {
	
	@InitBinder
	public void initBinder(WebDataBinder binder,WebRequest req){
		
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
			@Override
			public void setAsText(String text)throws IllegalArgumentException{
				
				Date date = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
				if(text!=null && !text.equals("")){
					try{
						date = sdf.parse(text);
						
					}catch(Exception e){
						
						date = new Date();
					}
				}
				setValue(date);
			}
			
		});
		
	}
}
