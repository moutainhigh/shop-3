package com.tuisitang.common.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swetake.util.Qrcode;
import com.tuisitang.common.service.ServiceException;

/**    
 * @{#} QRCodeEncoder.java   
 *    
 * 二维码工具类
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class QRCodeEncoder {
	
	private static final Logger logger = LoggerFactory.getLogger(QRCodeEncoder.class);
	
	public static final int DEFAULT_SIZE = 200;

	/**
	 * 1. 创建QRCode
	 * 
	 * @param content
	 * @param size
	 * @return
	 */
	public static byte[] createQRCode(String content, int size) {
		byte[] result = null;
		try {
			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);
			byte[] contentBytes = content.getBytes("utf-8");
			BufferedImage bufferImgage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = bufferImgage.createGraphics();
			graphics2D.setBackground(Color.WHITE);
			graphics2D.clearRect(0, 0, size, size);
			graphics2D.setColor(Color.BLACK);
			int pixoff = 10;
			if (contentBytes.length > 0 && contentBytes.length < 120) {
				boolean[][] matrix = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix.length; j++) {
						if (matrix[j][i]) {
							graphics2D.fillRect(j * 4 + pixoff, i * 4 + pixoff, 4, 4);
						}
					}
				}
			} else {
				logger.error("Content's length not in [0, 120]");
				throw new ServiceException("Content's length not in [0, 120]");
			}
			graphics2D.dispose();
			bufferImgage.flush();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(bufferImgage, "png", output);
			result = output.toByteArray();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 2. 生成二维码(QRCode)图片
	 * 
	 * @param content
	 * @param imgPath
	 * @param size
	 */
	public static File createQRCode(String content, String imgPath, int size) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);

			System.out.println(content);
			byte[] contentBytes = content.getBytes("utf-8");

			BufferedImage bufImg = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, size, size);
			// 设定图像颜色 > BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 10;
			// 输出内容 > 二维码
			if (contentBytes.length > 0 && contentBytes.length < 120) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 4 + pixoff, i * 4 + pixoff, 4, 4);
						}
					}
				}
			} else {
				logger.error("Content's length not in [0, 120]");
				throw new ServiceException("Content's length not in [0, 120]");
			}
			gs.dispose();
			bufImg.flush();
			File imgFile = new File(imgPath);
//			if (!imgFile.exists()) {
//				imgFile.createNewFile();
//			}
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "png", imgFile);
			return imgFile;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
			throw new ServiceException(e);
		}

	}

}