package test.myflix;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	/** Called when the user touches the button */
	public void sendMessage(View view) {
	    // use fields to query db
		
	}

}
