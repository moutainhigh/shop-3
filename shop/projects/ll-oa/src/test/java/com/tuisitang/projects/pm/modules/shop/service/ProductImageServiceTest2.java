package com.tuisitang.projects.pm.modules.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import com.tuisitang.common.ds.SchemaContextHolder;
import com.tuisitang.common.service.ServiceException;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.common.utils.SpringContextHolder;

public class ProductImageServiceTest2 extends SpringTransactionalContextTests {

	@Autowired
	private ProductService productService;
	
	// 编码  
    private static final String ECODING = "UTF-8";  
    // 获取img标签正则  
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";  
    // 获取src路径的正则  
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";
	
	@Test
	@Rollback(false)
	public void sync() {
		SchemaContextHolder.setSchema(SchemaContextHolder.SCHEMA_PRODUCTION);
		
		DataSource dataSource = SpringContextHolder.getBean("shopDataSource");
		final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		String sql = "SELECT a.* FROM t_product a ";
		
		List<Map<String, Object>> l = jdbc.queryForList(sql);
		
		for (Map<String, Object> m : l) {
			Integer id = (Integer) m.get("id");
			String name = (String) m.get("name");
			String productHTML = (String) m.get("productHTML");
			if (!StringUtils.isBlank(productHTML)) {
				productHTML = StringEscapeUtils.unescapeHtml(productHTML);
//				productHTML = productHTML.replaceAll("attached/details/", "http://img.baoxiliao.com/attached/details/");
//				productHTML = productHTML.replaceAll("http://img.baoxiliao.com/http://img.baoxiliao.com/attached/details/",
//								"http://img.baoxiliao.com/attached/details/");
//				productHTML = productHTML.replaceAll("align=\"left\"", "align=\"center\"");
				
				logger.info("之前{}", productHTML);
				
				List<String> imgs = getImageSrc(getImageUrl(productHTML));
				productHTML = "";
				for (String img : imgs) {
					if (productHTML.indexOf(img) < 0)
						productHTML += "<img src=\"" + img +"\" width=\"800\" align=\"center\" />";
					else 
						logger.info("{}", img);
				}
				logger.info("之后{}", productHTML);
				productHTML = StringEscapeUtils.escapeHtml(productHTML);
				
				jdbc.update("update t_product set productHTML = ? where id = ? ", productHTML, id);
			}
		}
		
	}
	
	/*** 
     * 获取ImageUrl地址 
     *  
     * @param HTML 
     * @return 
     */  
	private List<String> getImageUrl(String HTML) {
		Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
		List<String> list = new ArrayList<String>();
		while (matcher.find()) {
			String img = matcher.group();
			if (!list.contains(img))
				list.add(img);
		}
		return list;
	}  
  
    /*** 
     * 获取ImageSrc地址 
     *  
     * @param listImageUrl 
     * @return 
     */  
    private List<String> getImageSrc(List<String> listImageUrl) {  
        List<String> list = new ArrayList<String>();  
        for (String image : listImageUrl) {  
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);  
            while (matcher.find()) {  
            	list.add(matcher.group().substring(0, matcher.group().length() - 1));  
            }  
        }  
        return list;  
    }  
	
}
