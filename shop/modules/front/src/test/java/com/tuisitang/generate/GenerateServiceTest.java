package com.tuisitang.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;
import com.tuisitang.common.utils.FileUtils;
import com.tuisitang.common.utils.FreeMarkers;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**    
 * @{#} GenerateServiceTest.java  
 * 
 * 生成Service测试类
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
public class GenerateServiceTest {

	private static Logger logger = LoggerFactory.getLogger(GenerateServiceTest.class);

	public static void main(String[] args) throws Exception {

		// ========== ↓↓↓↓↓↓ 执行前请修改参数，谨慎执行。↓↓↓↓↓↓ ====================

		// 主要提供基本功能模块代码生成。
		// 目录生成结构：{packageName}/{moduleName}/{dao,service}/{className}

		String packageName = "com.tuisitang.modules.shop";

		String moduleName = "front"; // 模块名，例：sys
		String className = "Advert"; // 类名，例：user
		String classAuthor = "paninxb"; // 类作者，例：tuisitang

		// 是否启用生成工具
		Boolean isEnable = true;

		// ========== ↑↑↑↑↑↑ 执行前请修改参数，谨慎执行。↑↑↑↑↑↑ ====================

		if (!isEnable) {
			logger.error("请启用代码生成工具，设置参数：isEnable = true");
			return;
		}

		if (StringUtils.isBlank(moduleName) || StringUtils.isBlank(moduleName)
				|| StringUtils.isBlank(className)) {
			logger.error("参数设置错误：包名、模块名、类名不能为空。");
			return;
		}

		// 获取文件分隔符
		String separator = File.separator;

		// 获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		while (!new File(projectPath.getPath() + separator + "src" + separator + "main").exists()) {
			projectPath = projectPath.getParentFile();
		}
		logger.info("Project Path: {}", projectPath);

		// 模板文件路径
		String tplPath = StringUtils.replace(projectPath + "/src/test/java/com/tuisitang/generate/template", "/", separator);
		logger.info("Template Path: {}", tplPath);

		// Java文件路径
		String javaPath = StringUtils.replaceEach(projectPath + "/src/test/java/" + StringUtils.lowerCase(packageName),
				new String[] { "/", "." }, new String[] { separator, separator });
		logger.info("Java Path: {}", javaPath);

		// 代码模板配置
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(tplPath));

		// 定义模板变量
		Map<String, String> model = Maps.newHashMap();
		model.put("packageName", StringUtils.lowerCase(packageName));
		model.put("moduleName", StringUtils.lowerCase(moduleName));
		model.put("className", StringUtils.uncapitalize(className));
		model.put("ClassName", StringUtils.capitalize(className));
		model.put("classAuthor", StringUtils.isNotBlank(classAuthor) ? classAuthor : "Generate Tools");
		model.put("classVersion", "1.0");
		model.put("classCompany", "TST");

		// 生成 ServiceTest
		Template template = cfg.getTemplate("service.ftl");
		String content = FreeMarkers.renderTemplate(template, model);
		String filePath = javaPath + separator + model.get("moduleName") + separator + "service" + separator  
				+ model.get("ClassName") + "ServiceTest.java";
		writeFile(content, filePath);
		logger.info("Dao: {}", filePath);
		
		logger.info("Generate Success.");
	}

	/**
	 * 将内容写入文件
	 * 
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
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
}
