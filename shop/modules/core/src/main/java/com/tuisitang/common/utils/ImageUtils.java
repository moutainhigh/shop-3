package com.tuisitang.common.utils;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**    
 * @{#} ImageUtils.java   
 * 
 * 图片工具
 *
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
public class ImageUtils {

	public static final int DEFAULT_IMAGE_SIZE = 240;

	/**
	 * 创建缩略图
	 * 
	 * @param srcFile
	 * @param destFile
	 */
	public static void createThumbnailImage(File srcFile, File destFile, int size) {
		try {
			BufferedImage bis = ImageIO.read(srcFile);

			int width = bis.getWidth();
			int height = bis.getHeight();
			int w = size;
			int h = (w * height) / width;
			if (h > size) {
				h = size;
				w = (h * width) / height;
			}
			double sx = (double) w / width;
			double sy = (double) h / height;
			AffineTransform transform = new AffineTransform();
			transform.setToScale(sx, sy);
			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
			ato.filter(bis, bid);
			ImageIO.write(bid, "png", destFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 创建缩略图
	 * 
	 * @param srcFile
	 * @param destFile
	 */
	public static void createThumbnailImage(File srcFile, File destFile) {
		createThumbnailImage(srcFile, destFile, DEFAULT_IMAGE_SIZE);
	}
	
	/**
     * Decode string to image
     * @param imageString The string to decode
     * @return decoded image
     */
    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Encode image to string
     * @param image The image to encode
     * @param type jpeg, bmp, ...
     * @return encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static void main (String args[]) throws IOException {
        /* Test image to string and string to image start */
        BufferedImage img = ImageIO.read(new File("/Users/xubin/Pictures/_CSC0213.JPG"));
        BufferedImage newImg;
        String imgstr = encodeToString(img, "png");
        System.out.println(imgstr);
        newImg = decodeToImage(imgstr);
        ImageIO.write(newImg, "jpg", new File("/Users/xubin/Documents/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/hf-web/userfiles/23/2015/04/18/1.jpg"));
    }
}
