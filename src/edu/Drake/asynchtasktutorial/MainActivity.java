package edu.Drake.asynchtasktutorial;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	String mArray[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Initialize our array of strings
		mArray = new String[10];
		for ( int i = 0; i< 10; i++){
            mArray[i] = "";
        }
		//Instantiate a new task
		PopulateList task = new PopulateList();
		//Pass in the URL where the data is located as a parameter. These parameters depend on your needs
		task.execute(new String[] { "http://www.ethanclevenger.com/MirrorMe/tutorial.txt" });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private class PopulateList extends AsyncTask<String, Void, Void> {  
		
		//Constructor
	    PopulateList() {
	    }
	    
	    /*The heavy lifting - the actual stuff you want happening off the main thread
	     * You see that this returns void. If I didn't use a global variable
	     * for the array I'll be using, it would need to be returned by this method.
	     * Common practice calls this variable 'result'
	     */
		 @Override
		    protected Void doInBackground(String... urls) {
		      String text;
		      
		      for (String url : urls) {
		        DefaultHttpClient client = new DefaultHttpClient();
		        HttpGet httpGet = new HttpGet(url);
		        
		        try {
		          HttpResponse execute = client.execute(httpGet);
		          InputStream content = execute.getEntity().getContent();
		          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
		          
		          for(int i = 0; i<10; i++)
		          {
		        	  text = buffer.readLine();
		              mArray[i] = text;
		              
		          }

		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		      }
			return null;
		    }

		 /*
		  * This method is called after the above is finished. It usually receieves
		  * the 'result' from the above method instead of Void. It can perform whatever you need.
		  */
		 @Override
		    protected void onPostExecute(Void v) {
		    	ListView mList = (ListView) findViewById(R.id.listView1);
		    	/*
		    	 * Check for null! If your app has more than one screen, the user may have left the screen
		    	 * containing the view you need before the task actually finished! If you aren't familiar
		    	 * with ArrayAdapters, you can see another group's sample posted in Spring 2013 that covers
		    	 * them
		    	 */
		    	if(mList != null)
		    	{
		    		ArrayAdapter<String> adapt = new ArrayAdapter<String>(MainActivity.this, R.layout.menu_item, mArray);
			    	mList.setAdapter(adapt);
		    	}
		    }
	 }

}
