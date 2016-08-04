package com.tuisitang.generate;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.utils.FileUtils;
import com.tuisitang.common.utils.FreeMarkers;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class DataBaseUtil {
 private static Log logger = LogFactory.getLog(DataBaseUtil.class);

 public static String tplPath = null;
 public static String javaPath = null;
 public static File projectPath = null;
 public static String filePath = null;
 //获取文件分隔符
 public static String separator = File.separator;
	/**
	 * 获取指定数据库和用户的所有表名
	 * 
	 * @param conn
	 *            连接数据库对象
	 * @param user
	 *            用户
	 * @param database
	 *            数据库名
	 * @return
	 * @throws IOException 
	 */
	public static Map<String,String> getAllTableNames(Connection conn, String user,
			String database) throws IOException {
		Map<String, String> tableClassMap = Maps.newHashMap();
		if (conn != null) {
			try {
				DatabaseMetaData dbmd = conn.getMetaData();
				//表名列表
				ResultSet rest = dbmd.getTables(database, null, null,
						new String[] { "TABLE" });
				setGlobalPath();
				List<String> fileNames = getFilesList();
				//输出 table_name
				while (rest.next()) {
					String tableName = rest.getString("TABLE_NAME");
					if(!tableName.toUpperCase().equals("T_ROLE"))
						continue;
					String className = getClassName(tableName, fileNames);
					tableClassMap.put(tableName, className);
					if (className == null) continue;
					StringBuffer insertValue = new StringBuffer();
					StringBuffer insertColumn = new StringBuffer();
					StringBuffer updateColumn = new StringBuffer();
					StringBuffer whereCondition = new StringBuffer();
					ResultSet colRet = dbmd.getColumns(null,"%", tableName,"%");
					while(colRet.next()) { 
						String columnName = colRet.getString("COLUMN_NAME"); 
						String columnType = colRet.getString("TYPE_NAME"); 
						
						if ("id".equals(columnName)) continue;
						if (columnType.equals("VARCHAR") || columnType.equals("CHAR")) {
							insertColumn.append("<if test=\"" + columnName +"!=null and " + columnName +"!=''\">,"+columnName+"</if>\n\t\t");
							insertValue.append("<if test=\"" + columnName +"!=null and " + columnName +"!=''\">,#{"+columnName+"}</if>\n\t\t");
							
							
							updateColumn.append("<if test=\"" + columnName +"!=null and " + columnName +"!=''\">,"+columnName+" = #{"+columnName+"}</if>\n\t\t");
							
							whereCondition.append("<if test=\"" + columnName +"!=null and " + columnName +"!=''\"> and "+columnName+" = #{"+columnName+"}</if>\n\t\t");
							
						} else if (columnType.equals("INT")) {
							insertColumn.append("<if test=\"" + columnName +"!=0\">,"+columnName+"</if>\n\t\t");
							insertValue.append("<if test=\"" + columnName +"!=0\">,${"+columnName+"}</if>\n\t\t");
							
							updateColumn.append("<if test=\"" + columnName +"!=0\">," + columnName+" = #{"+columnName+"}</if>\n\t\t");

							whereCondition.append("<if test=\"" + columnName +"!=0\"> and " + columnName+" = #{"+columnName+"}</if>\n\t\t");
						} else {
							insertColumn.append("<if test=\"" + columnName +"!=null\">,"+columnName+"</if>\n\t\t");
							insertValue.append("<if test=\"" + columnName +"!=null\">,${"+columnName+"}</if>\n\t\t");

							
							updateColumn.append("<if test=\"" + columnName +"!=null\">,"+columnName+" = #{"+columnName+"}</if>\n\t\t");
							
							whereCondition.append("<if test=\"" + columnName +"!=null\"> and "+columnName+" = #{"+columnName+"}</if>\n\t\t");
						}
					}
					//生成模板列信息
					Configuration cfg = new Configuration();
					cfg.setDirectoryForTemplateLoading(new File(tplPath));

					// 定义模板变量
					Map<String, String> model = Maps.newHashMap();
					model.put("className", className);
					model.put("id", "#{id}");
					model.put("offset", "#{offset}");
					model.put("pageSize", "#{pageSize}");
					model.put("tableName", tableName);
					model.put("insertColumn", insertColumn.toString());
					model.put("insertValue", insertValue.toString());
					model.put("updateColumn", updateColumn.toString());
					model.put("whereCondition", whereCondition.toString());
					
					// 生成 DaoTest
					Template template = cfg.getTemplate("mapper.ftl");
					String content = FreeMarkers.renderTemplate(template, model);
					
					String filePath = StringUtils.replaceEach(projectPath + "/src/main/resources/test/" + className + "Mapper.xml" ,
							new String[] { "/"}, new String[] { separator });
					writeFile(content, filePath);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableClassMap;
	}
	
	/**
	 * 设置路径
	 * @return
	 * @throws IOException
	 */
	public static void setGlobalPath() throws IOException {
		// 获取工程路径
		projectPath = new DefaultResourceLoader().getResource("").getFile();
		while (!new File(projectPath.getPath() + separator + "src" + separator + "main").exists()) {
			projectPath = projectPath.getParentFile();
		}
		logger.info("Project Path: " + projectPath.getAbsolutePath());

		String packageName = "com.tuisitang.modules.shop.entity";
		// Java文件路径
		javaPath = StringUtils.replaceEach(projectPath + "/src/main/java/" + StringUtils.lowerCase(packageName),
				new String[] { "/", "." }, new String[] { separator, separator });
		logger.info("Java Path: " + javaPath);
		
		// 模板文件路径
		tplPath = StringUtils.replace(projectPath + "/src/test/java/com/tuisitang/generate/template", "/", separator);
		logger.info("Template Path: " + tplPath);
	}
	
	/**
	 * 将内容写入文件
	 * 
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
			File f = new File(filePath);
			if (f.exists()) {
				f.delete();
			}
			if (FileUtils.createFile(filePath)) {
				FileWriter fileWriter = new FileWriter(filePath, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(content);
				bufferedWriter.close();
				fileWriter.close();
			} else {
				logger.info("生成失败，文件已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getClassName(String tableName, List<String> fileName) {
		String className = tableName.replace("t_", "").replaceAll("_", "");
		for (String f : fileName) {
			if (className.toLowerCase().equals(f.toLowerCase())) {
				return f;
			}
		}
		return null;
	}
	
	public static List<String> getFilesList() throws IOException {
		String classPath = javaPath;
		classPath = classPath.replace("front", "core");
		File file=new File(classPath);
		File[] fileList = file.listFiles();
		List<String> fileName = Lists.newArrayList();
		for (File f : fileList) {
			fileName.add(f.getName().replace(".java", ""));
		}
		return fileName;
	}
	
	public static void main(String[] args) throws IOException {
		Connection conn = null;
		try {
			String url = "jdbc:mysql://jinshulai.mysql.rds.aliyuncs.com:3306/git_jeeshop?useUnicode=true&characterEncoding=utf-8";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, "tst", "tst1qaz2wsx");
			Map<String, String> tableClassMap = getAllTableNames(conn,"tst", "git_jeeshop");
			for(Map.Entry<String, String> entry : tableClassMap.entrySet()){    
			     System.out.println("tableName:" + entry.getKey()+"--->className:"+entry.getValue());    
			}   
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}