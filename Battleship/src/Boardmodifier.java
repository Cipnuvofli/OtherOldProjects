
public interface Boardmodifier 
{
		
			public abstract void sethasship(int horizontal, int vertical, boolean condition);
			
			public abstract boolean gethasship(Battleshiptile Tile);
			
			public abstract void setshiphit(int horizontal, int vertical, boolean condition);
			
			public abstract boolean getshiphit(Battleshiptile Tile);
			
			public abstract void setboardicon(int horizontal, int vertical, String Image);
			
			public abstract void getboardicon(Battleshiptile Tile);
		
}
