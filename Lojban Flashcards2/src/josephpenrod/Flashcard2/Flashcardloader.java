package josephpenrod.Flashcard2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Flashcardloader extends Activity 
{
	   public void onCreate(Bundle savedInstanceState) 
	   {
		   final String PAIR_DETECTED = "josephpenrod.Flashcard2.action.PAIR_DETECTED";
		   final String ACTION_DELETE = "android.intent.action.DELETE";
		   final Intent Pairsender = new Intent(PAIR_DETECTED);
		   final Intent Remotedelete = new Intent(ACTION_DELETE);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.flashcardloader);  
	        Button load = (Button)this.findViewById(R.id.button1);
	        final EditText FileEmail = (EditText)this.findViewById(R.id.FileEmail);
	        Button send = (Button)this.findViewById(R.id.FileEmailSender);
	        final Intent i = new Intent(Intent.ACTION_SEND);
	        OnClickListener Sendfile = new OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		
	        		i.setType("text/plain");
	        		i.putExtra(Intent.EXTRA_EMAIL  , FileEmail.getText().toString());
	        		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
	        		i.putExtra(Intent.EXTRA_TEXT   , "body of email");
	        		try 
	        		{
	        		    startActivity(Intent.createChooser(i, "Send mail..."));
	        		} 
	        		catch (android.content.ActivityNotFoundException ex) {
	        		    Toast.makeText(getBaseContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
	        		}

	        	}
	        	
	        	
	        };
	        send.setOnClickListener(Sendfile);
	        OnClickListener Create= new OnClickListener() 
	         {
	        	 public void onClick(View v) 
	        	 {
                   File tester = Environment.getExternalStorageDirectory();
                   File file = new File(tester,"file.txt");
                   StringBuilder text = new StringBuilder();
                   ArrayList<String> Fronts = new ArrayList<String>();
                   ArrayList<String> Backs = new ArrayList<String>();
                   int index = 0;
                   try
                   {
                	   BufferedReader reader = new BufferedReader(new FileReader(file));
                	   String line;

                	    while ((line = reader.readLine()) != null)
                	    {
                	    	if(index%2 == 1)
                	    	{              	    		
                                Backs.add(line);               	    		             	    		
                	    	}
                	    	else if(index%2 == 0)
                	    	{
                	    		Fronts.add(line);	
                	    	}
                	        text.append(line);
                	        text.append('\n');
                	        index++;
                	    }
                	    reader.close();
                	    sendBroadcast(Remotedelete);
                	if(Fronts.size() != 0 && Backs.size() != 0)
                	{
                	    for(int x = 0; x<Fronts.size(); x++)
                	    {	
                	    	Pair Pair = new Pair(Fronts.get(x),Backs.get(x));
                        	Pairsender.putExtra("card", Pair);
                        	sendBroadcast(Pairsender);
                	    }
                	    Toast.makeText(getBaseContext(), "File Loaded", Toast.LENGTH_SHORT).show();
                	}
                	else
                	{
                		Toast.makeText(getBaseContext(), "Empty File", Toast.LENGTH_SHORT).show();
                		
                	}
                   }
                   catch(IOException e)
                   {
                	   Toast.makeText(getBaseContext(), "File Not Found", Toast.LENGTH_SHORT).show();
                	   
                   }
                   
	        	 }
	         };
	         load.setOnClickListener(Create);
	    }

}
