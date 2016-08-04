package com.tuisitang.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Lists;
import com.gson.util.HttpKit;

public class CrawlerTaobaoWzhzhq {
	
	private static final Logger logger = LoggerFactory.getLogger(CrawlerTaobaoWzhzhq.class);
	private static WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);;

	public static void main(String[] args) throws Exception {
		List<String> l = Lists.newArrayList();
		
		List<String> list = Lists.newArrayList();
		{
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443455859329_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.114.tKvcBf&pageNo=1");
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443452282009_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.112.csXx9R&pageNo=2");
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443455930534_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.115.tKvcBf&pageNo=3");
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443455951750_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.116.tKvcBf&pageNo=4");
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443455971640_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.117.tKvcBf&pageNo=5");
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443455995141_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.118.tKvcBf&pageNo=6");
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443456015659_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.119.tKvcBf&pageNo=7");
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443456034538_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.120.tKvcBf&pageNo=8");
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443456052427_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.121.tKvcBf&pageNo=9");
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443456068825_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.120.BwnhfY&pageNo=10");
			list.add("https://wzhzhq.taobao.com/i/asynSearch.htm?_ksTS=1443456085032_201&callback=jsonp202&mid=w-2889555263-0&wid=2889555263&path=/search.htm&search=y&spm=a1z10.3-c.w4002-2889555263.121.BwnhfY&pageNo=11");
		}
		for (String url : list) {
			String body = HttpKit.get(url, "gbk");
			body = body.substring(body.indexOf("(") + 2, body.lastIndexOf(")") - 1);
			body = body.replace("\\", "");
			Document doc = Jsoup.parse(body);
			Elements elements = doc.getElementsByClass("photo");
			for (int i = 0; i < elements.size(); i++) {
				Element e = elements.get(i);
				String s = e.getElementsByTag("a").get(0).attr("href");
				if (!l.contains("http:" + s)) {
					l.add("http:" + s);
				}
			}
		}
		
		for (String url : l) {
			logger.info("{}", url);
			String path = "/Users/xubin/Desktop/hz/";
			Document doc = Jsoup.parse(new URL(url), 60 * 1000);
			path = path + doc.getElementById("J_Title").getElementsByTag("h3").get(0).text() + "/";
			logger.info("path {}", path);
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			{
				Elements elements = doc.getElementById("J_UlThumb").getElementsByTag("img");
				int size = elements.size();
				for (int i = 0; i < size; i++) {
					try {
						Element e = elements.get(i);
						String src = "http:" + e.attr("data-src");
						src = src.replace("jpg_50x50.", "");
						logger.info("src {}", src);
						String suffix = src.substring(src.lastIndexOf("/"));
						download(src, path + suffix);
//						Response response = Jsoup.connect(src)
//								.ignoreContentType(true).execute();
//						FileOutputStream out = (new FileOutputStream(
//								new java.io.File(path + suffix)));
//						out.write(response.bodyAsBytes());
//						out.close();
					} catch (Throwable e) {
						
					}
				}
			}
			{
				path = path + "description/";
				file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				
				HtmlPage page;
				try {
					page = webClient.getPage(url);
					Thread.sleep(1000 * 10);
					String description = page.getHtmlElementById("description").asXml();
					Elements elements = Jsoup.parse(description).getElementsByTag("img");
					int size = elements.size();
					for (int i = 0; i < size; i++) {
						try {
							Element e = elements.get(i);
							String src = e.attr("src");
							logger.info("src {}", src);
							if (!src.startsWith("http:") && !src.startsWith("https:")) {
								src = "http:" + src;
							}
							String suffix = src.substring(src.lastIndexOf("/"));
							download(src, path + suffix);
//							Response response = Jsoup.connect(src).ignoreContentType(true).execute();
//							FileOutputStream out = (new FileOutputStream(
//									new java.io.File(path + suffix)));
//							out.write(response.bodyAsBytes());
//							out.close();
						} catch (Throwable e) {
							
						}
					}
				} catch (FailingHttpStatusCodeException | IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	public static void download(String url, String fileName) {
		try {
			if (new File(fileName).exists()) {
				return;
			}
			byte[] data = null;
			if (url.startsWith("https")) {
				HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
				conn.setRequestMethod("GET");
				conn.setReadTimeout(6 * 10000);
				if (conn.getResponseCode() == 200) {
					InputStream is = conn.getInputStream();

					ByteArrayOutputStream os = new ByteArrayOutputStream();
					byte[] buffer = new byte[2048];
					int len = -1;
					while ((len = is.read(buffer)) != -1) {
						os.write(buffer, 0, len);
					}
					data = os.toByteArray();
					os.close();
					is.close();
				}
			} else {
				HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
				conn.setRequestMethod("GET");
				conn.setReadTimeout(6 * 10000);
				if (conn.getResponseCode() == 200) {
					InputStream is = conn.getInputStream();

					ByteArrayOutputStream os = new ByteArrayOutputStream();
					byte[] buffer = new byte[2048];
					int len = -1;
					while ((len = is.read(buffer)) != -1) {
						os.write(buffer, 0, len);
					}
					data = os.toByteArray();
					os.close();
					is.close();
				}
			}
			if (data != null) {
				FileOutputStream outputStream = new FileOutputStream(fileName);
				outputStream.write(data);
				outputStream.close();
			}
			Thread.sleep(1000 * 1);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Thread.sleep(1000 * 1);
			} catch (InterruptedException ex) {
			}
		}
	}
	
}
