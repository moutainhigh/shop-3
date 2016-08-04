package com.tuisitang.common.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;
import com.qiniu.api.rsf.RSFEofException;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.modules.shop.utils.Global;

public class KindEditorServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(KindEditorServlet.class);
	
	private static final JsonMapper JSON_MAPPER = JsonMapper.getInstance();

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
        
        RSFClient client = new RSFClient(mac);
        String marker = "";
            
        List<ListItem> images = new ArrayList<ListItem>();
        ListPrefixRet ret = null;
        while (true) {
            ret = client.listPrifix(Global.getQiniuBucketName(), prefix, marker, 10);
            marker = ret.marker;
            images.addAll(ret.results);
            if (!ret.ok()) {// 数据为空时或者发生异常时跳出
                break;
            }
        }
        if (ret.exception.getClass() != RSFEofException.class) {// 处理异常
        } 
        Map<String, Object> returnMap = Maps.newHashMap();
        returnMap.put("current_dir_path", "");
        returnMap.put("current_url", Global.getQiniuDomain() + "/");
        returnMap.put("moveup_dir_path", "");
        returnMap.put("total_count", images.size());
        List<Map<String,Object>> l = Lists.newArrayList();
        returnMap.put("file_list", l);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(ListItem li : images){
        	Map<String,Object> m = Maps.newHashMap();
        	m.put("datetime", sdf.format(new Date(li.putTime)));
        	m.put("filename", li.key);
        	m.put("filesize", li.fsize);
        	m.put("filetype", li.mimeType);
        	m.put("has_file", false);
        	m.put("is_dir", false);
        	m.put("is_photo", true);
        	l.add(m);
        }
        
        String json = JSON_MAPPER.toJson(returnMap);
        logger.info("{}", json);
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().println(json);
	}

}
