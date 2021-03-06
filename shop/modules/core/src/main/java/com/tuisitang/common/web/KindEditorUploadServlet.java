package com.tuisitang.common.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.resumableio.ResumeableIoApi;
import com.qiniu.api.rs.PutPolicy;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.utils.Identities;
import com.tuisitang.modules.shop.utils.Global;

public class KindEditorUploadServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(KindEditorUploadServlet.class);
	
	private static final JsonMapper JSON_MAPPER = JsonMapper.getInstance();
	
	// 定义允许上传的文件扩展名
	private static Map<String, String> extMap = Maps.newHashMap();
	{
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	}
	
	//最大文件大小
	private static long maxSize = 1024 * 1024 * 10;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String prefix = req.getParameter("prefix");
		if (prefix == null)
			prefix = "";
		logger.info("prefix {}", prefix);
		Mac mac = new Mac(Global.getQiniuAccessKey(), Global.getQiniuSecretKey());
        
        Map<String, Object> returnMap = Maps.newHashMap();

		if(!ServletFileUpload.isMultipartContent(req)){
			resp.getWriter().write(buildErrorMsg("请选择文件"));
			return;
		}

		String ext = req.getParameter("ext");
		if (ext == null) {
			ext = "image";
		}
		if(!extMap.containsKey(ext)){
			resp.getWriter().write(buildErrorMsg("非法的上传文件"));
			return;
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items;
		try {
			items = upload.parseRequest(req);
			Iterator<FileItem> it = items.iterator();
			while (it.hasNext()) {
				FileItem item = it.next();
				String fileName = item.getName();
				if (!item.isFormField()) {
					//检查文件大小
					if(item.getSize() > maxSize){
						resp.getWriter().write(buildErrorMsg("上传文件大小超过限制"));
						return;
					}
					//检查扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(!Arrays.<String>asList(extMap.get(ext).split(",")).contains(fileExt)){
						resp.getWriter().write(buildErrorMsg("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(ext) + "格式。"));
						return;
					}
					
					String mimeType = "image/jpeg";
					if (fileExt.equals("png")) {
						mimeType = "image/png";
					}
					PutPolicy p = new PutPolicy(Global.getQiniuBucketName());
					String upToken;
					upToken = p.token(mac);
					String key = prefix + Identities.randomBase62(32);//item.getName();
					PutRet ret = ResumeableIoApi.put(item.getInputStream(), upToken, key, mimeType);

					returnMap.put("error", 0);
					returnMap.put("url", Global.getQiniuDomain() + "/" + key);

					resp.setContentType("application/json; charset=UTF-8");
					resp.getWriter().println(JSON_MAPPER.toJson(returnMap));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			resp.getWriter().write(buildErrorMsg(e.getMessage()));
		}
       
	}
	
	private String buildErrorMsg(String message) {
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("error", "1");
		returnMap.put("message", message);
		return JSON_MAPPER.toJson(returnMap);
	}

}
