import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
//extend JLabel, use Mouselistener, Tile should be an object. //due Dec 16, no paper
public class Reversi extends JFrame implements ActionListener, MouseListener
{//TODO WHEN TWO METHODS EXECUTE SIMULTANEOUSLY CLICKED TILE IS COUNTED TWICE IN SCOREKEEPING(FIX), ADD SUPPORT FOR AI

private Tile board[][] = new Tile[9][9];
private Random rand = new Random();
private ArrayList<Tile> legalmoves = new ArrayList<Tile>();
private ArrayList<Tile> legalmovesofequalvalue = new ArrayList<Tile>();
private int AI = 0;//if this is 1, the AI will move after you
private int testredcontrolled = 0; //used by AI to test how many squares red will have
private int testgreencontrolled = 0; // used by AI to test how many squares green will have
private JButton AION = new JButton();//activates AI
private Tile AITESTboard[][] = new Tile [9][9];//mirror of actual board AI uses for testing purposes
private JLabel turnlabel = new JLabel();//this is a label
private String Pturn= "red";//this changes to green if its greens turn
private int redcontrolled = 2; //tiles red controls 
private int greencontrolled = 2; //tiles green controls
private int highestgreencontrolled = 0;
private int turn = 1; //red is 1 green is 2
private JLabel Redscore = new JLabel();
private JLabel Greenscore = new JLabel();
public Tile Piece = new Tile(0, 0);
int horizontal = 0;
int vertical = 0;
int firstcheckmethod = 0;//used to make sure the tile clicked is not counted more than once if two methods are executed.
private boolean onemethodworks = false;//necessary to keep turns from changing without a proper move
public Reversi()
{

	Container contentPane = getContentPane();
	contentPane.setLayout(null);
	
	
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setTitle("Reversi");
	setSize(800, 768);
	setResizable(false);
	setLocation(10, 20);
	for (int row = 0; row<8; row++)
	{
	      for (int column = 0; column<8; column++)
		  {
	    	  int horz = column*86;//where it is in terms of x could be defined as column
	    	  int vert = row*86;//where it is in terms of y could be defined as row
	    	  board[row][column] = new Tile(row, column);
	    	  board[row][column].sethorizontal(horz);
	    	  board[row][column].setvertical(vert);
	    	  board[row][column].setBounds(horz,vert, 85, 85);
	    	  board[row][column].setBorder(BorderFactory.createLineBorder(Color.black));
	    	  board[row][column].setIcon(new ImageIcon("reversi tile.png"));
	    	  board[row][column].addMouseListener(this);
	    	  contentPane.add(board[row][column]);
	    	  
		   }
	}
	for (int row = 0; row<8; row++)
	{
	      for (int column = 0; column<8; column++)
		  {
	    	  int horz = column*86;//where it is in terms of x could be defined as column
	    	  int vert = row*86;//where it is in terms of y could be defined as row
	    	  AITESTboard[row][column] = new Tile(row, column);
	    	  AITESTboard[row][column].sethorizontal(horz);
	    	  AITESTboard[row][column].setvertical(vert);
	    	  AITESTboard[row][column].setBounds(horz,vert, 85, 85);	    	   	  
		   }
	}
	board[3][3].setstatus(1);	
	board[4][4].setstatus(1);
	board[4][3].setstatus(2);
	board[3][4].setstatus(2);
	
	AION = new JButton("Turn on AI");
	AION.setBounds(0, 690, 100, 50);
	AION.addActionListener(this);
	contentPane.add(AION);
	
	turnlabel = new JLabel("Turn:"+" "+Pturn);
	turnlabel.setBounds(700,100, 80, 30);
	contentPane.add(turnlabel);

	Redscore = new JLabel("Red has"+" "+redcontrolled);
	Redscore.setBounds(250, 687, 100, 50);
	contentPane.add(Redscore);
	
	Greenscore = new JLabel("Green has"+" "+greencontrolled);
	Greenscore.setBounds(500, 687, 100, 50);
	contentPane.add(Greenscore);

}

public static void main(String[] args)
{
		// this spawns the window that holds everything
			Reversi frame= new Reversi();
			frame.setVisible(true);
	 	
}

public void scorekeeping()
{
int	redcounter = 0;
int	greencounter = 0;
	for (int x = 0; x<=7; x++)
	{
		for (int y = 0; y<=7; y++)
		{
			if (board[y][x].getactive() == false)//if something is on the tile
			{
				if (board[y][x].getstatus() == 1)//red tile
				{
					redcounter++;
					redcontrolled = redcounter;
				}
				else if(board[y][x].getstatus() == 2)//greentile
				{
					greencounter++;
					greencontrolled = greencounter;
				}
			}
		}
	} 
}
public void AItestscorekeeping()
{
int	redcounter = 0;
int	greencounter = 0;

	for (int y = 0; y<=7; y++)
	{
		for (int x = 0; x<=7; x++)
		{
			if (AITESTboard[y][x].getactive() == false)//if something is on the tile
			{
				if (AITESTboard[y][x].getstatus() == 1)//red tile
				{
					redcounter++;
					testredcontrolled = redcounter;
				}
				else if(AITESTboard[y][x].getstatus() == 2)//greentile
				{
					greencounter++;
					testgreencontrolled = greencounter;
				}
			}
		}
	}
AIBoardsync();
}
public void AImove()
{
AIassess();
AItest();
AIact();


}


public void AIassess()
{

	for (int y = 0; y<=7; y++)
	{
		for (int x = 0; x<=7; x++)
		{
			
			AITESTboard[x][y].setstatus(board[y][x].getstatus());	
			
		}
	}
	for(int x = 0; x<=7; x++)
	{
		for (int y = 0; y<=7; y++)
		{
			if(AITESTboard[x][y].getvertical()/86!= 0)
			{
				AIassessnorth(AITESTboard[x][y].gethorizontal()/86, AITESTboard[x][y].getvertical()/86, 1, 0);
			}
			if(AITESTboard[x][y].getvertical()/86!=7)
			{
				AIassesssouth(AITESTboard[x][y].gethorizontal()/86, AITESTboard[x][y].getvertical()/86, 1, 0);
			}
			if(AITESTboard[x][y].gethorizontal()/86!=7)
			{
				AIassesseast(AITESTboard[x][y].gethorizontal()/86, AITESTboard[x][y].getvertical()/86, 1, 0);
			}
			if(AITESTboard[x][y].gethorizontal()/86!=0)
			{
				AIassesswest(AITESTboard[x][y].gethorizontal()/86, AITESTboard[x][y].getvertical()/86, 1, 0);
			}
			if(AITESTboard[x][y].gethorizontal()/86!=7 && AITESTboard[x][y].getvertical()/86!=7)
			{
				AIassesssoutheast(AITESTboard[x][y].gethorizontal()/86, AITESTboard[x][y].getvertical()/86, 1, 0);
			}
			if(AITESTboard[x][y].gethorizontal()/86!=0 && AITESTboard[x][y].getvertical()/86!=7)
			{
				AIassesssouthwest(AITESTboard[x][y].gethorizontal()/86, AITESTboard[x][y].getvertical()/86, 1, 0);
			}
			if(AITESTboard[x][y].gethorizontal()/86!=0 && AITESTboard[x][y].getvertical()/86!=0)
			{
				AIassessnorthwest(AITESTboard[x][y].gethorizontal()/86, AITESTboard[x][y].getvertical()/86, 1, 0);
			}
			if(AITESTboard[x][y].gethorizontal()/86!=7 && AITESTboard[x][y].getvertical()/86!= 0)
			{
				AIassessnortheast(AITESTboard[x][y].gethorizontal()/86, AITESTboard[x][y].getvertical()/86, 1, 0);
			}
			if(onemethodworks == true)
			{
				board[x][y].setBorder(BorderFactory.createLineBorder(Color.yellow));
				legalmoves.add(board[x][y]);
				System.out.println("AI thinks "+board[x][y].gethorizontal()/86+","+board[x][y].getvertical()/86+"Is a legal move");
				onemethodworks = false;
			}
			
		}	
		
	}
}
public void AIBoardsync()
{
	for (int y = 0; y<=7; y++)
	{
		for (int q= 0; q<=7; q++)
		{
			//TODO find way to reset score to the way it was before tested move
			AITESTboard[q][y].setstatus(board[y][q].getstatus());	

			
		}
	}	
}
public void AItest()
{
	
	int highestgreencontrolled = 0;//whichever move has the highest result of theoretical greencontrolleds should be the move AI Makes
	for (int x = 0; x<legalmoves.size(); x++)//this should go through all the legal moves on the AI board, one at a time, and it picks the one that gives it the most tiles
	{
		
		testgreencontrolled = greencontrolled;
		if(legalmoves.get(x).getvertical()/86!= 0)
		{
			AItestingnorth(legalmoves.get(x).gethorizontal()/86, legalmoves.get(x).getvertical()/86, 1, 0);
		}
		if(legalmoves.get(x).getvertical()/86!=7)
		{
			AItestingsouth(legalmoves.get(x).gethorizontal()/86, legalmoves.get(x).getvertical()/86, 1, 0);
		}
		if(legalmoves.get(x).gethorizontal()/86!=0)
		{
			AItestingwest(legalmoves.get(x).gethorizontal()/86, legalmoves.get(x).getvertical()/86, 1, 0);
		}
		if(legalmoves.get(x).gethorizontal()/86!=7)
		{
			AItestingeast(legalmoves.get(x).gethorizontal()/86, legalmoves.get(x).getvertical()/86, 1, 0);
		}
		if(legalmoves.get(x).gethorizontal()/86!=7 && legalmoves.get(x).getvertical()/86!=7)
		{
			AItestingsoutheast(legalmoves.get(x).gethorizontal()/86, legalmoves.get(x).getvertical()/86, 1, 0);
		}
		if(legalmoves.get(x).gethorizontal()/86!=0 && legalmoves.get(x).getvertical()/86!=7)
		{
			AItestingsouthwest(legalmoves.get(x).gethorizontal()/86, legalmoves.get(x).getvertical()/86, 1, 0);
		}
		if(legalmoves.get(x).gethorizontal()/86!=0 && legalmoves.get(x).getvertical()/86!=0)
		{
			AItestingnorthwest(legalmoves.get(x).gethorizontal()/86, legalmoves.get(x).getvertical()/86, 1, 0);
		}
		if(legalmoves.get(x).gethorizontal()/86!=7 && legalmoves.get(x).getvertical()/86!= 0)
		{
			AItestingnortheast(legalmoves.get(x).gethorizontal()/86, legalmoves.get(x).getvertical()/86, 1, 0);
		}		
		AItestscorekeeping();
		if(legalmoves.get(x).gethorizontal() == 0 && legalmoves.get(x).getvertical() == 0)//experimental attempt to make sides and corners more valuable to the AI
		{
			System.out.println("Corner move found");
			testgreencontrolled = testgreencontrolled*3;
		}
		else if(legalmoves.get(x).gethorizontal() == 0 || legalmoves.get(x).getvertical() == 0)
		{
			System.out.println("Side move found");
			testgreencontrolled = testgreencontrolled*2;
		}
		if(testgreencontrolled == highestgreencontrolled)//my attempt at making the AI's move selection more random, need to figure out why a line is so important first
		{
			System.out.println("Eqivalent value move found");
			
			legalmovesofequalvalue.add(legalmoves.get(x));
			Piece = legalmovesofequalvalue.get(rand.nextInt(legalmovesofequalvalue.size()));
			System.out.println("Selected at random, piece is"+Piece.gethorizontal()/86+","+Piece.getvertical()/86);
		}
		System.out.println("Testgreencontrolled vs highestgreencontrolled"+testgreencontrolled+">"+highestgreencontrolled);
		if(testgreencontrolled >highestgreencontrolled)
		{
	
			
			highestgreencontrolled = testgreencontrolled;
			System.out.println("Target coordinates were ="+ legalmoves.get(x).gethorizontal()/86+","+legalmoves.get(x).getvertical()/86);
			Piece = legalmoves.get(x);
			System.out.println("Target coordinates are ="+ legalmoves.get(x).gethorizontal()/86+","+legalmoves.get(x).getvertical()/86);
			testgreencontrolled = greencontrolled;
			legalmovesofequalvalue.clear();
			legalmovesofequalvalue.add(Piece);
		}
		AIBoardsync();
		testredcontrolled = redcontrolled;
		testgreencontrolled = greencontrolled;
	}

	
	
	
	
}
public void AIact()
{


	System.out.println("Executing AIact");
	System.out.println("AI has selected the tile at these coordinates"+" "+Piece.gethorizontal()/86+" ,"+Piece.getvertical()/86);
	System.out.println("AIselected tile is"+Piece.getactive());
	if(Piece.getactive() == true)		
    {
		System.out.println("at act AI sees this");
		AIvision();
	if(Piece.getvertical()/86!= 0)//if north and south are active at the same time south farks north up, need to fix
	{
		checkingnorth(Piece.gethorizontal()/86, Piece.getvertical()/86, 1, 0);
	
	}
	if(Piece.getvertical()/86!=7)
	{
		checkingsouth(Piece.gethorizontal()/86, Piece.getvertical()/86, 1, 0);

	}
	if(Piece.gethorizontal()/86!=7)//if west and east are active at the same time, east farks west up, need to fix this
	{
		checkingeast(Piece.gethorizontal()/86, Piece.getvertical()/86, 1, 0);

	}
	if(Piece.gethorizontal()/86!= 0)
	{
		checkingwest(Piece.gethorizontal()/86, Piece.getvertical()/86, 1, 0);

	}	
	if(Piece.gethorizontal()/86!=7 && Piece.getvertical()/86!=7)
	{
		checkingsoutheast(Piece.gethorizontal()/86, Piece.getvertical()/86, 1, 0);

	}
	if(Piece.gethorizontal()/86!=0 && Piece.getvertical()/86!=7)
	{
		checkingsouthwest(Piece.gethorizontal()/86, Piece.getvertical()/86, 1, 0);

	}
	if(Piece.gethorizontal()/86!=0 && Piece.getvertical()/86!=0)
	{
		checkingnorthwest(Piece.gethorizontal()/86, Piece.getvertical()/86, 1, 0);

	}
	if(Piece.gethorizontal()/86!=7 && Piece.getvertical()/86!= 0)
	{
		checkingnortheast(Piece.gethorizontal()/86, Piece.getvertical()/86, 1, 0);

	}
			Piece.setBorder(BorderFactory.createLineBorder(Color.blue));
			scorekeeping();
			turn = 1;
			Pturn = "red";
			turnlabel.setText("Turn:"+" "+Pturn);
			Redscore.setText("Red has"+" "+redcontrolled);
			Greenscore.setText("Green has"+" "+greencontrolled);
		
	onemethodworks = false;
	firstcheckmethod = 0;
	legalmoves.clear();
	highestgreencontrolled = 0;
	Piece.setactive(false);
	System.out.println("after act AI sees this");
	AIvision();
	System.out.println("Tile "+Piece.gethorizontal()/86+" ,"+Piece.getvertical()/86+"set to false");
	Piece = new Tile(0, 0);

    }
	
	
	
  
}
public void AIvision()
{
	for (int x = 0; x<= 7; x++)
	{
		for(int y = 0; y<=7; y++)
		{
			System.out.print(board[x][y].getstatus());
		}
		System.out.println();
	}
}
public void checkingnorth(int horizontal, int vertical, int iterations, int confirmed)
{	

		if(turn == board[vertical-iterations][horizontal].getstatus()&& iterations>1 && confirmed == iterations-1)
	    { 		

			onemethodworks = true;
				for (int x = vertical; x >= vertical - iterations; x--) 
				{		
					
					board[x][horizontal].setstatus(turn);
				}
	    }
	    if ((board[vertical-iterations][horizontal].getvertical() > 0)&& (turn != board[vertical-iterations][horizontal].getstatus()))//COUNTERINTUITIVE WARNING, THIS IS ME TRYING TO METAPHORICALLY REVERSE THE POLARITY TO SEE IF IT WILL WORK
	    { 
	    	if(board[vertical-iterations][horizontal].getstatus()!= turn && board[vertical-iterations][horizontal].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
	    	iterations++;
	    	checkingnorth(horizontal,vertical, iterations, confirmed);
	    }
}
public void checkingsouth(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
		if(turn == board[vertical+iterations][horizontal].getstatus()&& iterations >1 && confirmed == iterations-1 )
		{
			
			onemethodworks = true;
			
			for(int x = vertical; x<=vertical+iterations; x++)
			{
				
				board[x][horizontal].setstatus(turn);
		    }
		}	
		if (board[vertical+iterations][horizontal].getvertical()/86 < 7 && turn != board[vertical+iterations][horizontal].getstatus())
		{
			if(board[vertical+iterations][horizontal].getstatus()!= turn && board[vertical+iterations][horizontal].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;	
		checkingsouth(horizontal, vertical, iterations, confirmed);	
		}	
}
public void checkingwest(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
		if(turn == board[vertical][horizontal-iterations].getstatus() && iterations >1 && confirmed == iterations-1)
		{
			
			onemethodworks = true;
			for(int x = horizontal; x>=horizontal-iterations; x--)
			{
				
				

				board[vertical][x].setstatus(turn);//my wording is strange, but for some reason I put board[row]is vertical and board[column]is horizontal, THIS WORKS
				
	
			}		
		}
		if ((board[vertical][horizontal-iterations].gethorizontal() > 0) && (turn != board[vertical][horizontal-iterations].getstatus()))
		{
			if(board[vertical][horizontal-iterations].getstatus()!= turn && board[vertical][horizontal-iterations].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;
		checkingwest(horizontal, vertical, iterations, confirmed);
		}
		
	
}
public void checkingeast(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
		if(turn == board[vertical][horizontal+iterations].getstatus()&& iterations >1 && confirmed == iterations -1)
		{
			onemethodworks = true;
			firstcheckmethod = 1;
			for(int x = horizontal; x<=horizontal+iterations; x++)
			{
				

				board[vertical][x].setstatus(turn);//my wording is strange, but for some reason I put board[row]is vertical and board[column]is horizontal, THIS WORKS
			}	
		}
		if (board[vertical][horizontal+iterations].gethorizontal()/86 < 7 && turn != board[vertical][horizontal+iterations].getstatus())
		{
			if(board[vertical][horizontal+iterations].getstatus()!= turn && board[vertical][horizontal+iterations].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;
		checkingeast(horizontal, vertical, iterations, confirmed);	
		}
}
public void checkingsoutheast(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
	int x = horizontal;
	int y = vertical;
	//confirmed needs to be present to make sure there are actually green tiles in between red ones or vice versa, used in a conditional if equals iterations-1
		if(turn == board[vertical+iterations][horizontal+iterations].getstatus()&&iterations > 1 &&confirmed == iterations-1)
		{
			
			onemethodworks = true;
			while(x< x+iterations && y< y+iterations && x+iterations!= 7 && y+iterations!= 7)
			{
			  
				
				board[y][x].setstatus(turn);//my wording is strange, but for some reason I put board[row]is vertical and board[column]is horizontal, THIS WORKS
				y++;//if not here, then the clicked tile would not change
				x++;			  
			 }	
		}
		if (board[vertical+iterations][horizontal+iterations].gethorizontal()/86 < 7 && board[vertical+iterations][horizontal+iterations].getvertical()/86 < 7&& turn != board[vertical+iterations][horizontal+iterations].getstatus())
		{
		if(board[vertical+iterations][horizontal+iterations].getstatus()!=turn && board[vertical+iterations][horizontal+iterations].getstatus()!=0)
		{
			confirmed++;//necessary to make sure there are green between red or vice versa
		}
		iterations++;
		checkingsoutheast(horizontal, vertical, iterations, confirmed);	
		}
}
public void checkingsouthwest(int horizontal, int vertical, int iterations, int confirmed)
{
	
	int x = horizontal;
	int y = vertical;
		if(turn == board[vertical+iterations][horizontal-iterations].getstatus() && iterations>1 && confirmed == iterations -1)
		{
			System.out.println("Executing Southwest");
			onemethodworks = true;
			while(x> x-iterations && y<y+iterations && x-iterations!= 0 && y+iterations!= 7)
			{
				
			
				board[y][x].setstatus(turn);//my wording is strange, but for some reason I put board[row]is vertical and board[column]is horizontal, THIS WORKS
				y++;//if not here, then the clicked tile would not change
				x--;
			}
		}
		if (board[vertical+iterations][horizontal-iterations].gethorizontal()/86 > 0 && board[vertical+iterations][horizontal-iterations].getvertical()/86 < 7&& turn != board[vertical+iterations][horizontal-iterations].getstatus())
		{	
			if(board[vertical+iterations][horizontal-iterations].getstatus()!= turn && board[vertical+iterations][horizontal-iterations].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;
		checkingsouthwest(horizontal, vertical, iterations, confirmed);	
		}


}
public void checkingnorthwest(int horizontal, int vertical, int iterations, int confirmed)
{
	int x = horizontal;
	int y = vertical;
	if(turn == board[vertical-iterations][horizontal-iterations].getstatus() && iterations>1 && confirmed == iterations -1)
	{
		System.out.println("executing northwest");
		onemethodworks = true;
		while(x> x-iterations && y>y-iterations && x-iterations!= 0 && y-iterations!= 0 )
		{
			
			board[y][x].setstatus(turn);
			y--;
			x--;

		}
	}
	if (board[vertical-iterations][horizontal-iterations].gethorizontal()/86 > 0 && board[vertical-iterations][horizontal-iterations].getvertical()/86 > 0&& turn != board[vertical-iterations][horizontal-iterations].getstatus())
	{	
		if(board[vertical-iterations][horizontal-iterations].getstatus()!= turn && board[vertical-iterations][horizontal-iterations].getstatus()!=0)
    	{
    		confirmed++;
    	}
	iterations++;
	checkingnorthwest(horizontal, vertical, iterations, confirmed);	
	}
}
public void checkingnortheast(int horizontal, int vertical, int iterations, int confirmed)
{

	int x = horizontal;
	int y = vertical;
	if(turn == board[vertical-iterations][horizontal+iterations].getstatus() && iterations>1 && confirmed == iterations -1)
	{

		onemethodworks = true;
		while(x< x+iterations && y>y-iterations && x+iterations!= 7 && y-iterations!= 0)
		{
			
			board[y][x].setstatus(turn);//my wording is strange, but for some reason I put board[row]is vertical and board[column]is horizontal, THIS WORKS
			y--;//if not here, then the clicked tile would not change
			x++;
		}
	}
	if (board[vertical-iterations][horizontal+iterations].gethorizontal()/86 < 7 && board[vertical-iterations][horizontal+iterations].getvertical()/86 > 0&& turn != board[vertical-iterations][horizontal+iterations].getstatus())
	{	
		if(board[vertical-iterations][horizontal+iterations].getstatus()!= turn && board[vertical-iterations][horizontal+iterations].getstatus()!=0)
    	{
    		confirmed++;
    	}
	iterations++;
	checkingnortheast(horizontal, vertical, iterations, confirmed);	
	}
}
public void AIassessnorth(int horizontal, int vertical, int iterations, int confirmed)
{	
	
		if(turn == board[vertical-iterations][horizontal].getstatus()&& iterations>1 && confirmed == iterations-1 && board[vertical][horizontal].getstatus()==0)
	    { 		
			
			onemethodworks = true;
			System.out.println("AI thinks"+board[vertical][horizontal].gethorizontal()/86+","+board[vertical][horizontal].getvertical()/86+"Works north");
	    }
	    if ((board[vertical-iterations][horizontal].getvertical() > 0)&& (turn != board[vertical-iterations][horizontal].getstatus()))//COUNTERINTUITIVE WARNING, THIS IS ME TRYING TO METAPHORICALLY REVERSE THE POLARITY TO SEE IF IT WILL WORK
	    { 
	    	if(board[vertical-iterations][horizontal].getstatus()!= turn && board[vertical-iterations][horizontal].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
	    	iterations++;
	    	AIassessnorth(horizontal,vertical, iterations, confirmed);
	    }
}
public void AIassesssouth(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
		if(turn == board[vertical+iterations][horizontal].getstatus()&& iterations >1 && confirmed == iterations-1 && board[vertical][horizontal].getstatus()==0 )
		{
			
			onemethodworks = true;
			System.out.println("AI thinks"+board[vertical][horizontal].gethorizontal()/86+","+board[vertical][horizontal].getvertical()/86+"Works south");
		}	
		if (board[vertical+iterations][horizontal].getvertical()/86 < 7 && turn != board[vertical+iterations][horizontal].getstatus())
		{
			if(board[vertical+iterations][horizontal].getstatus()!= turn && board[vertical+iterations][horizontal].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;	
		AIassesssouth(horizontal, vertical, iterations, confirmed);	
		}	
}
public void AIassesswest(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
		if(turn == board[vertical][horizontal-iterations].getstatus() && iterations >1 && confirmed == iterations-1 && board[vertical][horizontal].getstatus()==0)
		{
			
			onemethodworks = true;
			System.out.println("AI thinks"+board[vertical][horizontal].gethorizontal()/86+","+board[vertical][horizontal].getvertical()/86+"Works west");
		}
		if ((board[vertical][horizontal-iterations].gethorizontal() > 0) && (turn != board[vertical][horizontal-iterations].getstatus()))
		{
			if(board[vertical][horizontal-iterations].getstatus()!= turn && board[vertical][horizontal-iterations].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;
		AIassesswest(horizontal, vertical, iterations, confirmed);
		}
		
	
}
public void AIassesseast(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
		if(turn == board[vertical][horizontal+iterations].getstatus()&& iterations >1 && confirmed == iterations -1 && board[vertical][horizontal].getstatus()==0)
		{
			onemethodworks = true;
			System.out.println("AI thinks"+board[vertical][horizontal].gethorizontal()/86+","+board[vertical][horizontal].getvertical()/86+"Works east");
		}
		if (board[vertical][horizontal+iterations].gethorizontal()/86 < 7 && turn != board[vertical][horizontal+iterations].getstatus())
		{
			if(board[vertical][horizontal+iterations].getstatus()!= turn && board[vertical][horizontal+iterations].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;
		AIassesseast(horizontal, vertical, iterations, confirmed);	
		}
}
public void AIassesssoutheast(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
	
	//confirmed needs to be present to make sure there are actually green tiles in between red ones or vice versa, used in a conditional if equals iterations-1
		if(turn == board[vertical+iterations][horizontal+iterations].getstatus()&&iterations > 1 &&confirmed == iterations-1 && board[vertical][horizontal].getstatus()==0)
		{
			
			onemethodworks = true;
			System.out.println("AI thinks"+board[vertical][horizontal].gethorizontal()/86+","+board[vertical][horizontal].getvertical()/86+"Works southeast");
		}
		if (board[vertical+iterations][horizontal+iterations].gethorizontal()/86 < 7 && board[vertical+iterations][horizontal+iterations].getvertical()/86 < 7&& turn != board[vertical+iterations][horizontal+iterations].getstatus())
		{
		if(board[vertical+iterations][horizontal+iterations].getstatus()!=turn && board[vertical+iterations][horizontal+iterations].getstatus()!=0)
		{
			confirmed++;//necessary to make sure there are green between red or vice versa
		}
		iterations++;
		AIassesssoutheast(horizontal, vertical, iterations, confirmed);	
		}
}
public void AIassesssouthwest(int horizontal, int vertical, int iterations, int confirmed)
{
	

		if(turn == board[vertical+iterations][horizontal-iterations].getstatus() && iterations>1 && confirmed == iterations -1 && board[vertical][horizontal].getstatus()==0)
		{
			
			onemethodworks = true;
			System.out.println("AI thinks"+board[vertical][horizontal].gethorizontal()/86+","+board[vertical][horizontal].getvertical()/86+"Works southwest");
		}
		if (board[vertical+iterations][horizontal-iterations].gethorizontal()/86 > 0 && board[vertical+iterations][horizontal-iterations].getvertical()/86 < 7&& turn != board[vertical+iterations][horizontal-iterations].getstatus())
		{	
			if(board[vertical+iterations][horizontal-iterations].getstatus()!= turn && board[vertical+iterations][horizontal-iterations].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;
		AIassesssouthwest(horizontal, vertical, iterations, confirmed);	
		}


}
public void AIassessnorthwest(int horizontal, int vertical, int iterations, int confirmed)
{
	
	if(turn == board[vertical-iterations][horizontal-iterations].getstatus() && iterations>1 && confirmed == iterations -1 && board[vertical][horizontal].getstatus()==0)
	{
		
		onemethodworks = true;
		System.out.println("AI thinks"+board[vertical][horizontal].gethorizontal()/86+","+board[vertical][horizontal].getvertical()/86+"Works northwest");
	}
	if (board[vertical-iterations][horizontal-iterations].gethorizontal()/86 > 0 && board[vertical-iterations][horizontal-iterations].getvertical()/86 > 0&& turn != board[vertical-iterations][horizontal-iterations].getstatus())
	{	
		if(board[vertical-iterations][horizontal-iterations].getstatus()!= turn && board[vertical-iterations][horizontal-iterations].getstatus()!=0)
    	{
    		confirmed++;
    	}
	iterations++;
	AIassessnorthwest(horizontal, vertical, iterations, confirmed);	
	}
}
public void AIassessnortheast(int horizontal, int vertical, int iterations, int confirmed)
{

	
	if(turn == board[vertical-iterations][horizontal+iterations].getstatus() && iterations>1 && confirmed == iterations -1 && board[vertical][horizontal].getstatus()==0)
	{
		
		onemethodworks = true;
		System.out.println("AI thinks"+board[vertical][horizontal].gethorizontal()/86+","+board[vertical][horizontal].getvertical()/86+"Works northeast");
	}
	if (board[vertical-iterations][horizontal+iterations].gethorizontal()/86 < 7 && board[vertical-iterations][horizontal+iterations].getvertical()/86 > 0&& turn != board[vertical-iterations][horizontal+iterations].getstatus())
	{	
		if(board[vertical-iterations][horizontal+iterations].getstatus()!= turn && board[vertical-iterations][horizontal+iterations].getstatus()!=0)
    	{
    		confirmed++;
    	}
	iterations++;
	AIassessnortheast(horizontal, vertical, iterations, confirmed);	
	}
}
public void AItestingnorth(int horizontal, int vertical, int iterations, int confirmed)
{	

		if(turn == board[vertical-iterations][horizontal].getstatus()&& iterations>1 && confirmed == iterations-1)
	    { 		

			onemethodworks = true;
				for (int x = vertical; x >= vertical - iterations; x--) 
				{		
					
					AITESTboard[x][horizontal].setstatus(turn);
				}
	    }
	    if ((board[vertical-iterations][horizontal].getvertical() > 0)&& (turn != board[vertical-iterations][horizontal].getstatus()))//COUNTERINTUITIVE WARNING, THIS IS ME TRYING TO METAPHORICALLY REVERSE THE POLARITY TO SEE IF IT WILL WORK
	    { 
	    	if(board[vertical-iterations][horizontal].getstatus()!= turn && board[vertical-iterations][horizontal].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
	    	iterations++;
	    	AItestingnorth(horizontal,vertical, iterations, confirmed);
	    }
}
public void AItestingsouth(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
		
		if(turn == board[vertical+iterations][horizontal].getstatus()&& iterations >1 && confirmed == iterations-1 )
		{

			onemethodworks = true;
			
			for(int x = vertical; x<=vertical+iterations; x++)
			{
				
				AITESTboard[x][horizontal].setstatus(turn);
		    }
		}	
		if (board[vertical+iterations][horizontal].getvertical()/86 < 7 && turn != board[vertical+iterations][horizontal].getstatus())
		{
			if(board[vertical+iterations][horizontal].getstatus()!= turn && board[vertical+iterations][horizontal].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;	
		AItestingsouth(horizontal, vertical, iterations, confirmed);
		}	
}
public void AItestingwest(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
		if(turn == board[vertical][horizontal-iterations].getstatus() && iterations >1 && confirmed == iterations-1)
		{

			onemethodworks = true;
			for(int x = horizontal; x>=horizontal-iterations; x--)
			{
				
				AITESTboard[vertical][x].setstatus(turn);//my wording is strange, but for some reason I put board[row]is vertical and board[column]is horizontal, THIS WORKS
			}		
		}
		if ((board[vertical][horizontal-iterations].gethorizontal() > 0) && (turn != board[vertical][horizontal-iterations].getstatus()))
		{
			if(board[vertical][horizontal-iterations].getstatus()!= turn && board[vertical][horizontal-iterations].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;
		AItestingwest(horizontal, vertical, iterations, confirmed);
		}
		
	
}
public void AItestingeast(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
		if(turn == board[vertical][horizontal+iterations].getstatus()&& iterations >1 && confirmed == iterations -1)
		{

			onemethodworks = true;
			firstcheckmethod = 1;
			for(int x = horizontal; x<=horizontal+iterations; x++)
			{
				AITESTboard[vertical][x].setstatus(turn);//my wording is strange, but for some reason I put board[row]is vertical and board[column]is horizontal, THIS WORKS
			}	
		}
		if (board[vertical][horizontal+iterations].gethorizontal()/86 < 7 && turn != board[vertical][horizontal+iterations].getstatus())
		{
			if(board[vertical][horizontal+iterations].getstatus()!= turn && board[vertical][horizontal+iterations].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;
		AItestingeast(horizontal, vertical, iterations, confirmed);	
		}
}
public void AItestingsoutheast(int horizontal, int vertical, int iterations, int confirmed)//my semantics make the way this is set up slightly counterintuitive
{
	int x = horizontal;
	int y = vertical;
	//confirmed needs to be present to make sure there are actually green tiles in between red ones or vice versa, used in a conditional if equals iterations-1
		if(turn == board[vertical+iterations][horizontal+iterations].getstatus()&&iterations > 1 &&confirmed == iterations-1)
		{

			onemethodworks = true;
			while(x<= x+iterations && y<= y+iterations && x+iterations!= 7 && y+iterations!=7)
			{
			  
		
				AITESTboard[y][x].setstatus(turn);//my wording is strange, but for some reason I put board[row]is vertical and board[column]is horizontal, THIS WORKS
				y++;//if not here, then the clicked tile would not change
				x++;			  
			 }	
		}
		if (board[vertical+iterations][horizontal+iterations].gethorizontal()/86 < 7 && board[vertical+iterations][horizontal+iterations].getvertical()/86 < 7&& turn != board[vertical+iterations][horizontal+iterations].getstatus())
		{
		if(board[vertical+iterations][horizontal+iterations].getstatus()!=turn && board[vertical+iterations][horizontal+iterations].getstatus()!=0)
		{
			confirmed++;//necessary to make sure there are green between red or vice versa
		}
		iterations++;
		AItestingsoutheast(horizontal, vertical, iterations, confirmed);	
		}
}
public void AItestingsouthwest(int horizontal, int vertical, int iterations, int confirmed)
{
	
	int x = horizontal;
	int y = vertical;
		if(turn == board[vertical+iterations][horizontal-iterations].getstatus() && iterations>1 && confirmed == iterations -1)
		{

			onemethodworks = true;
			while(x>= x-iterations && y<=y+iterations && x-iterations!= 0 && y+iterations != 7)
			{
				
			
				AITESTboard[y][x].setstatus(turn);//my wording is strange, but for some reason I put board[row]is vertical and board[column]is horizontal, THIS WORKS
				y++;//if not here, then the clicked tile would not change
				x--;
			}
		}
		if (board[vertical+iterations][horizontal-iterations].gethorizontal()/86 > 0 && board[vertical+iterations][horizontal-iterations].getvertical()/86 < 7&& turn != board[vertical+iterations][horizontal-iterations].getstatus())
		{	
			if(board[vertical+iterations][horizontal-iterations].getstatus()!= turn && board[vertical+iterations][horizontal-iterations].getstatus()!=0)
	    	{
	    		confirmed++;
	    	}
		iterations++;
		AItestingsouthwest(horizontal, vertical, iterations, confirmed);
		}


}
public void AItestingnorthwest(int horizontal, int vertical, int iterations, int confirmed)
{
	int x = horizontal;
	int y = vertical;
	if(turn == board[vertical-iterations][horizontal-iterations].getstatus() && iterations>1 && confirmed == iterations -1)
	{

		onemethodworks = true;
		while(x>= x-iterations && y>=y-iterations && x-iterations!= 0 && y-iterations !=0)
		{
			
		
			AITESTboard[y][x].setstatus(turn);//my wording is strange, but for some reason I put board[row]is vertical and board[column]is horizontal, THIS WORKS
			y--;//if not here, then the clicked tile would not change
			x--;
			
		}
	}
	if (board[vertical-iterations][horizontal-iterations].gethorizontal()/86 > 0 && board[vertical-iterations][horizontal-iterations].getvertical()/86 > 0&& turn != board[vertical-iterations][horizontal-iterations].getstatus())
	{	
		if(board[vertical-iterations][horizontal-iterations].getstatus()!= turn && board[vertical-iterations][horizontal-iterations].getstatus()!=0)
    	{
    		confirmed++;
    	}
	iterations++;
	AItestingnorthwest(horizontal, vertical, iterations, confirmed);
	}
}
public void AItestingnortheast(int horizontal, int vertical, int iterations, int confirmed)
{

	int x = horizontal;
	int y = vertical;
	if(turn == board[vertical-iterations][horizontal+iterations].getstatus() && iterations>1 && confirmed == iterations -1)
	{

		onemethodworks = true;
		while(x<= x+iterations && y>=y-iterations && x+iterations!= 7 && y-iterations!= 0)
		{
	
			AITESTboard[y][x].setstatus(turn);//HOLY FRAK THIS IS AFFECTING MY ACTUAL BOARD
			y--;//if not here, then the clicked tile would not change
			x++;
		}
	}
	if (board[vertical-iterations][horizontal+iterations].gethorizontal()/86 < 7 && board[vertical-iterations][horizontal+iterations].getvertical()/86 > 0&& turn != board[vertical-iterations][horizontal+iterations].getstatus())
	{	
		if(board[vertical-iterations][horizontal+iterations].getstatus()!= turn && board[vertical-iterations][horizontal+iterations].getstatus()!=0)
    	{
    		confirmed++;
    	}
	iterations++;
	AItestingnortheast(horizontal, vertical, iterations, confirmed);	
	}
}
@Override
public void actionPerformed(ActionEvent e) 
{
	 if(e.getSource() instanceof JButton)
		{
			JButton clicked = (JButton)e.getSource(); 
			if(e.getSource() instanceof JButton)
			{
				
				if (clicked == AION)
				{
					if(AI == 0)
					{
						AI = 1;
						AION.setText("Turn AI off");
					}
					else if (AI == 1)
					{
						AI = 0;
						AION.setText("Turn on AI");
					}
					
				}
			}
		}
	
}

@Override
public void mouseClicked(MouseEvent arg0) 
{
	
	if(arg0.getSource() instanceof Tile)
	{  
		Tile clicked = (Tile)arg0.getSource();
		System.out.println("Clicked status is"+clicked.getstatus());
		System.out.println("Active status is"+clicked.getactive());
		System.out.println("You have clicked the tile at these coordinates"+" "+clicked.gethorizontal()/86+" ,"+clicked.getvertical()/86);
		if(clicked.getactive() == true)		
	    {
		if(clicked.getvertical()/86!= 0)//if north and south are active at the same time south farks north up, need to fix
		{
			checkingnorth(clicked.gethorizontal()/86, clicked.getvertical()/86, 1, 0);
		}
		if(clicked.getvertical()/86!=7)
		{
			checkingsouth(clicked.gethorizontal()/86, clicked.getvertical()/86, 1, 0);
		}
		if(clicked.gethorizontal()/86!=7)//if west and east are active at the same time, east farks west up, need to fix this
		{
			checkingeast(clicked.gethorizontal()/86, clicked.getvertical()/86, 1, 0);
		}
		if(clicked.gethorizontal()/86!= 0)
		{
			checkingwest(clicked.gethorizontal()/86, clicked.getvertical()/86, 1, 0);
		}	
		if(clicked.gethorizontal()/86!=7 && clicked.getvertical()/86!=7)
		{
			checkingsoutheast(clicked.gethorizontal()/86, clicked.getvertical()/86, 1, 0);
		}
		if(clicked.gethorizontal()/86!=0 && clicked.getvertical()/86!=7)
		{
			checkingsouthwest(clicked.gethorizontal()/86, clicked.getvertical()/86, 1, 0);
		}
		if(clicked.gethorizontal()/86!=0 && clicked.getvertical()/86!=0)
		{
			checkingnorthwest(clicked.gethorizontal()/86, clicked.getvertical()/86, 1, 0);
		}
		if(clicked.gethorizontal()/86!=7 && clicked.getvertical()/86!= 0)
		{
			checkingnortheast(clicked.gethorizontal()/86, clicked.getvertical()/86, 1, 0);
		}
		if(onemethodworks == true)
		{
			if(turn == 1)
			{
				scorekeeping();
				turn = 2;
				Pturn = "green";
				turnlabel.setText("Turn:"+" "+Pturn);
				Redscore.setText("Red has"+" "+redcontrolled);
				Greenscore.setText("Green has"+" "+greencontrolled);
			}
			else if (turn == 2)
			{
				scorekeeping();
				turn = 1;
				Pturn = "red";
				turnlabel.setText("Turn:"+" "+Pturn);
				Redscore.setText("Red has"+" "+redcontrolled);
				Greenscore.setText("Green has"+" "+greencontrolled);
			}
		onemethodworks = false;
		firstcheckmethod = 0;
		clicked.setactive(false);
		}
		
		if(AI == 1 && turn == 2)
		{

			AImove();
		}
	  }
	}
	
	 
	
}

@Override
public void mouseEntered(MouseEvent me) 
{
	if(me.getSource() instanceof Tile)
	{
	Tile entered = (Tile)me.getSource();
	entered.setBorder(BorderFactory.createLineBorder(Color.red));
	}
}

@Override
public void mouseExited(MouseEvent me) 
{
	if(me.getSource() instanceof Tile)
	{
	Tile exited = (Tile)me.getSource();
	exited.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
}
@Override
public void mousePressed(MouseEvent arg0) 
{
	if(arg0.getSource() instanceof Tile)
	{
		Tile pressed = (Tile)arg0.getSource();
	
	
	
		 switch(pressed.getstatus())
		 {
		 case 0: 
			 pressed.setIcon(new ImageIcon("reversi tiledown.png"));
			 break;
		 case 1: 
			 pressed.setIcon(new ImageIcon("reversi chip reddown.png"));
			 break;
		 case 2:
			 pressed.setIcon(new ImageIcon("reversi chip greenrdown.png"));
			 break;
		 }
	}
	
	
	
}

@Override
public void mouseReleased(MouseEvent arg0) 
{
	if(arg0.getSource() instanceof Tile)
	{
		Tile released = (Tile)arg0.getSource();	
		switch(released.getstatus())
		{
		case 0: 
			released.setIcon(new ImageIcon("reversi tile.png"));
			break;
		case 1: 
			released.setIcon(new ImageIcon("reversi chip red.png"));
			break;
		case 2:
			released.setIcon(new ImageIcon("reversi chip greenr.png"));
			break;
		}
	}
	
}

	

}
