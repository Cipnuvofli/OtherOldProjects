import java.util.Random;

public class APRgen 
{

	public static void main(String args[])
	{
		String moves[] = new String[17];
		moves[0]= "Down Punch";
		moves[1]= "HammerFist head";
		moves[2]= "Reverse Punch chest";
		moves[3]= "Back Fist head";
		moves[4]= "Ridge Hand Head";
		moves[5]= "Ridge Hand Groin";
		moves[6]= "Front Snap Kick";
		moves[7]= "Side Snap Kick";
		moves[8]= "Back Heel Kick";
		moves[9]= "Outside Crescent Kick";
		moves[10]= "Roundhouse Front Leg";
		moves[11]= "Roundhouse Rear Leg";
		moves[12]= "Hook Kick"; 
		moves[13]= "Turn Kick 1";
		moves[14]= "Turn Kick 2";
		moves[15]= "Spin Heel Kick";
		
		 Random rand = new Random();//randomly changed to determine word
		 int roll = 0;// randomly changed to determine word
		 
		 for(int x = 0; x<= 10; x++)
		 {
			 roll = rand.nextInt(16);
			 System.out.println(moves[roll]+"+"+moves[rand.nextInt(16)]+"+"+moves[rand.nextInt(16)]);
			
			 
		 }
	}	
		
		

}
