package com.tuisitang.common.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.util.FileCopyUtils;

import com.swetake.util.Qrcode;

public class QRCodeEncoderTest {
	
	public static final int DEFAULT_SIZE = 200;

	public static byte[] createQRCode(String content) {
		byte[] result = null;
		try {
			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);

			byte[] contentBytes = content.getBytes("utf-8");

			BufferedImage bufferImgage = new BufferedImage(DEFAULT_SIZE, DEFAULT_SIZE,
					BufferedImage.TYPE_INT_RGB);

			Graphics2D graphics2D = bufferImgage.createGraphics();
			graphics2D.setBackground(Color.WHITE);
			graphics2D.clearRect(0, 0, DEFAULT_SIZE, DEFAULT_SIZE);
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
				//
			}
			graphics2D.dispose();

			bufferImgage.flush();

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(bufferImgage, "png", output);
			result = output.toByteArray();
			output.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 * @param imgPath
	 */
	public static void encoderQRCode(String content, String imgPath) {
		try {

			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);

			System.out.println(content);
			byte[] contentBytes = content.getBytes("utf-8");

			BufferedImage bufImg = new BufferedImage(140, 140,
					BufferedImage.TYPE_INT_RGB);

			Graphics2D gs = bufImg.createGraphics();

			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 140, 140);

			// 设定图像颜色 > BLACK
			gs.setColor(Color.BLACK);

			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容 > 二维码
//			if (contentBytes.length > 0 && contentBytes.length < 120) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
//			} else {
//				System.err.println("QRCode content bytes length = "
//						+ contentBytes.length + " not in [ 0,120 ]. ");
//			}

			gs.dispose();
			bufImg.flush();

			File imgFile = new File(imgPath);

			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "png", imgFile);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {
//
//		String content = "Hello 大大、小小,welcome to QRCode!"
//				+ "\nMyblog[ http://sjsky.iteye.com]"
//				+ "\nEMail[ sjsky007@gmail.com]" + "\nTwitter[@suncto]";
		
		String content = "http://sjsky.iteye.com";

		byte[] b = QRCodeEncoderTest.createQRCode(content);

		System.out.println(b);
		File file = new File("/Users/xubin/Desktop/Michael_QRCode1.png");

		// 生成二维码QRCode图片
		FileCopyUtils.copy(b, file);
		System.out.println("encoder QRcode success");

		QRCodeEncoderTest.encoderQRCode(content, "/Users/xubin/Desktop/Michael_QRCode2.png");
	}
}