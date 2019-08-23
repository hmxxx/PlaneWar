package com.neuedu.entity;

import java.awt.Graphics;

import com.neuedu.action.ActionAble;
import com.neuedu.util.GetImgUtil;

/**
* @ClassName: BackGround
* @Description:背景类
* @author hmx
* @date 2019年8月20日 下午8:14:54
*
*/
public class BackGround extends GameObj implements ActionAble{

	
	private Integer speed;

	public BackGround() {
		
	}
	
	public BackGround(int x,int y,String imgName) {
		
		this.x = x;
		this.y = y;
		this.img = GetImgUtil.getImg(imgName);
		this.speed = 2;
	}
	
	@Override
	public void move() {
		
		x -= speed;
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y,null);
		move();
	}

	
}
