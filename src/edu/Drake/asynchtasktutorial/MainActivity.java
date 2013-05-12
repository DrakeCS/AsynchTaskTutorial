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
		mArray = new String[10];
		for ( int i = 0; i< 10; i++){
            mArray[i] = "";
        }
		PopulatePicture task = new PopulatePicture();
		task.execute(new String[] { "http://www.ethanclevenger.com/MirrorMe/tutorial.txt" });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private class PopulatePicture extends AsyncTask<String, Void, Void> {  
		

	    PopulatePicture() {
	    }
		 @Override
		    protected Void doInBackground(String... urls) {
		      String text;
		      
		      for (String url : urls) {
		        DefaultHttpClient client = new DefaultHttpClient();
		        HttpGet httpGet = new HttpGet(url);
		        
		        try {
		          HttpResponse execute = client.execute(httpGet);
		          Log.v("log", "this far");
		          InputStream content = execute.getEntity().getContent();
		          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
		          
		          for(int i = 0; i<10; i++)
		          {
		        	  text = buffer.readLine();
		        	  Log.v("log", text);
		              mArray[i] = text;
		              
		          }

		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		      }
			return null;
		    }

		 @Override
		    protected void onPostExecute(Void v) {
		    	ListView mList = (ListView) findViewById(R.id.listView1);
		    	ArrayAdapter<String> adapt = new ArrayAdapter<String>(MainActivity.this, R.layout.menu_item, mArray);
		    	mList.setAdapter(adapt);
		    }
	 }

}
