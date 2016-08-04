package com.tuisitang.common.servlet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;

public class AppValidateCodeServlet extends ValidateCodeServlet {

	protected void createImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getParameter("token");
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		SpyMemcachedClient memcachedClient = ctx.getBean(SpyMemcachedClient.class);
		
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		
		/*
		 * 得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
		 */
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		if (StringUtils.isNumeric(width) && StringUtils.isNumeric(height)) {
			w = NumberUtils.toInt(width);
			h = NumberUtils.toInt(height);
		}
		
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		/*
		 * 生成背景
		 */
		createBackground(g);

		/*
		 * 生成字符
		 */
		String s = createCharacter(g);
		request.getSession().setAttribute(VALIDATE_CODE, s);
		memcachedClient.set(MemcachedObjectType.VerifyCode.getPrefix() + token, MemcachedObjectType.VerifyCode.getExpiredTime(), s);

		g.dispose();
		OutputStream out = response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		out.close();
	}
}
