import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
public class Battleshipinterface extends JFrame implements ActionListener, MouseListener, Boardmodifier
{
	private Battleshiptile board[][] = new Battleshiptile[10][10]; //the board the player places his ships on
	private Battleshiptile AIboard[][] = new Battleshiptile[10][10];//the board the AI places his ships on
	private int AIreferenceh = 0;
	private int AIreferencev = 0;
	private JButton AION = new JButton();//activates AI
	private int AI = 0;//if this is 1, the AI will move after you
	private int redhealth = 17; //Player 1's health
	private int greenhealth = 17; //Player 2's Health
	private JLabel turnlabel = new JLabel();//this is a label
	private String Pturn= "Player 1 ";//this changes depending on whos turn it is
	private int turn = 1; //1 is 1 2 is 2
	private String Direction = "left";//the orientation used for placing ships
	private boolean directionconfirmed = false;
	private boolean preptime = true;//when false, pieces are set and game is begun 
	private int shipprocession = 1;//what ship you're placing, when it is at 6-10 it is player 2's turn to set ships
	private JLabel Redscore = new JLabel();// Displays hit points in interface
	private JLabel Greenscore = new JLabel();//Displays hit points in interface
	private JLabel redboard = new JLabel();
	private JLabel greenboard = new JLabel();
	int horizontal = 0;
	int vertical = 0;
	private int AIfiringline = 0;//determines which AIFiring method to use
	private Random rand = new Random();//tool of AI decisionmaking
	private int rhorizontal = rand.nextInt(10);//used to place ships and determine initial firing sequences
	private int rvertical = rand.nextInt(10);//used to place ships and determine initial firing sequences
	private int rdirection = rand.nextInt(4);//used to place AIships and determine initial firing sequences
	private String AIdirection = "left";//lets the AI choose directions
	private ArrayList<Battleshiptile> legalmoves = new ArrayList<Battleshiptile>();
	public Battleshipinterface()
	{

		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Battleship");
		setSize(1125, 800);
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
	public void actionPerformed(ActionEvent e)
	{
		JButton clicked = (JButton)e.getSource(); 
		if(e.getSource() instanceof JButton)
		{
			if(clicked == AION)
			{
			  if(AI == 0)
			  {
				AI = 1;//Turns the AI ON
				AION.setText("Turn off AI");
				System.out.println(AI);
			  }
			  else if(AI == 1)
			  {
				  AI = 0;//Turns the AI off
				  AION.setText("Turn on AI");
				  System.out.println(AI);
			  }
		   }
		
		}
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
				//If the area the ship would be is unoccupied, the orientation is set to left, and the ship would not be out of bounds
				//Places a ship in a direction and goes on to next ship	
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
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("left") && (clicked.gethorizontal()/51) >=4)
				{
				Ship carrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, board);	
				shipprocession++;
				turn = 2;
				boardobfuscator();//hides player ones tiles while the AI or P2 sets his
				System.out.println("Player 2/AI, set your pieces");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+4 <=9)
				{
				Ship carrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, board);	
				shipprocession++;
				turn = 2;
				boardobfuscator();
				System.out.println("Player 2/AI, set your pieces");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("down") && (clicked.getvertical()/51)+4 <=9)
				{
				Ship carrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, board);	
				shipprocession++;
				turn = 2;
				boardobfuscator();
				System.out.println("Player 2/AI, set your pieces");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("up") && (clicked.getvertical()/51)-4 >=0)
				{
				Ship carrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, board);	
				shipprocession++;
				turn = 2;
				boardobfuscator();
				System.out.println("Player 2/AI, set your pieces");
				}
				if(AI == 1)//Single player mode is where this happens
				{
					System.out.println("Placing boat");
					AIplacerboat();//Places a ship at a random location
					System.out.println("Placing cruiser");
					AIplacercruiser();
					System.out.println("Placing destroyer");
					AIplacerdestroyer();
					System.out.println("Placing battleship");
					AIplacerbattleship();
					System.out.println("Placing carrier");
					AIplacercarrier();
					preptime = false;
					turn = 1;
					boardobfuscator();
					System.out.println("GAMESTART");
				}
			}
			else if(preptime == true && shipprocession == 6)//this happens in two player mode
			{
			  if(AI == 0)
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
			
			}
			else if(preptime == true && shipprocession == 7)
			{
			  if(AI == 0)
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
			 
			}
			else if(preptime == true && shipprocession == 8)
			{
			  if(AI == 0)
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
			 
			}
			else if(preptime == true && shipprocession == 9)
			{
			  if(AI == 0)
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
			
			}
			else if(preptime == true && shipprocession == 10)
			{
			  if(AI == 0)
			  {
				if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("left") && (clicked.gethorizontal()/51)-4 >=0)
				{	
				Ship AIcarrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, AIboard);	
				preptime = false;
				turn = 1;
				boardobfuscator();
				System.out.println("GAMESTART");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("right") && (clicked.gethorizontal()/51)+4 <=9)
				{
				Ship AIcarrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, AIboard);	
				preptime = false;
				turn = 1;
				boardobfuscator();
				System.out.println("GAMESTART");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("down") && (clicked.getvertical()/51)+4 <=9)
				{
				Ship AIcarrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, AIboard);	
				preptime = false;
				turn = 1;
				boardobfuscator();
				System.out.println("GAMESTART");
				}
				else if(takencheck(clicked.gethorizontal()/51, clicked.getvertical()/51, 5) == false && Direction.equals("up") && (clicked.getvertical()/51)-4 >=0)
				{
				Ship AIcarrier = new Ship(clicked.gethorizontal()/51, clicked.getvertical()/51, 5, Direction, AIboard);	
				preptime = false;
				turn = 1;
				boardobfuscator();
				System.out.println("GAMESTART");
				}
			  }
			
			}
			else if(preptime == false && turn == 1 && clicked == AIboard[clicked.gethorizontal()/51][clicked.getvertical()/51]&&AIboard[clicked.gethorizontal()/51][clicked.getvertical()/51].gethasbeentargeted()==false)//when the game has started, this is hit/miss detection
			{
				if(gethasship(clicked) == true)
				{		
					if(AI == 0)
					{
					hit(clicked);	
					turn = 2;	
					boardobfuscator();
					turnlabel.setText("Player 2 turn");
					}
					else if(AI == 1)//Same as if the AI is off, but the AI's ships arent revealed and the AI makes a counter move
					{
					hit(clicked);
					clicked.setIcon(new ImageIcon(clicked.getimage()));
					rhorizontal = rand.nextInt(10);
					rvertical = rand.nextInt(10);
					turn = 2;
					AIFire();
					turn = 1;
					}
				}
				else if(gethasship(clicked) == false)
				{
				    if(AI == 0)
				    {
					miss(clicked);
					turn = 2;	
					boardobfuscator();
					turnlabel.setText("Player 2 turn");
				    }
				    else if(AI == 1)
				    {
				    miss(clicked);
				    rhorizontal = rand.nextInt(10);
					rvertical = rand.nextInt(10);
					turn = 2;
					if(AIfiringline == 0)
					{
					AIFire();
					}
					else if(AIfiringline == 1)
					{
					AIFire2();
					}
					else if(AIfiringline == 2)
					{
					AIFire3();
					}
					else if(AIfiringline == 3)
					{
					AIFire4();
					}
					else if(AIfiringline == 4)
					{
					AIFire5();
					}
					turn = 1;
				    }
				}
			}
			else if(preptime == false && turn == 2  && clicked == board[clicked.gethorizontal()/51][clicked.getvertical()/51])//this is if AI isn't present for two player support
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
			//direction=what way your ships are facing on placement
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
public void AIplacerboat()//places a patrol boat, method is very similar
{
	boolean placed = false;//until this is true, the method will repeat itself
	while(placed == false)
	{
		rdirection = rand.nextInt(4);//randomly chooses an orientation for the ship  
		switch(rdirection)
		{
			case 0: AIdirection = "left"; break;
			case 1: AIdirection = "right"; break;
			case 2: AIdirection = "up"; break;
			case 3: AIdirection = "down"; break;
		}
		System.out.println(AIdirection);
		System.out.println("horizontal generated"+rhorizontal);
		System.out.println("vertical generated"+rvertical);
		if(AItakencheck(rhorizontal, rvertical, 2) == false)
		{
			if(AIdirection.equals("left") && rhorizontal >= 1)
			{
				System.out.println("Place");
				Ship AIpatrolboat = new Ship(rhorizontal, rvertical, 2, AIdirection, AIboard);//makes AI place a boat at a randomly chosen origin point
				shipprocession++;
				placed = true;//makes the method stop
			}
			if(AIdirection.equals("right") && rhorizontal<=8)
			{
				System.out.println("Place");
				Ship AIpatrolboat = new Ship(rhorizontal, rvertical, 2, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("up") && rvertical>=1)
			{
				System.out.println("Place");
				Ship AIpatrolboat = new Ship(rhorizontal, rvertical, 2, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
		    if(AIdirection.equals("down") && rvertical<=8)
			{
		    	System.out.println("Place");
				Ship AIpatrolboat = new Ship(rhorizontal, rvertical, 2, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
		}
	  }
}
	public void AIplacercruiser()
	{
	boolean placed = false;
	while(placed == false)
	{
		rdirection = rand.nextInt(4);  
		switch(rdirection)
		{
			case 0: AIdirection = "left"; break;
			case 1: AIdirection = "right"; break;
			case 2: AIdirection = "up"; break;
			case 3: AIdirection = "down"; break;
		}
		System.out.println(AIdirection);
		rhorizontal = rand.nextInt(10);//randomly chooses a horizontal value for ship start point
		rvertical = rand.nextInt(10);// randomly chooses a vertical value for ship start point
		System.out.println("horizontal generated"+rhorizontal);
		System.out.println("vertical generated"+rvertical);
		if(AItakencheck(rhorizontal, rvertical, 3) == false)
		{
			if(AIdirection.equals("left") && rhorizontal >= 2)
			{
				System.out.println("Place");
				Ship AIcruiser = new Ship(rhorizontal, rvertical, 3, AIdirection, AIboard);
				shipprocession++;
				placed = true;
			}
			
			if(AIdirection.equals("right") && rhorizontal<=7)
			{
				System.out.println("Place");
				Ship AIcruiser = new Ship(rhorizontal, rvertical, 3, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("up") && rvertical>=2)
			{
				System.out.println("Place");
				Ship AIcruiser = new Ship(rhorizontal, rvertical, 3, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("down") && rvertical<=7)
			{
				System.out.println("Place");
				Ship AIcruiser = new Ship(rhorizontal, rvertical, 3, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
		}
	  }	
	}
	public void AIplacerdestroyer()
	{
		boolean placed = false;
	while (placed == false)
	{
		rdirection = rand.nextInt(4);  
		switch(rdirection)
		{
			case 0: AIdirection = "left"; break;
			case 1: AIdirection = "right"; break;
			case 2: AIdirection = "up"; break;
			case 3: AIdirection = "down"; break;
		}
		rhorizontal = rand.nextInt(10);
		rvertical = rand.nextInt(10);
		System.out.println(AIdirection);
		System.out.println("horizontal generated"+rhorizontal);
		System.out.println("vertical generated"+rvertical);
		if(AItakencheck(rhorizontal, rvertical, 3) == false)
		{
			if(AIdirection.equals("left") && rhorizontal >= 2)
			{
				System.out.println("Place");
				Ship AIdestroyer = new Ship(rhorizontal, rvertical, 3, AIdirection, AIboard);
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("right") && rhorizontal<=7)
			{
				System.out.println("Place");
				Ship AIdestroyer = new Ship(rhorizontal, rvertical, 3, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("up") && rvertical>=2)
			{
				System.out.println("Place");
				Ship AIdestroyer = new Ship(rhorizontal, rvertical, 3, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("down") && rvertical<=7)
			{
				System.out.println("Place");
				Ship AIdestroyer = new Ship(rhorizontal, rvertical, 3, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
		}
	  }
	}
	public void AIplacerbattleship()
	{
		boolean placed = false;
		while(placed == false)
		{
		rdirection = rand.nextInt(4);  
		switch(rdirection)
		{
			case 0: AIdirection = "left"; break;
			case 1: AIdirection = "right"; break;
			case 2: AIdirection = "up"; break;
			case 3: AIdirection = "down"; break;
		}
		rhorizontal = rand.nextInt(10);
		rvertical = rand.nextInt(10);
		System.out.println(AIdirection);
		System.out.println("horizontal generated"+rhorizontal);
		System.out.println("vertical generated"+rvertical);
		if(AItakencheck(rhorizontal, rvertical, 4) == false)
		{
			if(AIdirection.equals("left") && rhorizontal >= 3)
			{
				System.out.println("Place");
				Ship AIbattleship = new Ship(rhorizontal, rvertical, 4, AIdirection, AIboard);
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("right") && rhorizontal<=6)
			{
				System.out.println("Place");
				Ship AIbattleship = new Ship(rhorizontal, rvertical, 4, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("up") && rvertical>=3)
			{
				System.out.println("Place");
				Ship AIbattleship = new Ship(rhorizontal, rvertical, 4, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("down") && rvertical<=6)
			{
				System.out.println("Place");
				Ship AIbattleship = new Ship(rhorizontal, rvertical, 4, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
		}
	   }
	}
	public void AIplacercarrier()
	{
		boolean placed = false;
	    while (placed == false)
	    {
		rdirection = rand.nextInt(4);  
		switch(rdirection)
		{
			case 0: AIdirection = "left"; break;
			case 1: AIdirection = "right"; break;
			case 2: AIdirection = "up"; break;
			case 3: AIdirection = "down"; break;
		} 
		rhorizontal = rand.nextInt(10);
		rvertical = rand.nextInt(10);
		System.out.println(AIdirection);
		System.out.println("horizontal generated"+rhorizontal);
		System.out.println("vertical generated"+rvertical);
		if(AItakencheck(rhorizontal, rvertical, 5) == false)
		{
			if(AIdirection.equals("left") && rhorizontal >= 4)
			{
				System.out.println("Place");
				Ship AIcarrier = new Ship(rhorizontal, rvertical, 5, AIdirection, AIboard);
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("right") && rhorizontal<=5)
		    {
				System.out.println("Place");
				Ship AIcarrier = new Ship(rhorizontal, rvertical, 5, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("up") && rvertical>=4)
			{
				System.out.println("Place");
				Ship AIcarrier = new Ship(rhorizontal, rvertical, 5, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
			if(AIdirection.equals("down") && rvertical<=5)
			{
				System.out.println("Place");
				Ship AIcarrier = new Ship(rhorizontal, rvertical, 5, AIdirection, AIboard); 
				shipprocession++;
				placed = true;
			}
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
					AIboard[x][y].setIcon(new ImageIcon("genericbattleshiptile.png"));//this clears afterimages on the player 2 board
				}
			}
		}
	}
	public void AIFire()//This method should make the AI make a move
	{
		//TODO it is targeting tiles more than once, and the condition you thought would correct it would not
		System.out.println("Executing AIFire");
		rhorizontal = rand.nextInt(10);
		rvertical = rand.nextInt(10);
		System.out.println("AI is targeting" +rhorizontal+","+rvertical);
		System.out.println("legal tile has ship ="+gethasship(board[rhorizontal][rvertical]));
		System.out.println("legaltile has been targeted = "+board[rhorizontal][rvertical].gethasbeentargeted());
		if(board[rhorizontal][rvertical].gethasbeentargeted() == true)
		{
			System.out.println("Executing AIFire Recursion");
			AIFire();
		}
		else if(board[rhorizontal][rvertical].gethasship() == false && board[rhorizontal][rvertical].gethasbeentargeted() == false)
		{
			System.out.println("executing miss");
			miss(board[rhorizontal][rvertical]);
		}
		else if(board[rhorizontal][rvertical].gethasship() == true && board[rhorizontal][rvertical].gethasbeentargeted() == false)
		{
			System.out.println("executing hit");
			hit(board[rhorizontal][rvertical]);
			AIreferenceh = rhorizontal;//This is used to smarten up the AI targeting scheme if it manages a hit
			AIreferencev = rvertical;
			AIfiringline++;//this tells the game to use AIFire 2 next time
			System.out.println("AI Firing line was"+ AIfiringline);
			System.out.println("AIfiringline gains one");
		}
		
	  
	}
	public void AIFire2()
	{
	  if(AIreferenceh+1<=9)
	  {
		System.out.println("Executing AIFire2");
		if(directionconfirmed == false && board[AIreferenceh+1][AIreferencev].gethasship() == false && board[AIreferenceh+1][AIreferencev].gethasbeentargeted() == false && AIreferenceh+1<=9)
		{
			System.out.println("executing miss");
			miss(board[AIreferenceh+1][AIreferencev]);
			AIfiringline++;
			System.out.println("AI Firing line was"+ AIfiringline);
			System.out.println("AIfiringline gains one");
		}
		else if(directionconfirmed == true && board[AIreferenceh+1][AIreferencev].gethasship() == false && board[AIreferenceh+1][AIreferencev].gethasbeentargeted() == false && AIreferenceh+1<=9)
		{
			System.out.println("executing miss");
			miss(board[AIreferenceh+1][AIreferencev]);
			directionconfirmed = false;
			AIfiringline = 0;
			System.out.println("AI Firing line was"+ AIfiringline);
			System.out.println("AIfiringline reset");
		}
		else if(board[AIreferenceh+1][AIreferencev].gethasship() == true && board[AIreferenceh+1][AIreferencev].gethasbeentargeted() == false && AIreferenceh+1<=9)
		{
			System.out.println("executing hit");
			hit(board[AIreferenceh+1][AIreferencev]);
			System.out.println("Direction confirmed has been set to true");
			directionconfirmed = true;
			AIreferenceh++;//This is used to smarten up the AI targeting scheme if it manages a hit		
		}
		else
		{
			AIFire3();
		}
	  }
		else if(AIreferenceh+1>=10)
		{
			AIFire3();
		}
	}
	public void AIFire3()
	{
	  if(AIreferencev+1<=9)
	  {  
		System.out.println("Executing AIFire3");
		if(directionconfirmed == false && board[AIreferenceh][AIreferencev+1].gethasship() == false && board[AIreferenceh][AIreferencev+1].gethasbeentargeted() == false && AIreferencev+1<=9 )
		{
			System.out.println("executing miss");
			miss(board[AIreferenceh+1][AIreferencev]);
			AIfiringline++;
			System.out.println("AI Firing line was"+ AIfiringline);
			System.out.println("AIfiringline gains one");
		}
		else if(directionconfirmed == true &&board[AIreferenceh][AIreferencev+1].gethasship() == false && board[AIreferenceh][AIreferencev+1].gethasbeentargeted() == false && AIreferencev+1<=9 )
		{
			System.out.println("executing miss");
			miss(board[AIreferenceh][AIreferencev+1]);
			directionconfirmed = false;
			AIfiringline = 0;
			System.out.println("AI Firing line was"+ AIfiringline);
			System.out.println("AIfiringline reset");
		}
		else if(board[AIreferenceh][AIreferencev+1].gethasship() == true && board[AIreferenceh][AIreferencev+1].gethasbeentargeted() == false && AIreferencev+1<=9 )
		{
			System.out.println("executing hit");
			hit(board[AIreferenceh][AIreferencev+1]);
			System.out.println("Direction confirmed has been set to true");
			directionconfirmed = true;
			AIreferencev++;
			
		}
		else
		{
			AIFire4();
		}
	  }
		else if(AIreferencev+1>=10)
		{
			AIFire4();
		}
	}
	public void AIFire4()
	{
	  if(AIreferenceh-1>=0)
	  {
		System.out.println("Executing AIFire4");
		if(directionconfirmed == false && board[AIreferenceh-1][AIreferencev].gethasship() == false && board[AIreferenceh-1][AIreferencev].gethasbeentargeted() == false && AIreferenceh-1>=0)
		{
			System.out.println("executing miss");
			miss(board[AIreferenceh-1][AIreferencev]);
			AIfiringline++;
			System.out.println("AI Firing line was"+ AIfiringline);
			System.out.println("AIfiringline gains one");
		}
		else if(directionconfirmed == true && board[AIreferenceh-1][AIreferencev].gethasship() == false && board[AIreferenceh-1][AIreferencev].gethasbeentargeted() == false && AIreferenceh-1>=0)
		{
			System.out.println("executing miss");
			miss(board[AIreferenceh-1][AIreferencev]);
			directionconfirmed = false;
			AIfiringline = 0;
			System.out.println("AI Firing line was"+ AIfiringline);
			System.out.println("AIfiringline reset");
		}
		else if(board[AIreferenceh-1][AIreferencev].gethasship() == true && board[AIreferenceh-1][AIreferencev].gethasbeentargeted() == false && AIreferenceh-1>=0)
		{
			System.out.println("executing hit");
			hit(board[AIreferenceh-1][AIreferencev]);
			System.out.println("Direction confirmed has been set to true");
			directionconfirmed = true;
			AIreferenceh--;//This is used to smarten up the AI targeting scheme if it manages a hit		
		}
		else
		{
			AIFire5();
		}
	  }
		else if(AIreferenceh-1<0)
		{
			AIFire5();//if its out of bounds, it should just pick a tile at random
		}
	}
	public void AIFire5()
	{
	  if(AIreferencev-1>=0)
	  {	  
		System.out.println("Executing AIFire5");
		if(board[AIreferenceh][AIreferencev-1].gethasship() == false && board[AIreferenceh][AIreferencev-1].gethasbeentargeted() == false && AIreferencev-1>=0)
		{
			System.out.println("executing miss");
			miss(board[AIreferenceh][AIreferencev-1]);
			AIfiringline = 0;
			directionconfirmed = false;
			System.out.println("AI Firing line was"+ AIfiringline);
			System.out.println("AIfiringline reset");
		}
		else if(board[AIreferenceh][AIreferencev-1].gethasship() == true && board[AIreferenceh][AIreferencev-1].gethasbeentargeted() == false && AIreferencev-1>=0)
		{
			System.out.println("executing hit");
			hit(board[AIreferenceh][AIreferencev-1]);	
			System.out.println("Direction confirmed has been set to true");
			directionconfirmed = true;
			AIreferencev--;
			
		}
		else
		{
			AIFire();
		}
	  }
		else if(AIreferencev-1<0)
		{
			AIFire();//if its out of bounds, it should just pick a tile at random
		}
	
	}
	public void miss(Battleshiptile clicked)//marks a tile as missed
	{
		clicked.setBorder(BorderFactory.createLineBorder(Color.green));	
		clicked.sethasbeentargeted(true);
	}
	public void hit(Battleshiptile clicked)//marks a tile as hit
	{
		if(turn == 1)
		{
			greenhealth--;//reduces player 2's hit points for a more tangible scorecounter
			Greenscore.setText("Green has"+" "+greenhealth+" "+"Hit Points");	
		}
		else if(turn == 2)
		{
			redhealth--;//reduces player 1's hit points for a more tangible scorecounter
			Redscore.setText("Red has"+" "+redhealth+" "+"Hit Points");
		}
		clicked.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		clicked.sethasbeentargeted(true);//makes sure a particular tile can't be targeted more than once
	}
	public boolean takencheck(int horizontal, int vertical, int length)//makes sure the area a ship would occupy is empty
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
	public boolean AItakencheck(int horizontal, int vertical, int length)//prevents the AI from placing a ship in an occupied space
	{
		boolean taken = false;
		for(int x = 0; x<=length; x++)
		{
		  if(AIdirection.equals("left")&& horizontal-x>=0 && shipprocession <=5)
		  {
			if(board[horizontal-x][vertical].gethasship() == true)
			{
				taken = true;	
			}
		  }		  
		  else if(AIdirection.equals("right")&& horizontal+x<=9&& shipprocession <= 5)
		  {
			    if(board[horizontal+x][vertical].gethasship() == true)
				{
					taken = true;	
				}
		  }
		  else if(AIdirection.equals("up")&& vertical-x>=0 && shipprocession <= 5)
		  {
			  if(board[horizontal][vertical-x].gethasship() == true)
			  {
					taken = true;	
			  }
		  }
		  else if(AIdirection.equals("down")&& vertical+x<=9 && shipprocession <= 5)
		  {
			  if(board[horizontal][vertical+x].gethasship() == true)
			  {
					taken = true;	
			  }
		  }
		  else if(AIdirection.equals("left")&& horizontal-x>=0 && shipprocession >5 && shipprocession <= 10)
		  {
			if(AIboard[horizontal-x][vertical].gethasship() == true)
			{
				taken = true;	
			}
		  }		  
		  else if(AIdirection.equals("right")&& horizontal+x<=9&& shipprocession >5 && shipprocession <= 10)
		  {
			    if(AIboard[horizontal+x][vertical].gethasship() == true)
				{
					taken = true;	
				}
		  }
		  else if(AIdirection.equals("up")&& vertical-x>=0 && shipprocession >5 && shipprocession <= 10)
		  {
			  if(AIboard[horizontal][vertical-x].gethasship() == true)
			  {
					taken = true;	
			  }
		  }
		  else if(AIdirection.equals("down")&& vertical+x<=9 && shipprocession >5 && shipprocession <= 10)
		  {
			  if(AIboard[horizontal][vertical+x].gethasship() == true)
			  {
					taken = true;	
			  }
		  }
		}
		return taken;
	}
	public void boardobfuscator()//used in 2 player to hide a sides ships
	{
		for(int x = 0; x<=9; x++)
		{
			for(int y = 0; y<=9; y++)
			{
				if(turn == 1)
				{
					board[x][y].setIcon(new ImageIcon(board[x][y].getimage()));
					if(AIboard[x][y].gethasship() == true)
					{
					AIboard[x][y].setIcon(new ImageIcon("genericbattleshiptile.png"));
					}		
				}
				else if (turn == 2)
				{
					AIboard[x][y].setIcon(new ImageIcon(AIboard[x][y].getimage()));
					if(board[x][y].gethasship() == true)
					{
					board[x][y].setIcon(new ImageIcon("genericbattleshiptile.png"));
					}
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
			if(preptime == true && shipprocession == 1)//if it is time to place ships, place the patrol boat(These show where a boat will land by changing images)
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
			if(preptime == true && shipprocession == 1)//removes the trailing ship from the area your cursor left
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
		//TODO AIFIRING ISSUE(MAKE TILES ONLY CLICKABLE ONCE), ESTABLISH AI FIRING PATTERN	
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
	public void mousePressed(MouseEvent arg0) //DEFUNCT?
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
	public void mouseReleased(MouseEvent arg0) //DEFUNCT
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
