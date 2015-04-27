import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;


public class Ship extends Battleshiptile 
{

	private Object owner;
	private int horz;
	private int vert;
	private int len;
	private String dir;
	
	public Ship(int horizontalorigin, int verticalorigin, int length, String direction, Battleshiptile[][] o) //places a ship at a horizontal and vertical value on a board, the direction it goes from origin is determined by direction, the board the ship class is placing on is in the argument
	{
		
		super(horizontalorigin, verticalorigin);
		horz = horizontalorigin;
		vert = verticalorigin;
		len = length;
		dir = direction;
		owner = o;
		if(direction.equals("up") && verticalorigin-1 >= 0)
		{		
			for(int x = verticalorigin; x> verticalorigin - length; x--)
			{
				
				if(x == verticalorigin)
				{
					
					o[horizontalorigin][x].sethasship(true);		
					o[horizontalorigin][x].setIcon(new ImageIcon("shipenddown.png"));
					o[horizontalorigin][x].setimage("shipenddown.png");
					
				}
				
				else if(x != (verticalorigin - length)+1 && x != verticalorigin)
				{
					o[horizontalorigin][x].sethasship(true);
					o[horizontalorigin][x].setIcon(new ImageIcon("Shipvert.png"));
					o[horizontalorigin][x].setimage("Shipvert.png");
				}
				else if(x == (verticalorigin - length)+1)
				{
					o[horizontalorigin][x].sethasship(true);				
					o[horizontalorigin][x].setIcon(new ImageIcon("Shipendup.png"));
					o[horizontalorigin][x].setimage("Shipendup.png");
				}	
			}
		}
		if(direction.equals("left") && horizontalorigin-1 >= 0)
		{
			for(int x = horizontalorigin; x> horizontalorigin - length; x--)//Aka(int x = o.gethorizontal()/51; x>= o.gethorizontal()/51 - length; x--)
			{	
				
				if(x == horizontalorigin)
				{
					
					o[x][verticalorigin].sethasship(true);				
					o[x][verticalorigin].setIcon(new ImageIcon("shipendright.png"));
					o[x][verticalorigin].setimage("shipendright.png");
					
				}			
				else if(x != (horizontalorigin - length)+1 && x != horizontalorigin)
				{
					
					o[x][verticalorigin].sethasship(true);			
					o[x][verticalorigin].setIcon(new ImageIcon("Shiphorz.png"));
					o[x][verticalorigin].setimage("Shiphorz.png");
				}
				else if(x == (horizontalorigin - length)+1)
				{
					
					o[x][verticalorigin].sethasship(true);				
					o[x][verticalorigin].setIcon(new ImageIcon("Shipendleft.png"));
					o[x][verticalorigin].setimage("Shipendleft.png");
				}
		   }
		}
		if(direction.equals("right")&& horizontalorigin+1 <= 9)
		{	
			for(int x = horizontalorigin; x<horizontalorigin+length; x++)
			{
				if(x == horizontalorigin)
				{
					
					o[x][verticalorigin].sethasship(true);		
					o[x][verticalorigin].setIcon(new ImageIcon("shipendleft.png"));
					o[x][verticalorigin].setimage("shipendleft.png");
				}
				
				else if(x != (horizontalorigin + length)-1 && x != horizontalorigin)
				{
					
					o[x][verticalorigin].sethasship(true);		
					o[x][verticalorigin].setIcon(new ImageIcon("Shiphorz.png"));
					o[x][verticalorigin].setimage("Shiphorz.png");
				}
				else if(x == (horizontalorigin + length)-1)
				{
					o[x][verticalorigin].sethasship(true);				
					o[x][verticalorigin].setIcon(new ImageIcon("Shipendright.png"));
					o[x][verticalorigin].setimage("Shipendright.png");
				}	
			}
		}
		if(direction.equals("down") && verticalorigin+1 <= 9)
		{
			for(int x = verticalorigin; x<verticalorigin+length; x++)
			{
				if(x == verticalorigin)
				{
					o[horizontalorigin][x].sethasship(true);			
					o[horizontalorigin][x].setIcon(new ImageIcon("shipendup.png"));
					o[horizontalorigin][x].setimage("Shipendup.png");
				}
				
				else if(x != (verticalorigin + length)-1 && x != verticalorigin)
				{
					o[horizontalorigin][x].sethasship(true);	
					o[horizontalorigin][x].setIcon(new ImageIcon("Shipvert.png"));
					o[horizontalorigin][x].setimage("Shipvert.png");
				}
				else if(x == (verticalorigin + length)-1)
				{
					o[horizontalorigin][x].sethasship(true);		
					o[horizontalorigin][x].setIcon(new ImageIcon("Shipenddown.png"));
					o[horizontalorigin][x].setimage("Shipenddown.png");
				}	
			}
		}
	}	
	public int gethorizontalorigin(Ship ship)
	{
		return horz;
	}
	public int getverticalorigin(Ship ship)
	{
		return vert;
	}
	public int getlength(Ship ship)
	{
		return len;
	}
	public String getdirection(Ship ship)
	{
		return dir;
	}
	
}