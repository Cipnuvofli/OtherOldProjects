package josephpenrod.Flashcard2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

public class Flashcardmaker extends Activity
{
	 public void onCreate(Bundle savedInstanceState) 
	 {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.flashcardmaker);
	        final String PAIR_DETECTED = "josephpenrod.Flashcard2.action.PAIR_DETECTED";
	        final EditText front = (EditText)this.findViewById(R.id.editText1);
	        final EditText back = (EditText)this.findViewById(R.id.editText2);
	        Button make = (Button)this.findViewById(R.id.Cardmaker);
	        final Intent Pairsender = new Intent(PAIR_DETECTED);
	        OnClickListener Create= new OnClickListener() 
	         {
	        	 public void onClick(View v) 
	        	 {
                   String F = front.getText().toString();
                   String R = back.getText().toString();
                   Pair Pair = new Pair(F,R);
                   Pairsender.putExtra("card", Pair);
                   sendBroadcast(Pairsender);
                   Log.v("Broadcast", "Broadcast sent");
                   Toast.makeText(getBaseContext(), "Card Made", Toast.LENGTH_SHORT).show();
	        	 }
	         };
	        make.setOnClickListener(Create);
	        
	        
	       
	 }
}
