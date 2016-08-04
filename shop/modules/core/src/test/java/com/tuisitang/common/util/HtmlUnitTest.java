package com.tuisitang.common.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitTest {

	public static void main(String[] args)   {
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
		HtmlPage page;
		
		String url = "https://img.alicdn.com/imgextra/i1/45949731/TB2bSOUbXXXXXa2XpXXXXXXXXXX_!!45949731.jpg";
//		String url = "http://gd2.alicdn.com/bao/uploaded/i2/TB1tcG2GpXXXXaEapXXXXXXXXXX_!!0-item_pic.jpg_50x50.jpg";
		try {
//			page = webClient.getPage("https://item.taobao.com/item.htm?spm=a1z10.3-c.w4002-2889555263.9.gC3Jy2&id=44483023651");
//			System.out.println(page.asXml());
//			HtmlDivision div = page.getHtmlElementById("description");
//			System.out.println(div.asXml());
			
			Response response = Jsoup.connect(url).ignoreContentType(true).execute();
			if (response.statusCode() == 200) {  
				FileOutputStream out = (new FileOutputStream(new java.io.File("/Users/xubin/Desktop/TB2bSOUbXXXXXa2XpXXXXXXXXXX_!!45949731.jpg")));
				out.write(response.bodyAsBytes());
				out.close();
			}
			
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		
		download(url, "/Users/xubin/Desktop/1.jpg");
	}
	
	public static void download(String url, String fileName) {
		try {
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
				byte[] data = os.toByteArray();
				os.close();
				is.close();
				
				FileOutputStream outputStream = new FileOutputStream(fileName);
				outputStream.write(data);
				outputStream.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
