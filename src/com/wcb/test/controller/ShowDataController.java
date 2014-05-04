package com.wcb.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wcb.test.bean.PageBean;
import com.wcb.test.bean.Product;
import com.wcb.test.bean.User;
import com.wcb.test.service.QueryDataService;

/**
 * @desc
 * @date 2014-05-01
 * @author knight Wang
 * 
 */

@Controller
@RequestMapping("/show")
public class ShowDataController {
	
	//private Log log = LogFactory.getLog(UploadController.class);
	@Autowired
	QueryDataService queryDataService;
	
	
	/**
	 * 查询用户列表
	 * 
	 * @param excelfile
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/userlist")
	public String userlist(HttpServletRequest request, HttpServletResponse response,
			ModelMap map) {
		
		int pageNo = 0; 
		int pageSize = 10;
		try{
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}catch (Exception e) {
			pageNo = 0;
		}
		try{
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}catch (Exception e) {
			pageSize = 10;
		}
		
		try {
			 PageBean<User> usersPage = queryDataService.getUserPage(pageNo, pageSize);
			 map.put("pageModel", usersPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "userlist";
	}
	
	
	@RequestMapping(value = "/productlist")
	public String productlist(HttpServletRequest request, HttpServletResponse response,
			ModelMap map) {
		int pageNo = 0; 
		int pageSize = 10;
		try{
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}catch (Exception e) {
			pageNo = 0;
		}
		
		try{
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}catch (Exception e) {
			pageSize = 10;
		}
		
		try {
			PageBean<Product> productPage = queryDataService.getProductPage(pageNo, pageSize);
			map.put("pageModel", productPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productlist";
	}
	
	
	@RequestMapping(value = "/showindex", method=RequestMethod.GET)
	public String showindex(HttpServletRequest request, HttpServletResponse response,
			ModelMap map) {
		return "main";
	}
	
	
	 
}
