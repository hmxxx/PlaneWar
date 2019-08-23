package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.util.GetImgUtil;

/**
* @ClassName: EnemyPlane
* @Description:�з��ɻ���
* @author hmx
* @date 2019��8��20�� ����8:38:53
*
*/
public class EnemyPlane extends Plane implements ActionAble {

	private Integer enemyType;
	
	private Integer speed;
	private GameClient gc;
	
	
	public static Image[] imgs1 = {
		GetImgUtil.getImg("/boss/BOSS_01.png"),
		GetImgUtil.getImg("/boss/BOSS_02.png"),	
		GetImgUtil.getImg("/boss/BOSS_03.png"),	
		GetImgUtil.getImg("/boss/BOSS_04.png"),	
		GetImgUtil.getImg("/boss/BOSS_05.png")	
	};
	
	
	public EnemyPlane() {
		
	}
	public EnemyPlane(int x,int y,int enemyType,GameClient gc,boolean isGood) {
		
		this.x = x;
		this.y = y;
		this.enemyType = enemyType;
		this.speed = 1;
		this.gc = gc;
		this.isGood = isGood;
	}
	@Override
	public void move() {
		
		x -= speed;
	}
	int count = 0;
	@Override
	public void draw(Graphics g) {
		if(count>4) {
			count = 0;
		}
		g.drawImage(imgs1[count++], x, y,null);
		move();
		if(random.nextInt(500)>490) {
			fire();
		}
		
		
	}
	
	// �����
	Random random = new Random();
	
	// �о�����
	public void fire() {
		Bullet bullet = new Bullet(x,y+imgs1[0].getHeight(null)/2+20,"/bullet/enemyplane_03_bullet.png",gc,false);
		gc.bullets.add(bullet);	
	}
	
	// ��ȡ�з�boss����
	public Rectangle getRec() {
		
		return new Rectangle(x,y,this.imgs1[0].getWidth(null),this.imgs1[0].getHeight(null));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
