package com.neuedu.util;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
* @ClassName: SinglePlayer
* @Description: ���β�����
* @author hmx
* @date 2019��8��21�� ����8:54:52
*
*/
public class SinglePlayer extends Thread {

	private String mp3Name;
	
	public SinglePlayer() {
		// TODO Auto-generated constructor stub
	}
	
	public SinglePlayer(String mp3Name) {
		this.mp3Name = mp3Name;
	}
	public void play(String mp3Name) {
		SinglePlayer singlePlayer = new SinglePlayer(mp3Name);
		singlePlayer.start();
	}
	
	
	@Override
	public void run() {
		InputStream resourceAsStream = SoundPlayer.class.getClassLoader().getResourceAsStream(mp3Name);
		try {
			AdvancedPlayer advancedPlayer = new AdvancedPlayer(resourceAsStream);
			advancedPlayer.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
}
