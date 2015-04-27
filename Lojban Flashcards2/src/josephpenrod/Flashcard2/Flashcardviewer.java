package josephpenrod.Flashcard2;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class Flashcardviewer extends Activity 
{
	   static int index = 0;
	   static int flag = 0;
	   public void onCreate(Bundle savedInstanceState) 
	   {
		 
	
		    final ArrayList <Pair> Cards = new ArrayList<Pair>();
		    Cards.add(0, new Pair("Placeholder", "Placeholder"));
		   
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.flashcardview);

	        Log.v("this", "contentview set by oncreate");
	       
	        final Flashcardview flash = (Flashcardview) this.findViewById(R.id.flashcardview);
	        flash.Writecard(Cards.get(index));
	        final String PAIR_DETECTED = "josephpenrod.Flashcard2.action.PAIR_DETECTED";
	        final String ACTION_DELETE = "android.intent.action.DELETE";
	        Button flip = (Button)this.findViewById(R.id.Flip);
	        Button back = (Button)this.findViewById(R.id.Back);
	        Button forward = (Button)this.findViewById(R.id.Forward);
	        Button Save = (Button)this.findViewById(R.id.button1);
	        Button Delete = (Button)this.findViewById(R.id.button2);
	        IntentFilter Filter = new IntentFilter();
	        Filter.addAction(PAIR_DETECTED);
	        IntentFilter Delfilter = new IntentFilter();
	        Delfilter.addAction(ACTION_DELETE);
	        
	        BroadcastReceiver receiver = new BroadcastReceiver() 
	        {
	            @Override
	            public void onReceive(Context context, Intent intent)
	            {  
	            	 Log.v("this", "Onreceive has been called in a dynamically declared reciever");
	         		if((Pair)intent.getParcelableExtra("card")!= null)
	         		{
	         				if(index == 0 && flag == 1)
	         				{
	         				Log.v("this", "Placeholder altered");
	         				Cards.set(0, (Pair)intent.getParcelableExtra("card"));
	         				 flash.setside(true);
		                     flash.Writecard(Cards.get(index));
		                     
	         				}
	         				Cards.add((Pair)intent.getParcelableExtra("card"));
	         				if(flag == 1)
	         				{
	         					flag = 0;
			                    Cards.remove(1);
	         				}
	         				Log.v("this", "The parcelable extra has been added to the arraylist");
	         		}

	            }
	        };
	        registerReceiver(receiver, Filter);
	        BroadcastReceiver remotedel = new BroadcastReceiver()
	        {
	        	@Override
	        	public void onReceive(Context context, Intent intent)
	        	{
	        		if(Cards.size()!= 0)
	        		{
	        			Log.v("this", "The if statement has been entered");
	        			for(int x = 1; x<Cards.size(); x++)
	        			{
	        				Cards.remove(x);
	        			}
	        			index = 0;
	        			flag = 1;
	        			flash.Writecard(Cards.get(index));
	        			flash.invalidate();
	        		}
	        		
	        	}
	        	
	        };
	        registerReceiver(remotedel, Delfilter);
	        OnClickListener click = new OnClickListener() 
	         {
	        	 public void onClick(View v) 
	        	 {
                     flash.flip();
                     flash.invalidate();
	        	 }
	         };
	         flip.setOnClickListener(click);
	         OnClickListener del = new OnClickListener()
	         {
	        	 public void onClick(View v)
	        	 {
	        		if(index>0)
	        		{
	        		 Cards.remove(index);
	        		 index--;
	        		 System.out.println("Index is"+index);
	        		 flash.setside(true);
	        		 flash.Writecard(Cards.get(index));
	        		 flash.invalidate();
	        		}
	        		else
	        		{
	        			Toast.makeText(getBaseContext(), "Cannot remove last card", Toast.LENGTH_SHORT).show();
	        			
	        		}
	        	 }
	        	 
	        	 
	         };
	         Delete.setOnClickListener(del);

		         OnClickListener Right = new OnClickListener() 
		         {
		        	 
		        	 public void onClick(View v) 
		        	 {
		        		 if(index<Cards.size()-1)
		        		 {	 
		        		 index++;
		        		 System.out.println("Index is"+index);
	                     flash.setside(true);
	                     flash.Writecard(Cards.get(index));
		        		 }
		        	 }
		         };
		        forward.setOnClickListener(Right);
		         
		         OnClickListener Left = new OnClickListener() 
		         {
		        	 
		        	 public void onClick(View v) 
		        	 {
		        		 if((index>0))
		        		 {
		        		 index--;
		        		 System.out.println("Index is"+index);
	                     flash.setside(true);
	                     flash.Writecard(Cards.get(index));
		        		 }
		        		 
		        	 }
		         };
		         back.setOnClickListener(Left);
		         
		         OnClickListener Write = new OnClickListener()
		         {

					@Override
					public void onClick(View v)
					{
						 File tester = Environment.getExternalStorageDirectory();
		                   File file = new File(tester,"file.txt");
		                   String mySettings = " ";
							try 
							{
								for(int x = 1; x<Cards.size(); x++)
								{
									mySettings = mySettings + (Cards.get(x)).getpFront()+"\n";
									Toast.makeText(getBaseContext(), mySettings, Toast.LENGTH_SHORT).show();
									mySettings = mySettings + (Cards.get(x)).getpBack()+"\n";	
								}
								 BufferedWriter Writer = new BufferedWriter(new FileWriter(file));
								 Writer.write(mySettings);
								 Writer.close();
							}
							catch (java.io.IOException e)
							{
								Toast.makeText(getBaseContext(), "File not found", Toast.LENGTH_SHORT).show();
							};
						
					}
		        	 
		        	 
		         };
		         Save.setOnClickListener(Write);
		       
	   }
	  

}