package josephpenrod.Flashcard2;


import android.os.Parcel;
import android.os.Parcelable;

public class Pair implements Parcelable
{
	String pFront;
	String pBack;
	public Pair(String front, String back) 
	{
		pFront = front;
		pBack = back;
	}
	public Pair(Parcel q)
	{
       String[] FrontandBack = new String[2];
       q.readStringArray(FrontandBack);
       
       this.pFront = FrontandBack[0];
       this.pBack = FrontandBack[1];
	}
	public String getpFront()
	{
		return pFront;
	}
	public String getpBack()
	{
		return pBack;
		
	}
	@Override
	public int describeContents() 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Pair> CREATOR = new Parcelable.Creator<Pair>() 
    {
        public Pair createFromParcel(Parcel in) 
        {
            return new Pair(in);
        }

        public Pair[] newArray(int size) 
        {
            return new Pair[size];
        }
    };
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeStringArray(new String[] {this.pFront,
											this.pBack});
		
		
	}
	
}
