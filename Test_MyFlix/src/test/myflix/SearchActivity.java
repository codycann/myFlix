package test.myflix;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.view.Menu;


public class SearchActivity extends Activity implements ListView.OnItemClickListener {
	ArrayList<String> titles;
	ArrayList<String> ratings;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayAdapter<String> titlesAdapter;
    DatabaseQuery siteData;
	EditText TitleView;
	EditText GenreView;
	EditText KeywordView;
	EditText ActorView;
	ArrayList<String> args = new ArrayList<String>();
	String[] fieldName = {"title", "genre", "actor", "keyword"};
	String[] fields = {null, null, null, null}; //Fields are title, genre, actor, keyword;
	int numFields = 4;
	String sortBy = null;
	String selection = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_page);
		setContentView(R.layout.search_page);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.right_drawer);
        
        // Set the adapter for the list view
        titlesAdapter = new ArrayAdapter<String>(this, R.id.result_item);
        mDrawerList.setAdapter(titlesAdapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(this);
		// Set the drawer toggle as the DrawerListener
		
        //mDrawerLayout.setDrawerListener(mDrawerToggle);

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
		titles = siteData.getCol("title", selection, passArgs, null, null, sortBy, null, "30");
		titles = siteData.getCol("imdbRating", selection, passArgs, null, null, sortBy, null,"30");
		return true;
		
	}
	private void popField(){
		fields[1] = TitleView.getText().toString();
		fields[2] = GenreView.getText().toString();
		fields[3] = ActorView.getText().toString();
		fields[4] = KeywordView.getText().toString();
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
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
		String title = titles.get(position);
		Intent mIntent = new Intent(this, InfoScreen.class);
		mIntent.putExtra("title", title);
		startActivity(mIntent);
		
		
	}
}
