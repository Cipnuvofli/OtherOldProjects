package josephpenrod.Flashcard2;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class Flashcardhost extends TabActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Reusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, Flashcardviewer.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("Viewer").setIndicator("Viewer",
                          res.getDrawable(R.drawable.ic_tab_viewer))
                      .setContent(intent);
        tabHost.addTab(spec);

        // TODO Make a loader for making flash cards their own file
        intent = new Intent().setClass(this, Flashcardloader.class);
        spec = tabHost.newTabSpec("Loader").setIndicator("Loader",
                          res.getDrawable(R.drawable.ic_tab_loader))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Flashcardmaker.class);
        spec = tabHost.newTabSpec("Maker").setIndicator("Maker",
                          res.getDrawable(R.drawable.ic_tab_maker))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(2);
    }
    public void onPause()
    {
    	super.onPause();
    	
    }
    public void onResume()
    {
    	
    	super.onResume();
    }
}
