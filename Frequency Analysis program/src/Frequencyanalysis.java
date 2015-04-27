import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


public class Frequencyanalysis extends JFrame implements ActionListener
{
	
	private JTextArea Ciphertext = new JTextArea();
	private JTextArea Results = new JTextArea();
	private JLabel Input = new JLabel();
	private JLabel Output = new JLabel();
	private JButton Analyze = new JButton();
	private JButton Hash = new JButton();
	private char comparator[] = new char[26];
	private String[] basestring = new String[26];
	private String referencestring = new String();

	
	
	private int As = 0;
	private int Bs = 0;
	private int Cs = 0;
	private int Ds = 0;
	private int Es = 0;
	private int Fs = 0;
	private int Gs = 0;
	private int Hs = 0;
	private int Is = 0;
	private int Js = 0;
	private int Ks = 0;
	private int Ls = 0;
	private int Ms = 0;
	private int Ns = 0;
	private int Os = 0;
	private int Ps = 0;
	private int Qs = 0;
	private int Rs = 0;
	private int Ss = 0;
	private int Ts = 0;
	private int Us = 0;
	private int Vs = 0;
	private int Ws = 0;
	private int Xs = 0;
	private int Ys = 0;
	private int Zs = 0;
	public Frequencyanalysis()
	{
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Frequency Analyzer");
		setSize(800, 768);
		setResizable(false);
		setLocation(10, 20);
		
		Ciphertext = new JTextArea("Put your text here for analysis");
		Ciphertext.setLineWrap(true);
		Ciphertext.setBounds(100,200, 200, 420);
		contentPane.add(Ciphertext);
		
		Results = new JTextArea("The Frequencies are here");
		Results.setLineWrap(true);
		Results.setBounds(400, 200, 200, 420);
		contentPane.add(Results);
			
		Input= new JLabel("Input Text Here:");
		Input.setBounds(100,160, 100, 30);
		contentPane.add(Input);

		Output = new JLabel("Results are Here:");
		Output.setBounds(400, 160, 100, 50);
		contentPane.add(Output);
		
		Analyze = new JButton("Analyze");
		Analyze.setBounds(300, 610, 100, 50);
		Analyze.addActionListener(this);
		contentPane.add(Analyze);
		
		Hash = new JButton("Hash");
		Hash.setBounds(300, 670, 100, 50);
		Hash.addActionListener(this);
		contentPane.add(Hash);
		
	
	}
	
	public static void main(String[] args)
	{
			// this spawns the window that holds everything
				Frequencyanalysis frame= new Frequencyanalysis();
				frame.setVisible(true);
		 	
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() instanceof JButton)
		{
			JButton clicked = (JButton)e.getSource(); 
			if(e.getSource() instanceof JButton)
			{
				
				if (clicked == Analyze)
				{
					String Text = new String(Ciphertext.getText());
					Text = Text.toLowerCase().trim();
					char chars[] = Text.toCharArray();
					int Textsize = Text.length();
					for(int x = 0; x<Textsize; x++)
					{
			
						if(chars[x] == 'a')
						{
							As++;
						}
						else if(chars[x] == 'b')
						{
							Bs++;
						}	
						else if(chars[x] == 'c')
						{
							Cs++;
						}	
						else if(chars[x] == 'd')
						{
							Ds++;
						}	
						else if(chars[x] == 'e')
						{
							Es++;
						}	
						else if(chars[x] == 'f')
						{
							Fs++;
						}	
						else if(chars[x] == 'g')
						{
							Gs++;
						}	
						else if(chars[x] == 'h')
						{
							Hs++;
						}	
						else if(chars[x] == 'i')
						{
							Is++;
						}	
						else if(chars[x] == 'j')
						{
							Js++;
						}	
						else if(chars[x] == 'k')
						{
							Ks++;
						}	
						else if(chars[x] == 'l')
						{
							Ls++;
						}	
						else if(chars[x] == 'm')
						{
							Ms++;
						}	
						else if(chars[x] == 'n')
						{
							Ns++;
						}	
						else if(chars[x] == 'o')
						{
							Os++;
						}	
						else if(chars[x] == 'p')
						{
							Ps++;
						}	
						else if(chars[x] == 'q')
						{
							Qs++;
						}	
						else if(chars[x] == 'r')
						{
							Rs++;
						}	
						else if(chars[x] == 's')
						{
							Ss++;
						}	
						else if(chars[x] == 't')
						{
							Ts++;
						}	
						else if(chars[x] == 'u')
						{
							Us++;
						}	
						else if(chars[x] == 'v')
						{
							Vs++;
						}	
						else if(chars[x] == 'w')
						{
							Ws++;
						}	
						else if(chars[x] == 'x')
						{
							Xs++;
						}	
						else if(chars[x] == 'y')
						{
							Ys++;
						}	
						else if(chars[x] == 'z')
						{
							Zs++;
						}	
					}
					Results.setText("A = "+As+"\n"+
									"B = "+Bs+"\n"+
									"C = "+Cs+"\n"+
									"D = "+Ds+"\n"+
									"E = "+Es+"\n"+
									"F = "+Fs+"\n"+
									"G = "+Gs+"\n"+
									"H = "+Hs+"\n"+
									"I = "+Is+"\n"+
									"J = "+Js+"\n"+
									"K = "+Ks+"\n"+
									"L = "+Ls+"\n"+
									"M = "+Ms+"\n"+
									"N = "+Ns+"\n"+
									"O = "+Os+"\n"+
									"P = "+Ps+"\n"+
									"Q = "+Qs+"\n"+
									"R = "+Rs+"\n"+
									"S = "+Ss+"\n"+ 
									"T = "+Ts+"\n"+
									"U = "+Us+"\n"+
									"V = "+Vs+"\n"+
									"W = "+Ws+"\n"+
									"X = "+Xs+"\n"+
									"Y = "+Ys+"\n"+
									"Z = "+Zs);
					 As = 0;
					 Bs = 0;
					 Cs = 0;
					 Ds = 0;
					 Es = 0;
					 Fs = 0;
					 Gs = 0;
					 Hs = 0;
					 Is = 0;
					 Js = 0;
					 Ks = 0;
					 Ls = 0;
					 Ms = 0;
					 Ns = 0;
					 Os = 0;
					 Ps = 0;
					 Qs = 0;
					 Rs = 0;
					 Ss = 0;
					 Ts = 0;
					 Us = 0;
					 Vs = 0;
					 Ws = 0;
					 Xs = 0;
					 Ys = 0;
					 Zs = 0;
					
				}
				if(clicked == Hash)
				{
					String Text = new String(Ciphertext.getText());
					Text = Text.toLowerCase().trim();
					char chars[] = Text.toCharArray();
					int Textsize = Text.length();
					
				}
			}
		}
		
	}

	
	
	
	
}
