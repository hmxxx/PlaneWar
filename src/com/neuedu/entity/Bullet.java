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
* @Description: 子弹类
* @author hmx
* @date 2019年8月20日 下午7:06:44
*
*/
public class Bullet extends GameObj implements ActionAble{

	// 单次播放音乐的对象
	SinglePlayer singlePlayer = new SinglePlayer();
	
	
	// 创建速度属性
	private Integer speed;
	
	// 拿到客户端
	public GameClient gc;
	
	// 子弹类型
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
	
	// 子弹越界销毁
	public void outOfBounds() {
		
		if(x>Constant.GAME_WIDTH||x<0) {
			
			gc.bullets.remove(this);
		}
	}
	
	// 随机生成道具
	Random random = new Random();
	
	
	// 子弹打一个boss
	public boolean hitMonster(Plane plane) {
		Boom boom = new Boom(plane.x,plane.y,gc);
		if(this.getRec().intersects(plane.getRec())&&this.isGood!=plane.isGood) {
			
			if(plane.isGood) {
				plane.blood -=10;
				if(plane.blood == 0) {
					
				// 销毁自身
				gc.planes.remove(plane);
				
				}
				// 移除子弹
				gc.bullets.remove(this);
			}else {
				singlePlayer.play("com/neuedu/sound/SOUND_PCBULLET_01.mp3");// 打中
				if(plane instanceof Boss) {
					plane.blood -= 100;
					if(plane.blood<=0) {
						gc.bosss.remove(plane);
						// 移除子弹
						gc.bullets.remove(this);
					}
					
				}else {
					// 移除打中的敌人
					gc.enemys.remove(plane);
					// 移除子弹
					gc.bullets.remove(this);
					// 生成一个道具出来
					if(random.nextInt(500)>400) {
						if(plane instanceof EnemyPlane ) {
							EnemyPlane enemyPlane = (EnemyPlane)plane;
							Prop prop = new Prop(plane.x+enemyPlane.imgs1[0].getWidth(null)/2,plane.y+enemyPlane.imgs1[0].getHeight(null)/2,"/prop/addblood.png");
							gc.props.add(prop);
							
						}
						
					}
				}
				
				
				
			}
			// 添加爆炸
			gc.booms.add(boom);
			return true;
		}
		return false;
	}
	
	// 子弹打多个怪物
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
	
	
	
	// 获取子弹的矩形
	public Rectangle getRec() {
		
		return new Rectangle(x,y,this.img.getWidth(null),this.img.getHeight(null));
	}
	
	
}
