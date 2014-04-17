package test.myflix;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class SearchActivity extends Activity {
	ArrayList<String> titles;
	ArrayList<String> ratings;
	ArrayList<String> args = new ArrayList<String>();
	String[] fieldName = {"title", "genre", "actor", "keyword"};
	String[] fields = {null, null, null, null}; //Fields are title, genre, actor, keyword;
	int numFields = 4;
	String sortBy = null;
	String selection = "";
	
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
	public boolean search(){
		DatabaseQuery siteData = new DatabaseQuery(this);
		popField();
		int size = populate();
		if(size == 0) return false;
		String[] passArgs = (String[]) args.toArray();
		titles = siteData.getCol("title", selection, passArgs, null, null, sortBy, null);
		titles = siteData.getCol("imdbRating", selection, passArgs, null, null, sortBy, null);
		return true;
		
	}
	private void popField(){
		fields[1] = "";
		fields[2] = "";
		fields[3] = "";
		fields[4] = "";
		sortBy = "";
	}
	private int populate(){
		int size = 0;
		for(int i = 0; i < numFields; i++){
			if(fields[i] != null){
				if(size > 0) selection = selection + " AND ";
				selection = selection + fieldName[i] + " like ? ";
				args.add("%"+fields[i]+"%");
				size++;
			}
		}
		return size;
	}
}
