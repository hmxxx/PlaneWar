package com.neuedu.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
* @ClassName: GetImgUtil
* @Description: 获取图片工具类
* @author hmx
* @date 2019年8月19日 上午11:08:22
*
*/
public class GetImgUtil {

	// URL
	
	private static final File URL = null;

	// 获取图片方法
	public static Image getImg(String imgName){
		// 反射
		URL resource = GetImgUtil.class.getClassLoader().getResource("com/neuedu/img"+imgName);
		BufferedImage bufferedImage = null;
		
		try {
			bufferedImage = ImageIO.read(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bufferedImage;
	}
	
}
