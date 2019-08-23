package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.constant.Constant;
import com.neuedu.util.GetImgUtil;
import com.neuedu.util.SinglePlayer;

/**
* @ClassName: Plane
* @Description: 创建一个飞机类
* @author hmx
* @date 2019年8月20日 上午11:04:53
*
*/
public class Plane extends GameObj implements ActionAble{

	SinglePlayer play = new SinglePlayer();
	
	// 速度
	private int speed;
	// 方向布尔变量
	private boolean left,up,right,down;

	// 客户端拿过来
	public GameClient gc;
	// 判断是否是我军
	public boolean isGood;
	
	// 添加飞机子弹等级变量
	public int BulletLevel = 1;
	
	// 添加血值
	public int blood;
	
	public Plane() {
		
	}
	public Plane(int x,int y,String imgName,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.img = GetImgUtil.getImg(imgName);
		this.speed = 20;
		this.gc = gc;
		this.isGood = isGood;
		this.blood = 100;
	}
	// 移动的方法
	@Override
	public void move() {
		if(left) {
			x -= speed;
		}
		if(up) {
			y -= speed;
		}
		if(right) {
			x += speed;
		}
		if(down) {
			y += speed;
		}
		outOfBound();
	}
	// 画一个飞机
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y,null);
		move();
		g.drawString("当前血量："+blood,10,200);
	}
	
	// 处理飞机的边界问题
	public void outOfBound() {
		if(y<=30) {
			y =20;
		}
		if(x<=0) {
			x = 0;
		}
		if(x>=Constant.GAME_WIDTH-img.getWidth(null)) {
			x = Constant.GAME_WIDTH-img.getWidth(null);
		}
		if(y>=Constant.GAME_HEIGHT-img.getHeight(null)) {
			y = Constant.GAME_HEIGHT-img.getHeight(null);
		}
	}
	// 键盘按下
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;
		case KeyEvent.VK_F:
			fire();
			break;
			
		default:
			break;
		}
	}
	// 键盘释放
	public void keyReleased(KeyEvent e) {
		
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_S:
			down =false;
			break;
		default:
			break;
		}
	}
	
	// 我方飞机开火
	public void fire() {
		play.play("com/neuedu/sound/SOUND_PCBULLET_01.mp3");
		Bullet b = new Bullet(x+this.img.getWidth(null),y+this.img.getHeight(null)/2-20,"/bullet/bullet_0"+BulletLevel+".png",gc,true);
		gc.bullets.add(b);
	}
	
	// 获取矩形
	public Rectangle getRec() {
		
		return new Rectangle(x,y,this.img.getWidth(null),this.img.getHeight(null));
	}
	
	// 检测我方角色碰撞到道具
	public void containItem(Prop prop) {
		if(this.getRec().intersects(prop.getRect())) {
			gc.props.remove(prop);// 移除道具
			if(BulletLevel>4) {
				BulletLevel = 5;
				return;
			}
			// 更改子弹等级
			this.BulletLevel +=1;
		}
	}
	
	// 检测我方角色碰撞到多个道具
	public void containItems(List<Prop> props) {
		if(props==null) {
			return;
		}else {
			for(int i=0;i<props.size();i++) {
				containItem(props.get(i));
			}
		}
	}
	
	
	
	
	
	
	
}
