package com.wcb.test.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wcb.test.componet.UploadHandler;

/**
 * @desc
 * @date 2014-05-01
 * @author knight Wang
 * 
 */

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	//private Log log = LogFactory.getLog(UploadController.class);
	//@Autowired
	//UploadDataService uploadDataService;
	
	@Autowired
	UploadHandler uploadHandler;
	
	/**
	 * 上传数据的uploader
	 * 
	 * @param excelfile
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/uploadfile")
	public String uploadfile(@RequestParam MultipartFile excelfile,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap map) {
		try {
			String sessionId = request.getSession().getId();
			System.out.println("set sessionId = "+sessionId);
			InputStream in = excelfile.getInputStream();
			if(uploadHandler.handler(in, sessionId)){
				map.put("status", "success");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "loding";
	}
	
	
	@RequestMapping(value = "/getUploadState")
	@ResponseBody
	public String getState(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap map) {
		String sid = request.getSession().getId();
		String info = uploadHandler.getUploadStatus(sid);
		System.out.println("get info = "+info);
		return info;
	}
	
	
	
	public static void main(String ...args){
		
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
						new FileInputStream(
									new File("C:/Users/xiaopang/Desktop/Teradata Central Team-笔试题目-v13.04.xlsx")
									),"utf-8"));
			
			System.out.println(br.readLine());
//			Workbook book = Workbook.getWorkbook( );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
