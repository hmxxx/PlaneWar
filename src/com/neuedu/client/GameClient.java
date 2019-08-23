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
* @Description: ��Ϸ�ͻ���
* @author hmx
* @date 2019��8��18�� ����8:51:14
* @version 1.0
*
*/
public class GameClient extends Frame{

	// ����һ��plane����
//	Plane plane = new Plane(100,200,"/03.png",this,true);
	
	// ����һ���ҷ���ɫ����
	public List<Plane> planes=new ArrayList<>();
	
	// �������߼���
	public List<Prop> props=new ArrayList<>();
	
	// ����һ���ӵ�����
	public List<Bullet> bullets = new ArrayList<>();
	
	// ����һ������ͼ
	BackGround backImg = new BackGround(0,0,"/bg/bg01.png");
	
	// ����һ����ը����
	public List<Boom> booms = new ArrayList<>();
	
	// �����з�һ��
	EnemyPlane enemy1 = new EnemyPlane(650,50,1,this,false);
	EnemyPlane enemy2 = new EnemyPlane(650,400,1,this,false);
	
	// �����з�����
	public List<Plane> enemys = new ArrayList<>();
	
	// ����һ��boss����
	public List<Plane> bosss = new ArrayList<>();
	
	// ���ͼƬ��˸����
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
	
	// ���ɿͻ��˴��ڵķ���
	public void launchFrame() {
		
		SoundPlayer soundPlayer = new SoundPlayer("com/neuedu/sound/bgm_bullet.mp3");
		soundPlayer.start();
		
		// ���ô��ڴ�С
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		// ���ñ���
		this.setTitle("�ɻ���ս");
		// �����Ƿ��ܹ���ʾ
		this.setVisible(true);
		// ��ֹ���
		this.setResizable(false);
		// ���ھ���
		this.setLocationRelativeTo(null); 
		// �رմ��� ��Ӽ�����
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		plane = new Plane(100,200,"/03.png",this,true);
		// ���ҷ�����������Լ�
		planes.add(plane);
//		// ���������
//		this.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				System.out.println("�����һ�����");
//			}
//		});
		// ���̼���
		this.addKeyListener(new KeyAdapter() {
			// �������µ������
			public void keyPressed(KeyEvent e) {
				plane.keyPressed(e);	
			}
			public void keyReleased(KeyEvent e) {
				plane.keyReleased(e);
			}
		});
		// �����߳�
		new PaintThread().start();
		
		
		
		// ���з���������ӵ���
		EnemyPlane enemy1 = new EnemyPlane(650,50,1,this,false);
		EnemyPlane enemy2 = new EnemyPlane(650,400,1,this,false);
		EnemyPlane enemy3 = new EnemyPlane(950,50,1,this,false);
		EnemyPlane enemy4 = new EnemyPlane(950,400,1,this,false);
		
		enemys.add(enemy1);
		enemys.add(enemy2);
		enemys.add(enemy3);
		enemys.add(enemy4);
		
		// ���boss
		bosss.add(new Boss(1600,350,this,false));
	}
	// ��дpaint����
	public void paint(Graphics g) {
		backImg.draw(g);
		for(int i=0;i<planes.size();i++) {
			Plane plane2 = planes.get(i);
			plane2.draw(g);
			plane2.containItems(props);
			
		}
		
		// ��ǿѭ���������κβ���
		for(int i=0;i<bullets.size();i++) {
			Bullet bullet = bullets.get(i);
			bullet.draw(g);
			bullet.hitMonsters(enemys);
			bullet.hitMonsters(planes);
			bullet.hitMonsters(bosss);
		}
		
		g.drawString("��ǰ�ӵ�������"+bullets.size(), 10, 40);
		g.drawString("��ǰ�л���������"+enemys.size(), 10, 70);
		g.drawString("��ǰ��ը������"+booms.size(), 10, 100);
		g.drawString("��ǰ�ҷ�������"+planes.size(), 10, 130);
		g.drawString("��ǰ���ߵ�����"+props.size(), 10, 160);
		// ѭ�����з�
		for(int i=0;i<enemys.size();i++) {
			enemys.get(i).draw(g);
		}
		
		// ѭ����ը
		for(int i=0;i<booms.size();i++) {
			if(booms.get(i).isLive()==true) {
				booms.get(i).draw(g);
			}
			booms.get(i).draw(g);
		}
		
		// ѭ��������
		for(int i=0;i<props.size();i++) {
			
			props.get(i).draw(g);
		}
		
		if(enemys.size()==0) {
			// ѭ��boss����
			for(int i=0;i<bosss.size();i++) {
				
				bosss.get(i).draw(g);
			}
		}
		
	}
	// �ڲ���
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
