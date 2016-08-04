package net.jeeshop.core.oss;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.tuisitang.modules.shop.entity.QiniuOSS;
import com.tuisitang.modules.shop.utils.Global;

public class QiniuFileManagerJson {
	
	private static final Logger logger = LoggerFactory.getLogger(QiniuFileManagerJson.class);
	
	private static final JsonMapper JSON_MAPPER = JsonMapper.getInstance();

	/**
	 * {
    "current_dir_path": "",
    "current_url": "http://wedding-test2.oss-cn-hangzhou.aliyuncs.com/attached/image/",
    "file_list": [
        {
            "datetime": "2015-07-20 10:49:59",
            "filename": "1.png",
            "filesize": 39077,
            "filetype": "png",
            "has_file": false,
            "is_dir": false,
            "is_photo": true
        }
    ],
    "moveup_dir_path": "",
    "total_count": 99
}
	 * @param request
	 * @param response
	 * @return
	 */
	public String write(HttpServletRequest request, HttpServletResponse response) {
		String prefix = request.getParameter("prefix");
		if(prefix==null)
			prefix = "";
		logger.info("prefix {}", prefix);
		QiniuOSS qiniuOSS = (QiniuOSS)request.getSession().getServletContext().getAttribute(Global.QINIU_OSS_CONFIG);
		
		Mac mac = new Mac(qiniuOSS.getACCESS_KEY(), qiniuOSS.getSECRET_KEY());
        
        RSFClient client = new RSFClient(mac);
        String marker = "";
            
        List<ListItem> images = new ArrayList<ListItem>();
        ListPrefixRet ret = null;
        while (true) {
            ret = client.listPrifix(qiniuOSS.getBucketName(), prefix, marker, 10);
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
        returnMap.put("current_url", qiniuOSS.getDomain() + "/");
        returnMap.put("moveup_dir_path", "");
        returnMap.put("total_count", images.size());
        List<Map<String,Object>> l = Lists.newArrayList();
        returnMap.put("file_list", l);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(ListItem li : images){
        	/**
        	 * "datetime": "2015-07-20 10:49:59",
            "filename": "1.png",
            "filesize": 39077,
            "filetype": "png",
            "has_file": false,
            "is_dir": false,
            "is_photo": true
        	 */
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
        return json;
	}
	
}
