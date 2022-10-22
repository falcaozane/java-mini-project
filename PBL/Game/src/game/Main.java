package game;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;


import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.sound.sampled.*;
import javax.swing.*;


public class Main {
	
	public static void main(String[] args)
	{
		JFrame obj = new JFrame();
		GamePlay gamePlay = new GamePlay();
		obj.setBounds(10, 10, 800, 600); // main layout
		obj.setTitle("Brick Breaker");
		obj.setResizable(false); // interface size will remain constant
		String path = "src/game/Music.wav";
		gamePlay.playMusic(path);
		
	    //ImageIcon img = Toolkit.getDefaultToolkit().createImage("galaxybg.jpg");
		
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
		
	}

	
}