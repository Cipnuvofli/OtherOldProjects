
public class TileConfigEvent 
{
	private Object sender;
	public TileConfigEvent(Object s)
	{
		sender = s;
	}
	public Object getSource()
	{return sender;}
}
