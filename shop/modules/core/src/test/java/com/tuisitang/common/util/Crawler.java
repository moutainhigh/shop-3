package com.tuisitang.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class Crawler {
	
	private static final Logger logger = LoggerFactory.getLogger(Crawler.class);

	public static void main(String[] args) throws Exception {
		List<String> l = Lists.newArrayList();
		
		List<String> list = Lists.newArrayList();
		{
			list.add("http://meigaojiaju.tmall.com/i/asynSearch.htm?_ksTS=1436433171944_130&callback=jsonp131&mid=w-4999505417-0&wid=4999505417&path=/search.htm&&search=y&spm=a1z10.3-b.w4011-4999505417.696.FBUm0p&pageNo=1&tsearch=y");
			list.add("http://meigaojiaju.tmall.com/i/asynSearch.htm?_ksTS=1436434233289_119&callback=jsonp120&mid=w-4999505417-0&wid=4999505417&path=/search.htm&&search=y&spm=a1z10.3-b.w4011-4999505417.694.j8TINA&pageNo=2&tsearch=y");
			list.add("http://meigaojiaju.tmall.com/i/asynSearch.htm?_ksTS=1436434345565_119&callback=jsonp120&mid=w-4999505417-0&wid=4999505417&path=/search.htm&&search=y&spm=a1z10.3-b.w4011-4999505417.697.xWmN2k&pageNo=3&tsearch=y");
			list.add("http://meigaojiaju.tmall.com/i/asynSearch.htm?_ksTS=1436434500831_365&callback=jsonp366&mid=w-4999505417-0&wid=4999505417&path=/search.htm&&search=y&spm=a1z10.3-b.w4011-4999505417.698.DcizBi&pageNo=4&tsearch=y");
		}
		for (String url : list) {
			String body = Jsoup.connect(url).ignoreContentType(true).execute()
					.body();
			body = body.substring(body.indexOf("\"") + 1,
					body.lastIndexOf("\""));
			body = body.replace("\\", "");
			Document doc = Jsoup.parse(body);
			Elements elements = doc.getElementsByClass("item");
			for (int i = 0; i < elements.size(); i++) {
				Element e = elements.get(i);
				String s = e.getElementsByTag("a").get(0).attr("href");
				if (!l.contains("http:" + s)) {
					l.add("http:" + s);
				}
			}
		}
		logger.info("{}", l.size());
		{
			for (String url : l) {
				logger.info("{}", url);
				String path = "/Users/xubin/Desktop/Wedding/";
				Document doc = Jsoup.parse(new URL(url), 60 * 1000);
				path = path + doc.getElementById("J_DetailMeta").getElementsByTag("h1").get(0).text() + "/";
				logger.info("path {}", path);
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				Elements elements = doc.getElementById("J_UlThumb").getElementsByTag("img");
				int size = elements.size();
				for (int i = 0; i < size; i++) {
					try {
						Element e = elements.get(i);
						String src = "http:" + e.attr("src");
						src = src.replace("jpg_60x60q90.", "");
						String suffix = src.substring(src.lastIndexOf("/"));
						Response response = Jsoup.connect(src)
								.ignoreContentType(true).execute();
						FileOutputStream out = (new FileOutputStream(
								new java.io.File(path + suffix)));
						out.write(response.bodyAsBytes());
						out.close();
					} catch (Throwable e) {

					}
				}
				
//				elements = doc.getElementById("description").getElementsByTag("img");
//				logger.info("!!!{}", doc.getElementById("description").html());
//				for (int i = 0; i < elements.size(); i++) {
//					Element e = elements.get(i);
//					String src = "http:" + e.attr("src");
//					logger.info("!!!!!src {}", src);
//				}
			}
		}
	}

}
