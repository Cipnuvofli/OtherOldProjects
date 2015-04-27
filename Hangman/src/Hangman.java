import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
public class Hangman extends JFrame implements ActionListener
{
	private String[] words = new String[42];// all possible words
	private int health = 5;//number of wrong guesses player has left
	private JLabel Hangman = new JLabel();//displays image 
	private JTextField playerguess = new JTextField();//player enters their guess there
	private JTextArea worddisplay = new JTextArea();//displays dashes for blank answers
	private JButton Reset = new JButton();//resets game
	private JButton inputguess = new JButton();
	private JLabel guess = new JLabel();//labels guessing board
	private String word = new String();//The word the computer has chosen
	private String word1 = new String();//The word obscured by hyphens
	private Random rand = new Random();//randomly changed to determine word
	private int roll = 0;// randomly changed to determine word
	public Hangman()
	{
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
			
		words[0] = "Denarian";
		words[1] = "Doom";
		words[2] = "Supercalifragilisticexpialidocious";
		words[3] = "Sickle";
		words[4] = "THIS IS SPARTA";
		words[5] = "Doom";
		words[6] = "Sidereal";
		words[7] = "Massive";
		words[8] = "Irken";
		words[9] = "Cipnu";
		words[10] = "Hawk";
		words[11] = "Titania";
		words[12] = "Legolas";
		words[13] = "Gandalf";
		words[14] = "Python";
		words[15] = "Disintegrate";
		words[16] = "Shadow Walk";
		words[17] = "Blade Barrier";
		words[18] = "Furycrafting";
		words[19] = "I think therefore I am";
		words[20] = "Sleipnir";
		words[21] = "Odin";
		words[22] = "Ragnarok";
		words[23] = "So this is how liberty dies with thundrous applause";
		words[24] = "What if I'm the bad guy";
		words[25] = "Hawking Radiation";
		words[26] = "Cardassian";
		words[27] = "Transporter Accident";
		words[28] = "Pulp Fiction";
		words[29] = "Invader Zim";
		words[30] = "Malcolm Reynolds";
		words[31] = "River Tam";
		words[32] = "Snow Crash";
		words[33] = "Metaverse";
		words[34] = "Eberron";
		words[35] = "Bokken";
		words[36] = "Genshin";
		words[37] = "Saifa";
		words[38] = "Wail of the Banshee";
		words[39] = "No Squidward a banshee screams like this";
		words[40] = "For the lulz";
		words[41] = "Rickroll";
		
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Hangman");
		setSize(550, 800);
		setResizable(false);
		setLocation(10, 20);
		
		playerguess = new JTextField();//planning on making this only read the first letter, this is intentional
		playerguess.setBounds(0, 0, 90, 300);
		contentPane.add(playerguess);
		
		worddisplay = new JTextArea();
		worddisplay.setBounds(200, 0, 340, 300);
		contentPane.add(worddisplay);
		
		roll = rand.nextInt(42);
		word = words[roll];
		
		word1 = words[roll];
		System.out.println("word1 was "+word1);
		textobscurer(word1);
		System.out.println("word1 is "+word1);
		System.out.println(word);
		worddisplay.setText(word1);
		
		
		
		Hangman = new JLabel();// Declares whos turn it is
		Hangman.setIcon(new ImageIcon("1.png"));
		Hangman.setBounds(150, 150, 100, 100);
		contentPane.add(Hangman);
		
		guess = new JLabel("input letter guess in above field on left");
		guess.setBounds(0, 300, 250, 50);
		contentPane.add(guess);
		
		inputguess = new JButton("Click here to guess a letter");//resets game
		inputguess.setBounds(20, 690, 300, 50);
		inputguess.addActionListener(this);
		contentPane.add(inputguess);
		
		Reset = new JButton("Reset");//resets game
		Reset.setBounds(350, 690, 100, 50);
		Reset.addActionListener(this);
		contentPane.add(Reset);
		
	}
	public static void main(String[] args) 
	{
		
		Hangman frame= new Hangman();
		frame.setVisible(true);

	}
	public void textobscurer(String obscured)
	{
		String Text = new String(obscured);
		Text = Text.toLowerCase().trim();
		char chars[] = Text.toCharArray();
		int Textsize = Text.length();
		for(int x = 0; x< Textsize; x++)
		{
			
	
				if(chars[x] == 'a')
				{
					chars[x] = '-';
				}
				else if(chars[x] == 'b')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'c')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'd')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'e')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'f')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'g')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'h')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'i')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'j')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'k')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'l')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'm')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'n')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'o')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'p')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'q')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'r')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 's')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 't')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'u')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'v')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'w')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'x')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'y')
				{
					chars[x] = '-';
				}	
				else if(chars[x] == 'z')
				{
					chars[x] = '-';
				}
		}
		String obscured =  new String(charArray);

			
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JButton clicked = (JButton)e.getSource(); 
		if(e.getSource() instanceof JButton)
		{
			if(clicked == Reset)
			{
				
			}
			
			if(clicked == inputguess)
			{
				String Text = new String(playerguess.getText());
				Text = Text.toLowerCase().trim();
				char chars[] = Text.toCharArray();
				int Textsize = Text.length();
				
			}
		}
		
	}

}
