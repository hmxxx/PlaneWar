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
* @Description: ��ȡͼƬ������
* @author hmx
* @date 2019��8��19�� ����11:08:22
*
*/
public class GetImgUtil {

	// URL
	
	private static final File URL = null;

	// ��ȡͼƬ����
	public static Image getImg(String imgName){
		// ����
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
