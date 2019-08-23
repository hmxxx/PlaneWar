package com.neuedu.client;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.constant.Constant;
import com.neuedu.entity.BackGround;
import com.neuedu.entity.Boom;
import com.neuedu.entity.Boss;
import com.neuedu.entity.Bullet;
import com.neuedu.entity.EnemyPlane;
import com.neuedu.entity.Plane;
import com.neuedu.entity.Prop;
import com.neuedu.util.GetImgUtil;
import com.neuedu.util.SoundPlayer;

/**
* @ClassName: GameClient
* @Description: 游戏客户端
* @author hmx
* @date 2019年8月18日 上午8:51:14
* @version 1.0
*
*/
public class GameClient extends Frame{

	// 创建一个plane出来
//	Plane plane = new Plane(100,200,"/03.png",this,true);
	
	// 创建一个我方角色集合
	public List<Plane> planes=new ArrayList<>();
	
	// 创建道具集合
	public List<Prop> props=new ArrayList<>();
	
	// 创建一个子弹集合
	public List<Bullet> bullets = new ArrayList<>();
	
	// 创建一个背景图
	BackGround backImg = new BackGround(0,0,"/bg/bg01.png");
	
	// 创建一个爆炸集合
	public List<Boom> booms = new ArrayList<>();
	
	// 创建敌方一号
	EnemyPlane enemy1 = new EnemyPlane(650,50,1,this,false);
	EnemyPlane enemy2 = new EnemyPlane(650,400,1,this,false);
	
	// 创建敌方集合
	public List<Plane> enemys = new ArrayList<>();
	
	// 创建一个boss集合
	public List<Plane> bosss = new ArrayList<>();
	
	// 解决图片闪烁问题
	public void update(Graphics g) {
		Image backImg = createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		Graphics backg = backImg.getGraphics();
		Color color = backg.getColor();
		backg.setColor(Color.WHITE);
		backg.fillRect(0, 0, Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		backg.setColor(color);
		paint(backg);
		g.drawImage(backImg, 0, 0, null);
				
	}
	
	Plane plane = null;
	
	// 生成客户端窗口的方法
	public void launchFrame() {
		
		SoundPlayer soundPlayer = new SoundPlayer("com/neuedu/sound/bgm_bullet.mp3");
		soundPlayer.start();
		
		// 设置窗口大小
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		// 设置标题
		this.setTitle("飞机大战");
		// 设置是否能够显示
		this.setVisible(true);
		// 禁止最大化
		this.setResizable(false);
		// 窗口居中
		this.setLocationRelativeTo(null); 
		// 关闭窗口 添加监听器
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		plane = new Plane(100,200,"/03.png",this,true);
		// 往我方容器中添加自己
		planes.add(plane);
//		// 添加鼠标监听
//		this.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				System.out.println("你点了一下鼠标");
//			}
//		});
		// 键盘监听
		this.addKeyListener(new KeyAdapter() {
			// 键盘摁下的情况下
			public void keyPressed(KeyEvent e) {
				plane.keyPressed(e);	
			}
			public void keyReleased(KeyEvent e) {
				plane.keyReleased(e);
			}
		});
		// 启动线程
		new PaintThread().start();
		
		
		
		// 往敌方容器中添加敌人
		EnemyPlane enemy1 = new EnemyPlane(650,50,1,this,false);
		EnemyPlane enemy2 = new EnemyPlane(650,400,1,this,false);
		EnemyPlane enemy3 = new EnemyPlane(950,50,1,this,false);
		EnemyPlane enemy4 = new EnemyPlane(950,400,1,this,false);
		
		enemys.add(enemy1);
		enemys.add(enemy2);
		enemys.add(enemy3);
		enemys.add(enemy4);
		
		// 添加boss
		bosss.add(new Boss(1600,350,this,false));
	}
	// 重写paint方法
	public void paint(Graphics g) {
		backImg.draw(g);
		for(int i=0;i<planes.size();i++) {
			Plane plane2 = planes.get(i);
			plane2.draw(g);
			plane2.containItems(props);
			
		}
		
		// 增强循环不能做任何操作
		for(int i=0;i<bullets.size();i++) {
			Bullet bullet = bullets.get(i);
			bullet.draw(g);
			bullet.hitMonsters(enemys);
			bullet.hitMonsters(planes);
			bullet.hitMonsters(bosss);
		}
		
		g.drawString("当前子弹数量："+bullets.size(), 10, 40);
		g.drawString("当前敌机的数量："+enemys.size(), 10, 70);
		g.drawString("当前爆炸的数量"+booms.size(), 10, 100);
		g.drawString("当前我方的数量"+planes.size(), 10, 130);
		g.drawString("当前道具的数量"+props.size(), 10, 160);
		// 循环画敌方
		for(int i=0;i<enemys.size();i++) {
			enemys.get(i).draw(g);
		}
		
		// 循环爆炸
		for(int i=0;i<booms.size();i++) {
			if(booms.get(i).isLive()==true) {
				booms.get(i).draw(g);
			}
			booms.get(i).draw(g);
		}
		
		// 循环画道具
		for(int i=0;i<props.size();i++) {
			
			props.get(i).draw(g);
		}
		
		if(enemys.size()==0) {
			// 循环boss集合
			for(int i=0;i<bosss.size();i++) {
				
				bosss.get(i).draw(g);
			}
		}
		
	}
	// 内部类
	class PaintThread extends Thread{
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}
