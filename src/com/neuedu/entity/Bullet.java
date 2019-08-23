package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.constant.Constant;
import com.neuedu.util.GetImgUtil;
import com.neuedu.util.SinglePlayer;

/**
* @ClassName: Bullet
* @Description: �ӵ���
* @author hmx
* @date 2019��8��20�� ����7:06:44
*
*/
public class Bullet extends GameObj implements ActionAble{

	// ���β������ֵĶ���
	SinglePlayer singlePlayer = new SinglePlayer();
	
	
	// �����ٶ�����
	private Integer speed;
	
	// �õ��ͻ���
	public GameClient gc;
	
	// �ӵ�����
	public boolean isGood;
	
	
	public Bullet() {
		
	}
	public Bullet(int x,int y,String imgName,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.img = GetImgUtil.getImg(imgName);
		this.speed = 50;
		this.gc = gc;
		this.isGood=isGood;
	}
	@Override
	public void move() {
		if(isGood) {
			x += speed;
		}else {
			x -= speed;
		}
		
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(img, x, y,null);
		move();
		outOfBounds();
	}
	
	// �ӵ�Խ������
	public void outOfBounds() {
		
		if(x>Constant.GAME_WIDTH||x<0) {
			
			gc.bullets.remove(this);
		}
	}
	
	// ������ɵ���
	Random random = new Random();
	
	
	// �ӵ���һ��boss
	public boolean hitMonster(Plane plane) {
		Boom boom = new Boom(plane.x,plane.y,gc);
		if(this.getRec().intersects(plane.getRec())&&this.isGood!=plane.isGood) {
			
			if(plane.isGood) {
				plane.blood -=10;
				if(plane.blood == 0) {
					
				// ��������
				gc.planes.remove(plane);
				
				}
				// �Ƴ��ӵ�
				gc.bullets.remove(this);
			}else {
				singlePlayer.play("com/neuedu/sound/SOUND_PCBULLET_01.mp3");// ����
				if(plane instanceof Boss) {
					plane.blood -= 100;
					if(plane.blood<=0) {
						gc.bosss.remove(plane);
						// �Ƴ��ӵ�
						gc.bullets.remove(this);
					}
					
				}else {
					// �Ƴ����еĵ���
					gc.enemys.remove(plane);
					// �Ƴ��ӵ�
					gc.bullets.remove(this);
					// ����һ�����߳���
					if(random.nextInt(500)>400) {
						if(plane instanceof EnemyPlane ) {
							EnemyPlane enemyPlane = (EnemyPlane)plane;
							Prop prop = new Prop(plane.x+enemyPlane.imgs1[0].getWidth(null)/2,plane.y+enemyPlane.imgs1[0].getHeight(null)/2,"/prop/addblood.png");
							gc.props.add(prop);
							
						}
						
					}
				}
				
				
				
			}
			// ��ӱ�ը
			gc.booms.add(boom);
			return true;
		}
		return false;
	}
	
	// �ӵ���������
	public boolean hitMonsters(List<Plane>monsters) {
		
		if(monsters == null) {
			return false;
		}
		for(int i=0;i<monsters.size();i++) {
			if(hitMonster(monsters.get(i))) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	// ��ȡ�ӵ��ľ���
	public Rectangle getRec() {
		
		return new Rectangle(x,y,this.img.getWidth(null),this.img.getHeight(null));
	}
	
	
}
