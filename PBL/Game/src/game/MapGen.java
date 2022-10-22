package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGen {
	
	public int map [][];
	
	public int brickWidth;
	public int brickHeight;
	
	public MapGen(int row, int col) {
		
		// TODO Auto-generated constructor stub
		
		map =  new int[row][col];
		for(int i=0;i<map.length;i++)
		{
			for(int j = 0;j<map[0].length;j++)
			{
				map[i][j]=1;
			}
		}
		brickWidth = 640/col;
		brickHeight = 150/row;
	}

	public void draw(Graphics2D g)
	{
		for(int i=0;i<map.length;i++)
		{
			for(int j = 0;j<map[0].length;j++)
			{
				if(map[i][j]>0)
				{
					g.setColor(Color.CYAN);
					g.fillRoundRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight,15,15);
					// 80 n 50 padding for 1st brick 
					
					g.setStroke(new BasicStroke(2));
					g.setColor(Color.BLACK);
					g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
				}
			}
		}
	}
	public void setBrickValue(int value,int row ,int col)
	{
		map[row][col] = value;
	}

}