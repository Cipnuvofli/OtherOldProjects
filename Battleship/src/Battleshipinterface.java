import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
public class Battleshipinterface extends JFrame implements ActionListener, MouseListener, Boardmodifier
{
	private Battleshiptile board[][] = new Battleshiptile[10][10]; //the board the player places his ships on
	private Battleshiptile AIboard[][] = new Battleshiptile[10][10];//the board the AI places his ships on
	private Battleshiptile AITESTboard[][] = new Battleshiptile [10][10];//mirror of actual board AI uses for testing purposes
	private JButton AION = new JButton();//activates AI
	private int AI = 0;//if this is 1, the AI will move after you
	private int redhealth = 17; //Red's Overall Health 
	private int greenhealth = 17; //Green's Overall Health
	private JLabel turnlabel = new JLabel();//this is a label
	private String Pturn= "Player 1 ";//this changes to green if its greens turn
	private int turn = 1; //1 is 1 2 is 2
	private String Direction = "left";//the orientation used for placing ships
	private boolean preptime = true;//when false, pieces are set and game is begun 
	private int shipprocession = 1;//what ship you're placing, when it is at 6-10 it is player 2's turn to set ships
	private JLabel Redscore = new JLabel();
	private JLabel Greenscore = new JLabel();
	private JLabel redboard = new JLabel();
	private JLabel greenboard = new JLabel();
	int horizontal = 0;
	int vertical = 0;
	private Random rand = new Random();
	private ArrayList<Battleshiptile> legalmoves = new ArrayList<Battleshiptile>();
	public Battleshipinterface()
	{

		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Battleship");
		setSize(1280, 1024);
		setResizable(false);
		setLocation(10, 20);
		for (int row = 0; row<10; row++)
		{
		      for (int column = 0; column<10; column++)
			  {
		    	  int horz = column*51;//where it is in terms of x could be defined as column
		    	  int vert = row*51;//where it is in terms of y could be defined as row
		    	  board[column][row] = new Battleshiptile(column, row);
		    	  board[column][row].sethorizontal(horz);
		    	  board[column][row].setvertical(vert);
		    	  board[column][row].setBounds(horz,vert, 50, 50);
		    	  board[column][row].setBorder(BorderFactory.createLineBorder(Color.black));
		    	  board[column][row].setIcon(new ImageIcon("genericbattleshiptile.png"));
		    	  board[column][row].addMouseListener(this);
		    	  contentPane.add(board[column][row]);
		    	  
			   }
		}
	
		for (int row = 0; row<10; row++)
		{
		      for (int column = 0; column<10; column++)
			  {
		    	  int horz = (column)*51;//where it is in terms of x could be defined as column
		    	  int vert = (row)*51  ;//where it is in terms of y could be defined as row
		    	  AIboard[column][row] = new Battleshiptile(row, column);
		    	  AIboard[column][row].sethorizontal(horz);
		    	  AIboard[column][row].setvertical(vert);
		    	  AIboard[column][row].setBounds(horz+600,vert, 50, 50);	   
		    	  AIboard[column][row].setBorder(BorderFactory.createLineBorder(Color.black));
		    	  AIboard[column][row].setIcon(new ImageIcon("genericbattleshiptile.png"));
		    	  AIboard[column][row].addMouseListener(this);
		    	  contentPane.add(AIboard[column][row]);
			   }
		}
		AION = new JButton("Turn on AI");//Activates AI
		AION.setBounds(0, 690, 100, 50);
		AION.addActionListener(this);
		contentPane.add(AION);
		
		turnlabel = new JLabel("Turn:"+" "+Pturn);// Declares whos turn it is
		turnlabel.setBounds(700,700, 120, 30);
		contentPane.add(turnlabel);
		
		redboard = new JLabel("Player 1 Board");// Declares whos board it is
		redboard.setBounds(150,550, 120, 30);
		contentPane.add(redboard);
		
		greenboard = new JLabel("Player 2 Board");// Declares whos board it is
		greenboard.setBounds(750,550, 120, 30);
		contentPane.add(greenboard);

		Redscore = new JLabel("Red has"+" "+redhealth+" "+"Hit Points");
		Redscore.setBounds(250, 687, 150, 50);
		contentPane.add(Redscore);
		
		Greenscore = new JLabel("Green has"+" "+greenhealth+" "+"Hit Points");
		Greenscore.setBounds(500, 687, 150, 50);
		contentPane.add(Greenscore);

	}

