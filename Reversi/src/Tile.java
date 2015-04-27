import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Tile extends JLabel implements MouseListener
{

	int status = 0;//determines who the tile is taken for
	int horizontal = 0;//where on the x axis this 
	int vertical = 0;//where on the y axis this tile is
	boolean active = true;//this means the tile is usable
	
	public Tile(int horizontal, int vertical)	
	{
			
			 status = 0;//determines who the tile is taken for 1 for red 2 for green
			 horizontal = 0;//where on the x axis this 
			 vertical = 0;//where on the y axis this tile is
			 active = true;//this means the tile is usable(and it's not been used yet)
			
			

	}
	public void clear(Tile tile)//resets a tile
	{
		 tile.setstatus(0);//clears a tile of occupation
		 tile.setactive(true);//sets a tile to be usable
	}
	public void clearall(Tile tile[][])//clears all tiles
	{
		for (int row = 0; row<8; row++)
		{
		      for (int column = 0; column<8; column++)
			  {
		    	 setstatus(0);//clears a tile of occupation
		 		 setactive(true);//sets a tile to be usable
			  }
		}
//		board[3][3].setstatus(1);	this will be needed later probably
//		board[4][4].setstatus(1);
//		board[4][3].setstatus(2);
//		board[3][4].setstatus(2);
	}
	public void setactive(boolean condition)
	{
		 active = condition;
	}
	public boolean getactive()
	{
		return active;
	}
	public void setstatus(int condition)
	{
		 status = condition;
		 switch(status)
		 {
		 case 0: 
			 setIcon(new ImageIcon("reversi tile.png"));
			 break;
		 case 1: 
			
			 setIcon(new ImageIcon("reversi chip red.png"));
			 setactive(false);			 
			 break;
			 
		 case 2:			 
			 setIcon(new ImageIcon("reversi chip greenr.png"));
			 setactive(false);
			 break;
		 }
		 
	}
	public int getstatus()
	{
		return status;
	}
	public int gethorizontal()
	{
		return horizontal;
	}
	public int gethorizontal(Tile tile)
	{
		return horizontal;
	}
	public void sethorizontal(int xcoord)
	{
		horizontal = xcoord;
	}
	public int getvertical()
	{
		return vertical;
	}
	public void setvertical(int ycoord)
	{
		vertical = ycoord;
	}
//	@Override
//	public void mouseClicked(MouseEvent arg0) 
//	{
//	
//	  if(active == true)
//	  {
//		System.out.println("You have clicked the tile at these coordinates"+" "+horizontal/86+" ,"+vertical/86);
//		setstatus(turn);//changes status to red or green based on turn's state
//		
//		if(turn == 1)
//		{
//			turn = 2;
//		}
//		else if (turn == 2)
//		{
//			turn = 1;
//		}
//	  }
//	}
//	@Override
//	public void mouseEntered(MouseEvent arg0) 
//	{
//		setBorder(BorderFactory.createLineBorder(Color.red));
//	}
//	@Override
//	public void mouseExited(MouseEvent arg0) 
//	{
//		setBorder(BorderFactory.createLineBorder(Color.black));
//	}
//	@Override
//	public void mousePressed(MouseEvent arg0) 
//	{
//		 switch(status)
//		 {
//		 case 0: 
//			 setIcon(new ImageIcon("reversi tiledown.png"));
//			 break;
//		 case 1: 
//			 setIcon(new ImageIcon("reversi chip reddown.png"));
//			 break;
//		 case 2:
//			 setIcon(new ImageIcon("reversi chip greenrdown.png"));
//			 break;
//		 }
//		
//	}
//	@Override
//	public void mouseReleased(MouseEvent arg0) 
//	{
//		switch(status)
//		 {
//		 case 0: 
//			 setIcon(new ImageIcon("reversi tile.png"));
//			 break;
//		 case 1: 
//			 setIcon(new ImageIcon("reversi chip red.png"));
//			 break;
//		 case 2:
//			 setIcon(new ImageIcon("reversi chip greenr.png"));
//			 break;
//		 }
//		
//	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}