package com.wcb.test.service;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wcb.dao.BaseDao;
import com.wcb.test.bean.Product;
import com.wcb.test.bean.SaleRecord;
import com.wcb.test.bean.User;

/**
 * @desc 处理文件上传至数据库
 * @author knight Wang
 * @Date 2014-5-2
 */
 
public class UploadDataService {

	 
	private BaseDao simpleDao;
	
	public BaseDao getSimpleDao() {
		return simpleDao;
	}

	public void setSimpleDao(BaseDao simpleDao) {
		this.simpleDao = simpleDao;
	}


	private Log log = LogFactory.getLog(UploadDataService.class);

	public boolean importDataFromExcel(InputStream in) {

		try {
			XSSFWorkbook wb = new XSSFWorkbook(in);
			Sheet userSheet = wb.getSheet("User");
			Sheet productSheet = wb.getSheet("Product");
			Sheet saleSheet = wb.getSheet("Sale");

			getUserFormXSheet(userSheet);

			getProductsFormXSheet(productSheet);

			getSaleRecordsFormXSheet(saleSheet);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean testInsertUser() {
		try {
			User user = new User();

			user.setId(1);
			user.setName("wangchaobo");
			user.setAddress("hunan");
			user.setBirthday(new Date());
			// user.setGender(1);
			simpleDao.createEntity(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private int getUserFormXSheet(Sheet userSheet) {
		int count = 0;
		if(userSheet==null) return 0;
		try {
			int rowStart = userSheet.getFirstRowNum();
			int rowEnd = userSheet.getLastRowNum();
			for (int i = rowStart + 1; i <= rowEnd; i++) {
				Row row = userSheet.getRow(i);

				User user = new User();

				String strid = String.valueOf(getXssCellData(row.getCell(0)));
				if ("".equals(strid)) continue;
				int id = Integer.parseInt(strid);

				String name = (String) getXssCellData(row.getCell(1));
				String address = (String) getXssCellData(row.getCell(2));

				String str_borthday = getXssCellData(row.getCell(3)).toString();
				Date borthday = null;
				if (!"".equals(str_borthday)) {
					try{
						borthday = new SimpleDateFormat("yyyy-MM-dd")
						.parse(str_borthday);
					}catch (Exception e) {
						
					}
				}

				String gender = (String) getXssCellData(row.getCell(4));

				user.setId(id);
				user.setName(name);
				user.setAddress(address);
				user.setBirthday(borthday);
				if ("男".equals(gender)) {
					user.setGender("男");
				}

				simpleDao.createEntity(user);
				count++;
			}

		} catch (Exception e) {

			e.printStackTrace();
			log.error("getUserFormXSheet exception !");
		}
		return count;
	}

	/**
	 * 从excel表格中获取产品数据
	 * 
	 * @param productSheet
	 * @return
	 */
	private int getProductsFormXSheet(Sheet productSheet) {
		int count = 0;
		if(productSheet==null) return 0;
		try {

			int rowStart = productSheet.getFirstRowNum();
			int rowEnd = productSheet.getLastRowNum();
			for (int i = rowStart + 1; i <= rowEnd; i++) {
				Row row = productSheet.getRow(i);

				Product product = new Product();

				String strid = String.valueOf(getXssCellData(row.getCell(0)));
				if ("".equals(strid))
					strid = "0";
				int id = Integer.parseInt(strid);

				String name = (String) getXssCellData(row.getCell(1));

				String type = (String) getXssCellData(row.getCell(2));

				String coststr = (String) getXssCellData(row.getCell(3));
				double cost = 0d;
				if (!"".equals(coststr)) {
					cost = Double.parseDouble(coststr);
				}
				String unit = (String) getXssCellData(row.getCell(4));
				product.setId(id);
				product.setPname(name);
				product.setType(type);
				product.setCost(cost);
				product.setUnit(unit);

				simpleDao.createEntity(product);
				count++;
			}
		} catch (Exception e) {

			e.printStackTrace();
			log.error("getProductsFormXSheet exception !");
		}
		return count;
	}
	
	/**
	 * 获取销售记录
	 * @param productSheet
	 * @return
	 */
	private int getSaleRecordsFormXSheet(Sheet salesSheet) {
		int count = 0;
		if(salesSheet==null) return 0;
		try {
			int rowStart = salesSheet.getFirstRowNum();
			int rowEnd = salesSheet.getLastRowNum();
			for (int i = rowStart + 1; i <= rowEnd; i++) {
				Row row = salesSheet.getRow(i);

				SaleRecord sr = new SaleRecord();

				// 日期
				String datestr = String.valueOf(getXssCellData(row.getCell(0)));
				if ("".equals(datestr))
					continue;
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(datestr);
				} catch (Exception e) {
					continue;
				}

				String struser = String.valueOf(getXssCellData(row.getCell(1)));
				// if have not user info discard this record
				if ("".equals(struser))
					continue;
				int user = Integer.parseInt(struser);

				String strproduct = String.valueOf(getXssCellData(row
						.getCell(2)));
				// if have not user info discard this record
				if ("".equals(strproduct))
					continue;
				int product = Integer.parseInt(strproduct);

				// 购买价格
				String pricestr = (String) getXssCellData(row.getCell(3));
				double price = 0d;
				if (!"".equals(pricestr)) {
					price = Double.parseDouble(pricestr);
				}

				// 购买量
				String numstr = (String) getXssCellData(row.getCell(3));
				double num = 0d;
				if (!"".equals(numstr)) {
					num = Double.parseDouble(numstr);
				}

				sr.setNum(num);
				sr.setPrice(price);
				sr.setProduct(product);
				sr.setUser(user);
				sr.setSaledate(date);

				simpleDao.createEntity(sr);
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getSaleRecordsFormXSheet exception !");
		}
		return count;
	}

	
	/**
	 * 获取每个cell的数据
	 * @param cell
	 * @return object
	 */
	public Object getXssCellData(Object cell) {
		Object cellData = "";
		if (cell != null) {
			XSSFCell xssCell = (XSSFCell) cell;
			if (xssCell != null) {
				switch (xssCell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					cellData = xssCell.getStringCellValue();
					if (cellData != null) {
						cellData = String.valueOf(cellData).trim();
					}
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(xssCell)) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						cellData = df.format(xssCell.getDateCellValue());
						df = null;
					} else {
						DecimalFormat df = new DecimalFormat("0");
						cellData = df.format(xssCell.getNumericCellValue());
						df = null;
					}
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					cellData = "";
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					cellData = String.valueOf(xssCell.getBooleanCellValue());
					break;
				case XSSFCell.CELL_TYPE_ERROR:
					break;
				default:
					break;
				}
			}
			xssCell = null;

		}
		return cellData;
	}
}
