package com.tuisitang.projects.pm.modules.shop.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;

import com.google.common.collect.Lists;
import com.tuisitang.common.ds.SchemaContextHolder;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.projects.pm.modules.shop.entity.Catalog;
import com.tuisitang.projects.pm.modules.shop.entity.Product;
import com.tuisitang.projects.pm.modules.shop.entity.ProductAttr;
import com.tuisitang.projects.pm.modules.shop.entity.ProductSpec;
import com.tuisitang.projects.pm.modules.shop.entity.Supplier;

public class ProductExportServiceTest extends SpringTransactionalContextTests {

	@Autowired
	private ProductService productService;
	
	@Test
	@Rollback(false)
	public void analyze() {
//		SchemaContextHolder.setSchema(SchemaContextHolder.SCHEMA_TEST);
		List<Product> list = productService.findAllProduct();
		logger.info("{}", list.size());
//		DataSource dataSource = SpringContextHolder.getBean("shopDataSource");
//		final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		
		String products = ",60,61,63,64,65,66,67,68,70,75,76,77,92,93,94,95,96,97,98,100,101,102,103,104,105,106,107,276,291,308,309,310,311,312,547,567,568,569,574,575,581,584,586,587,589,590,591,592,593,";
		
		DecimalFormat df = new DecimalFormat("0.00");
		int rownum = 0;
		String title = "商品导出列表";
		SXSSFWorkbook wb;
		// ID 产品 规格 单位 最小购买数 出厂价 出售价 物流费 利润率
		List<String> headerList = Lists.newArrayList();
		headerList.add("ID");
		headerList.add("商品编号");
		headerList.add("商品名称");
		headerList.add("商品分类");
		headerList.add("商品规格");
		headerList.add("活动");
		headerList.add("单位");
		headerList.add("最小购买数");
		headerList.add("出厂价");
		headerList.add("出售价");
		headerList.add("物流费");
		headerList.add("利润率");

		wb = new SXSSFWorkbook(500);
		Sheet sheet = wb.createSheet("Export");
		Map<String, CellStyle> styles = createStyles(wb);
		// Create title
		if (StringUtils.isNotBlank(title)) {
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), headerList
							.size() - 1));
		}
		// Create header
		if (headerList == null || headerList.isEmpty()) {
			throw new RuntimeException("headerList not null!");
		}
		Row headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			if (ss.length == 2) {
				cell.setCellValue(ss[0]);
				Comment comment = sheet.createDrawingPatriarch()
						.createCellComment(
								new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3,
										(short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			} else {
				cell.setCellValue(headerList.get(i));
			}
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {
			int colWidth = sheet.getColumnWidth(i) * 2;
			sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
		}

		for (final Product product : list) {
			Long id = product.getId();
			String activity = "";
			if(products.indexOf(","+id+",")>=0){
				activity="零利专场";
			}
			String name = product.getName();
			double expressPrice = product.getExpressPrice() == null ? 19.99 : product.getExpressPrice();
			
			// 产品 规格 单位 最小购买数 出厂价 出售价 物流费 利润率
			List<ProductSpec> psList = productService.findProductSpec(product.getId());
			List<Catalog> catalogList = product.getCatalogList();
			String catalogs = "";
			for (Catalog catalog : catalogList) {
				catalogs += catalog.getName() + "(" + catalog.getId() + ")"
						+ ",";
			}
			if (psList.isEmpty()) {
				double price = product.getPrice();
				double factoryPrice = product.getFactoryPrice();
				
				int min = product.getMinSale() == null || product.getMinSale() == 0 ? 1 : product.getMinSale();
				String rate = "0.00";
				if (StringUtils.isBlank(activity)) {
					rate = df
							.format(((0.95 * 0.9 * price - factoryPrice) * min)
									* 100 / (factoryPrice * min));
				} else {
					rate = df
							.format(((0.95 * 0.9 * price - factoryPrice) * min - expressPrice)
									* 100 / (factoryPrice * min));
				}
				
				System.out.println(String.format("%s(%d) 出厂价 %f 出售价 %f 物流费 %f 利润率 %s\n ", name,id,factoryPrice,price,expressPrice,rate));
				logger.info(String.format("%s(%d) 出厂价 %f 出售价 %f 物流费 %f 利润率 %s\n ", name,id,factoryPrice,price,expressPrice,rate));
				Row row = sheet.createRow(rownum++);
				/*
				 * headerList.add("ID");
		headerList.add("商品编号");
		headerList.add("商品名称");
		headerList.add("商品分类");
		headerList.add("商品规格");
		headerList.add("活动");
		headerList.add("单位");
		headerList.add("最小购买数");
		headerList.add("出厂价");
		headerList.add("出售价");
		headerList.add("物流费");
		headerList.add("利润率");
				 */
				addCell(row, 0, id, 0, id.getClass(), styles);
				addCell(row, 1, "", 0, String.class, styles);
				addCell(row, 2, name, 0, String.class, styles);
				addCell(row, 3, catalogs, 0, String.class, styles);
				addCell(row, 4, "", 0, String.class, styles);
				addCell(row, 5, activity, 0, String.class, styles);
				addCell(row, 6, product.getUnit(), 0, String.class, styles);
				addCell(row, 7, min, 0, Integer.class, styles);
				addCell(row, 8, df.format(factoryPrice), 0, String.class, styles);
				addCell(row, 9, df.format(price), 0, String.class, styles);
				addCell(row, 10, df.format(expressPrice), 0, String.class, styles);
				addCell(row, 11, rate, 0, String.class, styles);
			} else {
				for (ProductSpec ps : psList) {
					double price = ps.getNowPrice();
					double factoryPrice = ps.getPrice();
//					double expressPrice = product.getExpressPrice();
					int min = ps.getMinSale() == null || ps.getMinSale() == 0 ? 1 : ps.getMinSale();
					String rate = "0.00";
					if (StringUtils.isBlank(activity)) {
						rate = df
								.format(((0.95 * 0.9 * price - factoryPrice) * min)
										* 100 / (factoryPrice * min));
					} else {
						rate = df
								.format(((0.95 * 0.9 * price - factoryPrice) * min - expressPrice)
										* 100 / (factoryPrice * min));
					}
					String spec = (StringUtils.isBlank(ps.getColor()) ? "" : "颜色：" + ps.getColor())
							+ "/" + (StringUtils.isBlank(ps.getType()) ? "" : "类型：" + ps.getType())
							+ "/" + (StringUtils.isBlank(ps.getSize()) ? "" : "尺寸：" + ps.getSize());
					
					Row row = sheet.createRow(rownum++);
					addCell(row, 0, id, 0, id.getClass(), styles);
					addCell(row, 1, "", 0, String.class, styles);
					addCell(row, 2, name, 0, String.class, styles);
					addCell(row, 3, catalogs, 0, String.class, styles);
					addCell(row, 4, spec, 0, String.class, styles);
					addCell(row, 5, activity, 0, String.class, styles);
					addCell(row, 6, product.getUnit(), 0, String.class, styles);
					addCell(row, 7, min, 0, Integer.class, styles);
					addCell(row, 8, df.format(factoryPrice), 0, String.class, styles);
					addCell(row, 9, df.format(price), 0, String.class, styles);
					addCell(row, 10, df.format(expressPrice), 0, String.class, styles);
					addCell(row, 11, rate, 0, String.class, styles);
					
					System.out.println(String.format("%s(%d) %s 出厂价 %f 出售价 %f 物流费 %f 利润率 %s\n ", name,id,spec,factoryPrice,price,expressPrice,rate));
					logger.info(String.format("%s(%d) %s 出厂价 %f 出售价 %f 物流费 %f 利润率 %s\n ", name,id,spec,factoryPrice,price,expressPrice,rate));
				}
			}
		}

		try {
			wb.write(new FileOutputStream("/Users/xubin/Desktop/" + title + ".xlsx"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
