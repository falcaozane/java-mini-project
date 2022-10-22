package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 36;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX= 310;
	
	private int ballposX = 450;
	private int ballposY = 250;
	private int ballXdir = -2;
	private int ballYdir = -3;
	
	private MapGen map;
	
	// gameplay initialization
	
	public GamePlay()
	{
		map = new MapGen(3,12);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	public void paint(Graphics g)
	{
		g.setColor(Color.black);
		//g.drawImage(img,0,0,null);
		g.fillRect(1, 1, 795, 695);
		
		map.draw((Graphics2D)g);
		
		g.setColor(Color.yellow);
		// position of borders
		g.fillRect(0, 0, 3, 795);
		g.fillRect(0, 0, 795, 3);
		g.fillRect(795, 0, 3, 795);
		
		// attributes of pedal
		g.setColor(Color.MAGENTA);
	  //g.fillRect(playerX, 550, 100, 8);
		g.fillRoundRect(playerX, 550, 100, 8, 10, 10);
		
		// attributes of ball
		g.setColor(Color.ORANGE);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		g.setColor(Color.green);
		g.setFont(new Font ("Helvetica",Font.BOLD,25));
		
		g.drawString("" +score, 740, 40); // location for score box
		
		if(totalBricks<=0)
		{
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			
			g.setColor(Color.green);
			g.setFont(new Font ("Helvetica",Font.BOLD,35));
			g.drawString("You Won, Score: "+score, 200, 300); 
			g.setFont(new Font ("Helvetica",Font.BOLD,30));
			g.drawString("Press Enter to Restart.", 230, 350); //to restart the game
		}
		
		if(ballposY > 560) // 570 bottom border
		{
			play = false;
			ballXdir = -1;
			ballYdir = -2;
			
			g.setColor(Color.red);
			g.setFont(new Font ("Helvetica",Font.BOLD,30));
			g.drawString("Game over, Score: "+score,180, 300); 
			
			g.setFont(new Font ("Helvetica",Font.BOLD,20));
			g.drawString("Press Enter to Restart.",180, 350);
			
		}
		
		g.dispose(); // to release the  system resources used 
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		timer.start();
		if(play)
		{
			if(new Rectangle(ballposX, ballposY, 20, 30).intersects(new Rectangle(playerX, 560,100,8)))
			{
				ballYdir=-ballYdir;
			}
			
			for(int i=0;i<map.map.length;i++)
			{
				for(int j = 0;j<map.map[0].length;j++)
				{
					if(map.map[i][j]>0)
					{
						int brickX = j*map.brickWidth+80;
						int brickY = i*map.brickHeight+80;
						int brickWidth = map.brickWidth; // accessing the values from MapGen class
						int brickHeight  = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{
							map.setBrickValue(0, i, j);
							totalBricks--; // brick disappears
							score+=5;
							
							if(ballposX+10 <=brickRect.x || ballposX+1 >= brickRect.x +brickRect.width)
							{
								ballXdir = -ballXdir; // reversing the ball direction
							}
							else
							{
								ballYdir = -ballYdir;
							}
						}
					}
				}
			}
				
			ballposX += ballXdir;
			ballposY -= ballYdir;
			if(ballposX <0) {
				ballXdir=-ballXdir;
				
			}
			if(ballposY <0) 
			{
				ballYdir = -ballYdir;
			}
			if(ballposX > 760) {
				ballXdir=-ballXdir;
				
			}
				
		}
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}
		
		

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()== KeyEvent.VK_RIGHT)
		{
			if(playerX >= 695) // to not exceed the boundary
			playerX = 695;
			else
			{
				moveRight();
			}
		}
		if(arg0.getKeyCode()== KeyEvent.VK_LEFT)
		{
			if(playerX < 10) // to not exceed the boundary
				{
					playerX = 10;
				}
			else
			{
				moveLeft();
			}
		}
		
		if(arg0.getKeyCode()== KeyEvent.VK_ENTER)
		{
			if(!play)
			{
				play =  true;
				ballposX = 360;
				ballposY = 250;
				ballXdir = -2;
				ballYdir = -3;
				playerX = 310;
				score = 0;
				totalBricks = 36;
				map = new MapGen(3,12);
				repaint();
			}
		}
		
		
	}
	public void moveRight()
	{
		play = true;
		playerX += 15;
	}
	
	public void moveLeft()
	{
		play = true;
		playerX -= 15;
	}	
		

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void playMusic(String path) {

		try {

			File musicPath = new File(path);
			if(musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				System.out.println("Cannot find the Audio File");
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			/*
			 * The printStackTrace() method of Java. lang.
			 *  Throwable class used to print this Throwable along with other details like class name and line number where the exception occurred means its backtrace. 
			 *  This method prints a stack trace for this Throwable object on the standard error output stream.
			 */
		}
	}
	
	

}