	public static void main(String[] args)
	{
			// this spawns the window that holds everything
				Battleshipinterface frame= new Battleshipinterface();
				frame.setVisible(true);
		 	
	}

	

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		if(arg0.getSource() instanceof Battleshiptile && arg0.getButton() == arg0.BUTTON1)
		{  
			Battleshiptile clicked = (Battleshiptile)arg0.getSource();	
			if(preptime == true && shipprocession == 1)
			{
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 2) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-1 >=0)
				{		
				Ship patrolboat = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 2, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 2) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+1 <=9)
				{
				Ship patrolboat = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 2, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 2) == false && Direction.equals("down") && (clicked.getvertical()/51)+1 <=9)
				{
				Ship patrolboat = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 2, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 2) == false && Direction.equals("up") && (clicked.getvertical()/51)-1 >=0)
				{
				Ship patrolboat = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 2, Direction, board);	
				shipprocession++;
				}
			}
			else if(preptime == true && shipprocession == 2)
			{
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-2 >=0)
				{
				Ship cruiser = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+2 <=9)
				{
				Ship cruiser = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("down") && (clicked.getvertical()/51)+2 <=9)
				{
				Ship cruiser = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("up") && (clicked.getvertical()/51)-2 >=0)
				{
				Ship cruiser = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, board);	
				shipprocession++;
				}
			}
			else if(preptime == true && shipprocession == 3)
			{
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-2 >=0)
				{
				Ship destroyer = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+2 <=9)
				{
				Ship destroyer = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("down") && (clicked.getvertical()/51)+2 <=9)
				{
				Ship destroyer = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("up") && (clicked.getvertical()/51)-2 >=0)
				{
				Ship destroyer = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, board);	
				shipprocession++;
				}
			}
			else if(preptime == true && shipprocession == 4)
			{
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 4) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-3 >=0)
				{
				Ship battleship = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 4, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 4) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+3 <=9)
				{
				Ship battleship = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 4, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 4) == false && Direction.equals("down") && (clicked.getvertical()/51)+3 <=9)
				{
				Ship battleship = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 4, Direction, board);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 4) == false && Direction.equals("up") && (clicked.getvertical()/51)-3 >=0)
				{
				Ship battleship = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 4, Direction, board);	
				shipprocession++;
				}
			}
			else if(preptime == true && shipprocession == 5)
			{
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-4 >=0)
				{
				Ship carrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, board);	
				shipprocession++;
				System.out.println("Player 2/AI, set your pieces");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+4 <=9)
				{
				Ship carrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, board);	
				shipprocession++;
				System.out.println("Player 2/AI, set your pieces");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("down") && (clicked.getvertical()/51)+4 <=9)
				{
				Ship carrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, board);	
				shipprocession++;
				System.out.println("Player 2/AI, set your pieces");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("up") && (clicked.getvertical()/51)-4 >=0)
				{
				Ship carrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, board);	
				shipprocession++;
				System.out.println("Player 2/AI, set your pieces");
				}
			}
			else if(preptime == true && shipprocession == 6)
			{
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 2) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-1 >=0)
				{
					
				Ship AIpatrolboat = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 2, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 2) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+1 <=9)
				{
				Ship AIpatrolboat = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 2, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 2) == false && Direction.equals("down") && (clicked.getvertical()/51)+1 <=9)
				{
				Ship AIpatrolboat = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 2, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 2) == false && Direction.equals("up") && (clicked.getvertical()/51)-1 >=0)
				{
				Ship AIpatrolboat = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 2, Direction, AIboard);	
				shipprocession++;
				}
			}
			else if(preptime == true && shipprocession == 7)
			{
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-2 >=0)
				{
				Ship AIcruiser = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+2 <=9)
				{
				Ship AIcruiser = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("down") && (clicked.getvertical()/51)+2 <=9)
				{
				Ship AIcruiser = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("up") && (clicked.getvertical()/51)-2 >=0)
				{
				Ship AIcruiser = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, AIboard);	
				shipprocession++;
				}
			}
			else if(preptime == true && shipprocession == 8)
			{
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-2 >=0)
				{
				Ship AIdestroyer = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+2 <=9)
				{
				Ship AIdestroyer = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("down") && (clicked.getvertical()/51)+2 <=9)
				{
				Ship AIdestroyer = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 3) == false && Direction.equals("up") && (clicked.getvertical()/51)-2 >=0)
				{
				Ship AIdestroyer = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 3, Direction, AIboard);	
				shipprocession++;
				}
			}
			else if(preptime == true && shipprocession == 9)
			{
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 4) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-3 >=0)
				{
				Ship AIbattleship = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 4, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 4) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+3 <=9)
				{
				Ship AIbattleship = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 4, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 4) == false && Direction.equals("down") && (clicked.getvertical()/51)+3 <=9)
				{
				Ship AIbattleship = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 4, Direction, AIboard);	
				shipprocession++;
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 4) == false && Direction.equals("up") && (clicked.getvertical()/51)-3 >=0)
				{
				Ship AIbattleship = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 4, Direction, AIboard);	
				shipprocession++;
				}
			}
			else if(preptime == true && shipprocession == 10)
			{
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-4 >=0)
				{
					
				Ship AIcarrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, AIboard);	
				preptime = false;
				boardobfuscator();
				System.out.println("GAMESTART");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+4 <=9)
				{
				Ship AIcarrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, AIboard);	
				preptime = false;
				boardobfuscator();
				System.out.println("GAMESTART");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("down") && (clicked.getvertical()/51)+4 <=9)
				{
				Ship AIcarrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, AIboard);	
				preptime = false;
				boardobfuscator();
				System.out.println("GAMESTART");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("up") && (clicked.getvertical()/51)-4 >=0)
				{
				Ship AIcarrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, AIboard);	
				preptime = false;
				boardobfuscator();
				System.out.println("GAMESTART");
				}
			}
			else if(preptime == false && turn == 1 && clicked == AIboard[clicked.gethorizontal()/51][clicked.getvertical()/51])
			{
				if(gethasship(clicked) == true)
				{		
					hit(clicked);	
					turn = 2;	
					boardobfuscator();
					turnlabel.setText("Player 2 turn");
				}
				else if(gethasship(clicked) == false)
				{	
					miss(clicked);
					turn = 2;	
					boardobfuscator();
					turnlabel.setText("Player 2 turn");
				}
			}
			else if(preptime == false && turn == 2  && clicked == board[clicked.gethorizontal()/51][clicked.getvertical()/51])
			{
				if(gethasship(clicked) == true)
				{		
					hit(clicked);
					turn = 1;	
					boardobfuscator();
					turnlabel.setText("Player 1 turn");
				}
				else if(gethasship(clicked) == false)
				{	
					miss(clicked);
					turn = 1;	
					boardobfuscator();
					turnlabel.setText("Player 1 turn");
				}
			}
		}
		else if(arg0.getButton() == arg0.BUTTON3)//this changes the value of direction on right click
		{
			if(Direction.equals("left"))
			{		
				Direction = "up";
				boardrefresh();//clears afterimages
			}
			else if(Direction.equals("up"))
			{
				Direction = "right";
				boardrefresh();
			}
			else if(Direction.equals("right"))
			{
				Direction = "down";
				boardrefresh();
			}
			else if(Direction.equals("down"))
			{
				Direction = "left";
				boardrefresh();
			}
		}
	}
	public void boardrefresh()
	{
		for(int x = 0; x<=9; x++)
		{
			for(int y = 0; y<=9; y++)
			{
				if(board[x][y].gethasship() == false)
				{
					board[x][y].setIcon(new ImageIcon("genericbattleshiptile.png"));//this changes all tiles that don't have ships in them to generictiles
				}
				if(AIboard[x][y].gethasship() == false)
				{
					AIboard[x][y].setIcon(new ImageIcon("genericbattleshiptile.png"));
				}
			}
		}
	}
	public void miss(Battleshiptile clicked)
	{
		clicked.setBorder(BorderFactory.createLineBorder(Color.green));	
		clicked.sethasbeentargeted(true);
	}
	public void hit(Battleshiptile clicked)
	{
		if(turn == 1)
		{
			greenhealth--;
			Greenscore.setText("Green has"+" "+greenhealth+" "+"Hit Points");	
		}
		else if(turn == 2)
		{
			redhealth--;
			Redscore.setText("Red has"+" "+redhealth+" "+"Hit Points");
		}
		clicked.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		clicked.sethasbeentargeted(true);
	}
	public boolean takencheck(int horizontal, int vertical, int length)
	{
		boolean taken = false;
		for(int x = 0; x<=length; x++)
		{
		  if(Direction.equals("left")&& horizontal-x>=0 && shipprocession <=5)
		  {
			if(board[horizontal-x][vertical].gethasship() == true)
			{
				taken = true;	
			}
		  }		  
		  else if(Direction.equals("right")&& horizontal+x<=9&& shipprocession <= 5)
		  {
			    if(board[horizontal+x][vertical].gethasship() == true)
				{
					taken = true;	
				}
		  }
		  else if(Direction.equals("up")&& vertical-x>=0 && shipprocession <= 5)
		  {
			  if(board[horizontal][vertical-x].gethasship() == true)
			  {
					taken = true;	
			  }
		  }
		  else if(Direction.equals("down")&& vertical+x<=9 && shipprocession <= 5)
		  {
			  if(board[horizontal][vertical+x].gethasship() == true)
			  {
					taken = true;	
			  }
		  }
		  else if(Direction.equals("left")&& horizontal-x>=0 && shipprocession >5 && shipprocession <= 10)
		  {
			if(AIboard[horizontal-x][vertical].gethasship() == true)
			{
				taken = true;	
			}
		  }		  
		  else if(Direction.equals("right")&& horizontal+x<=9&& shipprocession >5 && shipprocession <= 10)
		  {
			    if(AIboard[horizontal+x][vertical].gethasship() == true)
				{
					taken = true;	
				}
		  }
		  else if(Direction.equals("up")&& vertical-x>=0 && shipprocession >5 && shipprocession <= 10)
		  {
			  if(AIboard[horizontal][vertical-x].gethasship() == true)
			  {
					taken = true;	
			  }
		  }
		  else if(Direction.equals("down")&& vertical+x<=9 && shipprocession >5 && shipprocession <= 10)
		  {
			  if(AIboard[horizontal][vertical+x].gethasship() == true)
			  {
					taken = true;	
			  }
		  }
		}
		return taken;
	}
	public void boardobfuscator()
	{
		for(int x = 0; x<=9; x++)
		{
			for(int y = 0; y<=9; y++)
			{
				if(turn == 1 && AIboard[x][y].gethasship() == true)
				{
					board[x][y].setIcon(new ImageIcon(board[x][y].getimage()));
					AIboard[x][y].setIcon(new ImageIcon("genericbattleshiptile.png"));
				}
				else if (turn == 2 && board[x][y].gethasship() == true)
				{
					AIboard[x][y].setIcon(new ImageIcon(board[x][y].getimage()));
					board[x][y].setIcon(new ImageIcon("genericbattleshiptile.png"));
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) //this makes a ship follow your cursor during ship placement time
	{
		if(arg0.getSource() instanceof Battleshiptile)
		{
			Battleshiptile entered = (Battleshiptile)arg0.getSource();
			if(entered.gethasbeentargeted() == false)
			{
			entered.setBorder(BorderFactory.createLineBorder(Color.red));	
			}
			if(preptime == true && shipprocession == 1)//if it is time to place ships, place the patrol boat
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=1; x++)
						{
							if(x == 0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
							}
							else if(x == 1 && (entered.gethorizontal()/51)-x >=0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=1; x++)
						{
							if(x == 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipenddown.png"));
							}
							else if(x == 1 && (entered.getvertical()/51)-x >=0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipendup.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=1; x++)
					{
						if(x == 0 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
						}
						else if(x == 1 && (entered.gethorizontal()/51)+x <10 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=1; x++)
					{
						if(x == 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipendup.png"));
						}
						else if(x == 1 && (entered.getvertical()/51)+x <10 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipenddown.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 2)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
							}
							else if(x == 2 && (entered.gethorizontal()/51)-x >=0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{							
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
							}
							else if((entered.gethorizontal()/51)-x >= 0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{							
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipenddown.png"));
							}
							else if(x == 2 && (entered.getvertical()/51)-x >=0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipendup.png"));
							}
							else if((entered.getvertical()/51)-x >= 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("Shipvert.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{					
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
						}
						else if(x == 2 && (entered.gethorizontal()/51)+x <=9 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{				
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
						}
						else if((entered.gethorizontal()/51)+x <= 9 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{					
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipendup.png"));
						}
						else if(x == 2 && (entered.getvertical()/51)+x <=9 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipenddown.png"));
						}
						else if((entered.getvertical()/51)+x <= 9 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("Shipvert.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 3)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
							}
							else if(x == 2 && (entered.gethorizontal()/51)-x >=0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
							}
							else if((entered.gethorizontal()/51)-x >= 0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{				
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{			
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipenddown.png"));
							}
							else if(x == 2 && (entered.getvertical()/51)-x >=0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{							
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipendup.png"));
							}
							else if((entered.getvertical()/51)-x >= 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("Shipvert.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{				
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
						}
						else if(x == 2 && (entered.gethorizontal()/51)+x <=9 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{						
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
						}
						else if((entered.gethorizontal()/51)+x <= 9 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{					
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{				
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipendup.png"));
						}
						else if(x == 2 && (entered.getvertical()/51)+x <=9 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipenddown.png"));
						}
						else if((entered.getvertical()/51)+x <= 9 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{				
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("Shipvert.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 4)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=3; x++)
						{
							if(x == 0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
							}
							else if(x == 3 && (entered.gethorizontal()/51)-x >=0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{					
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
							}
							else if((entered.gethorizontal()/51)-x >= 0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{				
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=3; x++)
						{
							if(x == 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{	
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipenddown.png"));
							}
							else if(x == 3 && (entered.getvertical()/51)-x >=0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{					
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipendup.png"));
							}
							else if((entered.getvertical()/51)-x >= 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{	
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("Shipvert.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=3; x++)
					{
						if(x == 0 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
						}
						else if(x == 3 && (entered.gethorizontal()/51)+x <=9 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{			
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
						}
						else if((entered.gethorizontal()/51)+x <= 9 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{		
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=3; x++)
					{
						if(x == 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{			
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipendup.png"));
						}
						else if(x == 3 && (entered.getvertical()/51)+x <=9 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{			
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipenddown.png"));
						}
						else if((entered.getvertical()/51)+x <= 9 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("Shipvert.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 5)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=4; x++)
						{
							if(x == 0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{			
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
							}
							else if(x == 4 && (entered.gethorizontal()/51)-x >=0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{					
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
							}
							else if((entered.gethorizontal()/51)-x >= 0 && board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=4; x++)
						{
							if(x == 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{				
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipenddown.png"));
							}
							else if(x == 4 && (entered.getvertical()/51)-x >=0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{						
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipendup.png"));
							}
							else if((entered.getvertical()/51)-x >= 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{				
							board[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("Shipvert.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=4; x++)
					{
						if(x == 0 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{				
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
						}
						else if(x == 4 && (entered.gethorizontal()/51)+x <=9 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{	
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
						}
						else if((entered.gethorizontal()/51)+x <= 9 && board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{				
						board[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=4; x++)
					{
						if(x == 0 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{		
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipendup.png"));
						}
						else if(x == 4 && (entered.getvertical()/51)+x <=9 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipenddown.png"));
						}
						else if((entered.getvertical()/51)+x <= 9 && board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{				
						board[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("Shipvert.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 6)//if it is time to place ships, place the patrol boat
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=1; x++)
						{
							if(x == 0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
							}
							else if(x == 1 && (entered.gethorizontal()/51)-x >=0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=1; x++)
						{
							if(x == 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipenddown.png"));
							}
							else if(x == 1 && (entered.getvertical()/51)-x >=0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipendup.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=1; x++)
					{
						if(x == 0 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
						}
						else if(x == 1 && (entered.gethorizontal()/51)+x <10 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=1; x++)
					{
						if(x == 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipendup.png"));
						}
						else if(x == 1 && (entered.getvertical()/51)+x <10 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipenddown.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 7)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
							}
							else if(x == 2 && (entered.gethorizontal()/51)-x >=0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{							
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
							}
							else if((entered.gethorizontal()/51)-x >= 0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{							
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipenddown.png"));
							}
							else if(x == 2 && (entered.getvertical()/51)-x >=0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipendup.png"));
							}
							else if((entered.getvertical()/51)-x >= 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("Shipvert.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{					
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
						}
						else if(x == 2 && (entered.gethorizontal()/51)+x <=9 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{				
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
						}
						else if((entered.gethorizontal()/51)+x <= 9 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{					
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipendup.png"));
						}
						else if(x == 2 && (entered.getvertical()/51)+x <=9 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipenddown.png"));
						}
						else if((entered.getvertical()/51)+x <= 9 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("Shipvert.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 8)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
							}
							else if(x == 2 && (entered.gethorizontal()/51)-x >=0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
							}
							else if((entered.gethorizontal()/51)-x >= 0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{				
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{			
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipenddown.png"));
							}
							else if(x == 2 && (entered.getvertical()/51)-x >=0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{							
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipendup.png"));
							}
							else if((entered.getvertical()/51)-x >= 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("Shipvert.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{				
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
						}
						else if(x == 2 && (entered.gethorizontal()/51)+x <=9 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{						
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
						}
						else if((entered.gethorizontal()/51)+x <= 9 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{					
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{				
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipendup.png"));
						}
						else if(x == 2 && (entered.getvertical()/51)+x <=9 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipenddown.png"));
						}
						else if((entered.getvertical()/51)+x <= 9 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{				
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("Shipvert.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 9)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=3; x++)
						{
							if(x == 0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
							}
							else if(x == 3 && (entered.gethorizontal()/51)-x >=0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{					
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
							}
							else if((entered.gethorizontal()/51)-x >= 0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{				
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=3; x++)
						{
							if(x == 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{	
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipenddown.png"));
							}
							else if(x == 3 && (entered.getvertical()/51)-x >=0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{					
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipendup.png"));
							}
							else if((entered.getvertical()/51)-x >= 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{	
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("Shipvert.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=3; x++)
					{
						if(x == 0 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
						}
						else if(x == 3 && (entered.gethorizontal()/51)+x <=9 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{			
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
						}
						else if((entered.gethorizontal()/51)+x <= 9 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{		
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=3; x++)
					{
						if(x == 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{			
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipendup.png"));
						}
						else if(x == 3 && (entered.getvertical()/51)+x <=9 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{			
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipenddown.png"));
						}
						else if((entered.getvertical()/51)+x <= 9 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("Shipvert.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 10)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=4; x++)
						{
							if(x == 0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{			
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
							}
							else if(x == 4 && (entered.gethorizontal()/51)-x >=0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{					
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
							}
							else if((entered.gethorizontal()/51)-x >= 0 && AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)-x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=4; x++)
						{
							if(x == 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{				
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipenddown.png"));
							}
							else if(x == 4 && (entered.getvertical()/51)-x >=0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{						
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("shipendup.png"));
							}
							else if((entered.getvertical()/51)-x >= 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].gethasship() == false)
							{				
							AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)-x].setIcon(new ImageIcon("Shipvert.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=4; x++)
					{
						if(x == 0 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{				
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendleft.png"));
						}
						else if(x == 4 && (entered.gethorizontal()/51)+x <=9 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{	
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("shipendright.png"));
						}
						else if((entered.gethorizontal()/51)+x <= 9 && AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].gethasship() == false)
						{				
						AIboard[(entered.gethorizontal()/51)+x][entered.getvertical()/51].setIcon(new ImageIcon("Shiphorz.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=4; x++)
					{
						if(x == 0 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{		
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipendup.png"));
						}
						else if(x == 4 && (entered.getvertical()/51)+x <=9 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{					
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("shipenddown.png"));
						}
						else if((entered.getvertical()/51)+x <= 9 && AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].gethasship() == false)
						{				
						AIboard[(entered.gethorizontal()/51)][(entered.getvertical()/51)+x].setIcon(new ImageIcon("Shipvert.png"));
						}
					}
					
				}
			}
		}
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)//this switches the images to remove the trailing ship from where you were
	{
		if(arg0.getSource() instanceof Battleshiptile)
		{
			Battleshiptile exited = (Battleshiptile)arg0.getSource();
			if(exited.gethasbeentargeted() == false)
			{
			exited.setBorder(BorderFactory.createLineBorder(Color.black));
			}
			if(preptime == true && shipprocession == 1)
			{
				if(Direction.equals("left"))
				{
					for(int x = 0; x<=1; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 1 && (exited.gethorizontal()/51)-x >=0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=1; x++)
						{
							if(x == 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							//board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));	
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 1 && (exited.getvertical()/51)-x >=0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							//board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					for(int x = 0; x<=1; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						//board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));	
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 1 && (exited.gethorizontal()/51)+x <10 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						//board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=1; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						//board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));	
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 1 && (exited.getvertical()/51)+x <10 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						//board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 2)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 2 && (exited.gethorizontal()/51)-x >=0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.gethorizontal()/51)-x >= 0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 2 && (exited.getvertical()/51)-x >=0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.getvertical()/51)-x >= 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 2 && (exited.gethorizontal()/51)+x <=9 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.gethorizontal()/51)+x <= 9 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 2 && (exited.getvertical()/51)+x <=9 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.getvertical()/51)+x <= 9 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 3)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 2 && (exited.gethorizontal()/51)-x >=0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.gethorizontal()/51)-x >= 0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}			
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 2 && (exited.getvertical()/51)-x >=0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.getvertical()/51)-x >= 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
		//TODO resolve the only works once issue with hit and miss, HINT, It works as long as turn is not changed		
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 2 && (exited.gethorizontal()/51)+x <=9 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.gethorizontal()/51)+x <= 9 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 2 && (exited.getvertical()/51)+x <=9 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.getvertical()/51)+x <= 9 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 4)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=3; x++)
						{
							if(x == 0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 3 && (exited.gethorizontal()/51)-x >=0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.gethorizontal()/51)-x >= 0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}			
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=3; x++)
						{
							if(x == 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 3 && (exited.getvertical()/51)-x >=0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.getvertical()/51)-x >= 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=3; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 3 && (exited.gethorizontal()/51)+x <=9 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.gethorizontal()/51)+x <= 9 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=3; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 3 && (exited.getvertical()/51)+x <=9 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.getvertical()/51)+x <= 9 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 5)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=4; x++)
						{
							if(x == 0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
						
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 4 && (exited.gethorizontal()/51)-x >=0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.gethorizontal()/51)-x >= 0 && board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}			
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=4; x++)
						{
							if(x == 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 4 && (exited.getvertical()/51)-x >=0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.getvertical()/51)-x >= 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{

							board[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=4; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{	
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 4 && (exited.gethorizontal()/51)+x <=9 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{				
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.gethorizontal()/51)+x <= 9 && board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{						
						board[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=4; x++)
					{
						if(x == 0 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{				
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 4 && (exited.getvertical()/51)+x <=9 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{	
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.getvertical()/51)+x <= 9 && board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						board[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 6)
			{
				if(Direction.equals("left"))
				{
					for(int x = 0; x<=1; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 1 && (exited.gethorizontal()/51)-x >=0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=1; x++)
						{
							if(x == 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							//AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));	
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 1 && (exited.getvertical()/51)-x >=0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							//AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					for(int x = 0; x<=1; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						//AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));	
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 1 && (exited.gethorizontal()/51)+x <10 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						//AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=1; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						//AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));	
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 1 && (exited.getvertical()/51)+x <10 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						//AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setBorder(BorderFactory.createLineBorder(Color.yellow));
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 7)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 2 && (exited.gethorizontal()/51)-x >=0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.gethorizontal()/51)-x >= 0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 2 && (exited.getvertical()/51)-x >=0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.getvertical()/51)-x >= 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 2 && (exited.gethorizontal()/51)+x <=9 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.gethorizontal()/51)+x <= 9 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 2 && (exited.getvertical()/51)+x <=9 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.getvertical()/51)+x <= 9 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 8)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 2 && (exited.gethorizontal()/51)-x >=0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.gethorizontal()/51)-x >= 0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}			
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=2; x++)
						{
							if(x == 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 2 && (exited.getvertical()/51)-x >=0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.getvertical()/51)-x >= 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 2 && (exited.gethorizontal()/51)+x <=9 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.gethorizontal()/51)+x <= 9 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=2; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 2 && (exited.getvertical()/51)+x <=9 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.getvertical()/51)+x <= 9 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 9)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=3; x++)
						{
							if(x == 0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 3 && (exited.gethorizontal()/51)-x >=0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.gethorizontal()/51)-x >= 0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}			
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=3; x++)
						{
							if(x == 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 3 && (exited.getvertical()/51)-x >=0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.getvertical()/51)-x >= 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=3; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 3 && (exited.gethorizontal()/51)+x <=9 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.gethorizontal()/51)+x <= 9 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=3; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 3 && (exited.getvertical()/51)+x <=9 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.getvertical()/51)+x <= 9 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
			}
			else if(preptime == true && shipprocession == 10)
			{
				if(Direction.equals("left"))
				{
					
						for(int x = 0; x<=4; x++)
						{
							if(x == 0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
						
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 4 && (exited.gethorizontal()/51)-x >=0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.gethorizontal()/51)-x >= 0 && AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)-x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}			
				}
				else if(Direction.equals("up"))
				{
					
						for(int x = 0; x<=4; x++)
						{
							if(x == 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if(x == 4 && (exited.getvertical()/51)-x >=0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{
							
							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
							else if((exited.getvertical()/51)-x >= 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].gethasship() == false)
							{

							AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)-x].setIcon(new ImageIcon("genericbattleshiptile.png"));
							}
						}
					
				}
				else if(Direction.equals("right"))
				{
					
					for(int x = 0; x<=4; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{	
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 4 && (exited.gethorizontal()/51)+x <=9 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{				
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.gethorizontal()/51)+x <= 9 && AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].gethasship() == false)
						{						
						AIboard[(exited.gethorizontal()/51)+x][exited.getvertical()/51].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
				else if(Direction.equals("down"))
				{
					
					for(int x = 0; x<=4; x++)
					{
						if(x == 0 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{				
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if(x == 4 && (exited.getvertical()/51)+x <=9 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{	
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
						else if((exited.getvertical()/51)+x <= 9 && AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].gethasship() == false)
						{
						AIboard[(exited.gethorizontal()/51)][(exited.getvertical()/51)+x].setIcon(new ImageIcon("genericbattleshiptile.png"));
						}
					}
					
				}
			}
		
		}
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		if(arg0.getSource() instanceof Battleshiptile)
		{
			Battleshiptile pressed = (Battleshiptile)arg0.getSource();		
			if(pressed.gethasship() == false)
			{
				pressed.setIcon(new ImageIcon("genericbattleshiptile.png"));
			}
			
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		if(arg0.getSource() instanceof Battleshiptile)
		{
			Battleshiptile released = (Battleshiptile)arg0.getSource();	
			if(released.gethasship() == false)
			{
			released.setIcon(new ImageIcon("genericbattleshiptile.png")); 
			}
		}
		
	}
	
	@Override
	public boolean gethasship(Battleshiptile Tile) //interface method of boardmodifier
	{
		return Tile.gethasship();
	
	}

	@Override
	public boolean getshiphit(Battleshiptile Tile) //interface method of boardmodifier
	{
		return Tile.getshiphit();
	}

	@Override
	public void setboardicon(int horizontal, int vertical, String Image) //interface method of boardmodifier
	{
		board[horizontal][vertical].setIcon(new ImageIcon(Image));
		
	}

	@Override
	public void sethasship(int horizontal, int vertical, boolean condition)//interface method of boardmodifier 
	{
		board[horizontal][vertical].sethasship(condition);
		board[horizontal][vertical].setBorder(BorderFactory.createLineBorder(Color.yellow));	
		
	}

	@Override
	public void setshiphit(int horizontal, int vertical, boolean condition)//interface method of boardmodifier 
	{
		board[horizontal][vertical].setshiphit(condition);
		
	}

	@Override
	public void getboardicon(Battleshiptile Tile)//interfacemethod of boardmodifier 
	{
		
		
	}

}
