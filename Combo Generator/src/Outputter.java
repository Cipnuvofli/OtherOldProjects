
public class Outputter 
{
	
		
	
	public static void main(String args[])
	{
		String moves[] = new String[17];
		moves[0]= "D";
		moves[1]= "HF";
		moves[2]= "RP";
		moves[3]= "BF";
		moves[4]= "RHH";
		moves[5]= "RHG";
		moves[6]= "FSK";
		moves[7]= "SSK";
		moves[8]= "BHK";
		moves[9]= "OCK";
		moves[10]= "RFL";
		moves[11]= "RRL";
		moves[12]= "HK";
		moves[13]= "TK1";
		moves[14]= "TK2";
		
		for(int x = 0; x<= 14; x++)
		{
			for(int y = 0; y<= 14; y++)
			{
				for(int z = 0; z<= 14; z++)
				{
					if(moves[x] != moves[y] && moves[y] != moves[z] && moves[x]!= moves[z])
					{
					System.out.println(moves[x]+"+"+moves[y]+"+"+moves[z]);
					}
				}
			
			}
			
		
		}
		
	}

}
