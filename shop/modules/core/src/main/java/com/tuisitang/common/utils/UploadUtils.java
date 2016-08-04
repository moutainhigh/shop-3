//package com.tuisitang.common.utils;
//
//import java.io.File;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.tuisitang.common.config.Global;
//import com.tuisitang.modules.finance.entity.Image;
//import com.tuisitang.modules.finance.entity.Member;
//import com.tuisitang.modules.finance.service.MemberService;
//
///**    
// * @{#} UploadUtils.java   
// * 
// * 上传工具
// *
// * <p>Copyright: Copyright(c) 2015 </p> 
// * <p>Company: tst</p>
// * @version 1.0
// * @author <a href="mailto:paninxb@gmail.com">panin</a>   
// *  
// */
//public class UploadUtils {
//	
//	private static final Logger logger = LoggerFactory.getLogger(UploadUtils.class);
//
//	/**
//	 * 上传图片
//	 * 
//	 * @param request
//	 * @param memberService
//	 * @param member
//	 * @return
//	 */
//	public static List<Map<String, Object>> upload(MultipartHttpServletRequest request, MemberService memberService, Member member) {
//		List<Map<String, Object>> returnList = Lists.newArrayList();
//		Map<String, Object> map = Maps.newHashMap();
//		Date sysTime = new Date(System.currentTimeMillis());
//		String contentType = request.getContentType();
//		String sep = System.getProperty("file.separator");
//		Iterator<String> fileNames = request.getFileNames();
//		while (fileNames.hasNext()) {
//			boolean isSuccess = true;
//			String msg = "";
//			String baseUrl = "";
//			String newFileName = "";
//			try {
//				String fileName = fileNames.next();
//				MultipartFile file = request.getFile(fileName);
//				String originalFileName = file.getOriginalFilename();
//				String suffix = originalFileName.lastIndexOf(".") > 0 ? originalFileName.substring(originalFileName.lastIndexOf(".") + 1) : "";
//				newFileName = Identities.uuid2();
//				String thumbnailFileName = newFileName + "@240*240.png";
//				newFileName = newFileName + "." + suffix;
//				Long size = file.getSize();
//
//				byte[] bytes = file.getBytes();
//				String path = null;
//				String basePath = null;
//				if (member == null || member.getId() == null) {
//					basePath = Global.getConfig("uploadPath") 
//							+ sep + DateUtils.formatDate(sysTime, "yyyy")
//							+ sep + DateUtils.formatDate(sysTime, "MM") + sep + DateUtils.formatDate(sysTime, "dd");
//				} else {
//					basePath = Global.getConfig("userFilePath") 
//							+ sep + member.getId()
//							+ sep + DateUtils.formatDate(sysTime, "yyyy")
//							+ sep + DateUtils.formatDate(sysTime, "MM") 
//							+ sep + DateUtils.formatDate(sysTime, "dd");
//				}
//				path = request.getSession().getServletContext().getRealPath("/") + basePath;
//				baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + 
//						request.getSession().getServletContext().getContextPath() + "/" + basePath;
//				logger.info("originalFilename = {},\n path = {},\n basePath = {}", originalFileName, path, basePath);
//				File f = new File(path);
//				if (!f.exists()) {
//					f.mkdirs();
//				}
//				
//				File srcFile = new File(f + sep + newFileName);
//				File destFile = new File(f + sep + thumbnailFileName);
//				FileCopyUtils.copy(bytes, srcFile);
//				
//				ImageUtils.createThumbnailImage(srcFile, destFile);
//				
//				Image image = new Image();
//				image.setFileName(newFileName);
//				image.setOriginalFileName(originalFileName);
//				image.setThumbnailFileName(thumbnailFileName);
//				if (member != null) {
//					image.setOwnerId(member.getId());
//					image.setOwnerName(member.getName());
//				}
//				image.setSize(size);
//				image.setContentType(contentType);
//				image.setPath(path);
//				image.setBaseUrl(baseUrl);
//				
//				memberService.saveImage(image);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("{}", e.getMessage());
//				isSuccess = false;
//				msg = e.getMessage();
//			}
//			map.put("isSuccess", isSuccess);
//			map.put("msg", msg);
//			map.put("url", baseUrl + "/" + newFileName);
//			returnList.add(map);
//		}
//		return returnList;
//	}
//	
//	/**
//	 * 上传头像
//	 * 
//	 * @param request
//	 * @param memberService
//	 * @param member
//	 * @return
//	 */
//	public static Map<String, Object> uploadPic(MultipartHttpServletRequest request, MemberService memberService, Member member) {
//		Map<String, Object> map = Maps.newHashMap();
//		Date sysTime = new Date(System.currentTimeMillis());
//		String contentType = request.getContentType();
//		String sep = System.getProperty("file.separator");
//		Iterator<String> fileNames = request.getFileNames();
//		if (fileNames.hasNext()) {
//			boolean isSuccess = true;
//			String msg = "";
//			String baseUrl = "";
//			String newFileName = "";
//			String thumbnailFileName = "";
//			try {
//				String fileName = fileNames.next();
//				MultipartFile file = request.getFile(fileName);
//				String originalFileName = file.getOriginalFilename();
//				String suffix = originalFileName.lastIndexOf(".") > 0 ? originalFileName.substring(originalFileName.lastIndexOf(".") + 1) : "";
//				newFileName = Identities.uuid2();
//				thumbnailFileName = newFileName + "@150*150." + suffix;
//				newFileName = newFileName + "." + suffix;
//				Long size = file.getSize();
//
//				byte[] bytes = file.getBytes();
//				String path = null;
//				String basePath = null;
//				if (member == null || member.getId() == null) {
//					basePath = Global.getConfig("uploadPath") 
//							+ sep + DateUtils.formatDate(sysTime, "yyyy")
//							+ sep + DateUtils.formatDate(sysTime, "MM") + sep + DateUtils.formatDate(sysTime, "dd");
//				} else {
//					basePath = Global.getConfig("userFilePath") 
//							+ sep + member.getId()
//							+ sep + DateUtils.formatDate(sysTime, "yyyy")
//							+ sep + DateUtils.formatDate(sysTime, "MM") 
//							+ sep + DateUtils.formatDate(sysTime, "dd");
//				}
//				path = request.getSession().getServletContext().getRealPath("/") + basePath;
//				baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + 
//						request.getSession().getServletContext().getContextPath() + "/" + basePath;
//				logger.info("originalFilename = {},\n path = {},\n basePath = {}", originalFileName, path, basePath);
//				File f = new File(path);
//				if (!f.exists()) {
//					f.mkdirs();
//				}
//				
//				File srcFile = new File(f + sep + newFileName);
//				File destFile = new File(f + sep + thumbnailFileName);
//				FileCopyUtils.copy(bytes, srcFile);
//				
//				ImageUtils.createThumbnailImage(srcFile, destFile, 150);
//				
//				Image image = new Image();
//				image.setFileName(newFileName);
//				image.setOriginalFileName(originalFileName);
//				image.setThumbnailFileName(thumbnailFileName);
//				if (member != null) {
//					image.setOwnerId(member.getId());
//					image.setOwnerName(member.getName());
//				}
//				image.setSize(size);
//				image.setContentType(contentType);
//				image.setPath(path);
//				image.setBaseUrl(baseUrl);
//				
//				memberService.saveImage(image);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("{}", e.getMessage());
//				isSuccess = false;
//				msg = e.getMessage();
//			}
//			map.put("isSuccess", isSuccess);
//			map.put("msg", msg);
////			map.put("url", baseUrl + "/" + newFileName);
//			map.put("url", baseUrl + "/" + thumbnailFileName);
//		} else {
//			map.put("isSuccess", false);
//			map.put("msg", "上传文件为空！");
//		}
//		return map;
//	}
//}
