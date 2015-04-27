import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Battleshiptile extends JLabel 
{

	
	int horizontal = 0;//where on the x axis this 
	int vertical = 0;//where on the y axis this tile is
	boolean active = true;//determines if the tile can be clicked
	boolean hasship = false;//ship class modifies this variable
	boolean shiphit = false;//ship class modifies this variable
	boolean hasbeentargeted = false;
	String image = "genericbattleshiptile.png";
	public Battleshiptile(int horizontal, int vertical)	
	{		 			
			 horizontal = 0;//where on the x axis this 
			 vertical = 0;//where on the y axis this tile is
			 active = true;//this means the tile is usable(and it's not been used yet)
			 hasship = false;
			 shiphit = false;
			 hasbeentargeted = false;
			 image = "genericbattleshiptile.png";
			 
	}
	public void setactive(boolean condition)
	{
		 active = condition;
	}
	public boolean getactive()
	{
		return active;
	}	
	public int gethorizontal()
	{
		return horizontal;
	}
	public int gethorizontal(Battleshiptile tile)
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
	public boolean gethasship()
	{
		return hasship;
	}
	public void sethasship(boolean condition)
	{
		hasship = condition;
	}
	public boolean getshiphit()
	{
		return shiphit;
	}
	public void setshiphit(boolean condition)
	{
		shiphit = condition;
	}
	public void sethasbeentargeted(boolean condition)
	{
		hasbeentargeted = condition;
	}
	public boolean gethasbeentargeted()
	{
		return hasbeentargeted;
	}
	public void setimage(String picture)
	{
		image = picture;
	}
	public String getimage()
	{
		return image;
	}
}