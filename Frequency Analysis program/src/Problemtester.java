import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Problemtester 
{

	public static void main(String[] args) 
	{
		int sum = 0;
		
		for(int count = 1; count<=5; count++)
		{
			String input = JOptionPane.showInputDialog("Type a Number");
			int number = Integer.parseInt(input);
			if(number <= 0)
			{
				break;
			}
			System.out.println("sum was"+sum);
			sum = sum + number;
			System.out.println("Sum is"+sum);
		}
		System.out.print(sum);
	}

}
