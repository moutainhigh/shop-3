package com.tuisitang.projects.pm.modules.shop.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;

import com.google.common.collect.Lists;
import com.gson.util.HttpKit;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.common.utils.ValidateUtils;
import com.tuisitang.projects.pm.modules.wedding.entity.WeddingCompany;
import com.tuisitang.projects.pm.modules.wedding.service.WeddingCompanyService;

public class WeddingCompanyServiceTest extends SpringTransactionalContextTests {

	@Resource(name="dataSource")
	private DataSource dataSource;
	
	private JsonMapper jsonMapper = JsonMapper.getInstance();
	
	@Autowired
	private WeddingCompanyService weddingCompanyService;
	
	@Test
	@Rollback(false)
	public void analyze() {
		final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		final List<WeddingCompany> l = Lists.newArrayList();
		List<String> sqlList = Lists.newArrayList();
		sqlList.add("select t.* from user_2 t");
		sqlList.add("select t.* from user t");
		for (final String sql : sqlList) {
			jdbc.query(sql, new RowMapper<WeddingCompany>() {
				@Override
				public WeddingCompany mapRow(ResultSet rs, int rowNum) throws SQLException {
					String name = rs.getString("name");
					String password = rs.getString("password");
					String qq = rs.getString("qq");
					String tel = rs.getString("tel");
					String company = rs.getString("company");
					String address = rs.getString("address");
					Date registDate = rs.getDate("regdate");
					if (StringUtils.isBlank(qq) && StringUtils.isBlank(tel)) {
						return null;
					}
					WeddingCompany wc = new WeddingCompany();
					wc.setUsername(name);
					wc.setPlainPassword(password);
					wc.setPassword(password);
					wc.setName(name);
					wc.setQq(qq);
					wc.setTel(tel);
					wc.setCompany(company);
					wc.setAddress(address);
					wc.setRegistDate(registDate);
					wc.setVersion(sql.indexOf("user_2")>=0 ? WeddingCompany.VERSION_20 : WeddingCompany.VERSION_15);
					if (!l.contains(wc)) {
						l.add(wc);
						if (!StringUtils.isBlank(tel) && ValidateUtils.isMobile(tel)) {
							wc.setMobile(tel);
							String url = "http://shouji.51240.com/" +tel + "__shouji/";
							
							try {
								Document doc = Jsoup.parse(new URL(url), 60 * 1000);
								Elements elements = doc.getElementsByClass("kuang_biaoge");
								if(elements!=null && elements.size()>0){
									Element e = elements.get(0);
									wc.setJson(e.text());
								}
//								Thread.sleep(100);
							} catch (Exception e) {
								e.printStackTrace();
							} 
							
//							String url = "http://apis.haoservice.com/mobile?phone=" + tel + "&key=c61de12726f74dbcaff89b2c38acb0d3";
//							try {
//								String json = HttpKit.get(url);
//								wc.setJson(json);
////								HaoMobile hm = jsonMapper.fromJson(json, HaoMobile.class);
////								if("0".equals(hm.getError_code())){
////									
////								}
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
						}
					}
					return wc;
				}
			});
		}
//		Collections.sort(l, new Comparator<WeddingCompany>() {
//
//			@Override
//			public int compare(WeddingCompany o1, WeddingCompany o2) {
//				long t1 = o1.getRegistDate() == null ? 0L : o1.getRegistDate().getTime();
//				long t2 = o2.getRegistDate() == null ? 0L : o2.getRegistDate().getTime();
//				return t2 >= t1 ? 1 : -1;
//			}
//		});
		for(WeddingCompany wc : l) {
			logger.info("{}", wc);
		}
		logger.info("{}", l.size());
		weddingCompanyService.saveWeddingCompany(l);
	}
	
	/**
	 * 创建表格样式
	 * @param wb 工作薄对象
	 * @return 样式列表
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put("data3", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		return styles;
	}
	
	/**
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @param align 对齐方式（1：靠左；2：居中；3：靠右）
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType, Map<String, CellStyle> styles){
		Cell cell = row.createCell(column);
		CellStyle style = styles.get("data"+(align>=1&&align<=3?align:""));
		try {
			if (val == null){
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue((String) val);
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				cell.setCellValue((Double) val);
			} else if (val instanceof Float) {
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
//				DataFormat format = wb.createDataFormat();
//	            style.setDataFormat(format.getFormat("yyyy-MM-dd"));
//				cell.setCellValue((Date) val);
			} else {
				if (fieldType != Class.class){
					cell.setCellValue((String)fieldType.getMethod("setValue", Object.class).invoke(null, val));
				}else{
					cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
						"fieldtype."+val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val));
				}
			}
		} catch (Exception ex) {
			cell.setCellValue(val.toString());
		}
		cell.setCellStyle(style);
		return cell;
	}
	
}

class HaoMobile {
	private String error_code;// 0
	private String reason;//
	private Map<String, String> result;

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Map<String, String> getResult() {
		return result;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}

}
