package com.neuedu.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
* @ClassName: Constant
* @Description: 常量类
* @author hmx
* @date 2019年8月18日 上午8:55:48
*
*/
public class Constant {
	
	static Integer  Game_Width =  null;
	static Integer  Game_Height =  null;
	// 如何读取配置文件
	public static Properties prop = new  Properties();
	static {
		
		InputStream inStream = Constant.class.getResourceAsStream("/gameConfiguration.properties");
		try {
			prop.load(inStream);
			Game_Width =Integer.parseInt (prop.getProperty("Game_Width"));
			Game_Height =Integer.parseInt (prop.getProperty("Game_Height"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	// 定义宽度
	public static final int GAME_WIDTH = Game_Width;
	// 定义高度
	public static final int GAME_HEIGHT = Game_Height;
}
