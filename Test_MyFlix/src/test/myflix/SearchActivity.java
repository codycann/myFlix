package test.myflix;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.Menu;


public class SearchActivity extends Activity implements ListView.OnItemClickListener {
	ArrayList<String> titles;
	ArrayList<Movie> movie_data = new ArrayList<Movie>();
	ArrayList<String> ratings;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayAdapter<String> titlesAdapter;
    DatabaseQuery siteData;
	EditText TitleView;
	EditText GenreView;
	EditText KeywordView;
	EditText ActorView;
	Button search;
	ArrayList<String> args = new ArrayList<String>();
	String[] fieldName = {"Title", "Genre", "Actors", "Plot"};
	String[] fields = {"", "", "", ""};
	String[] reset = {"", "", "", ""};//Fields are title, genre, actor, keyword;
	int numFields = fields.length;
	String sortBy = null;
	String selection = "";
	ActionBar mActionBar;
	SearchAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_page);
        //titlesAdapter = new ArrayAdapter<String>(null, numFields, args)
		adapter = new SearchAdapter(this, R.layout.search_result_row, movie_data);
        mDrawerList = (ListView) findViewById(R.id.right_drawer);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);
        //mDrawerLayout.setDrawerListener(mDrawerToggle);
		mActionBar = this.getActionBar();
		mActionBar.setTitle("Movie Search");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        TitleView = (EditText) findViewById(R.id.field_title);
        GenreView = (EditText) findViewById(R.id.field_genre);
        ActorView = (EditText) findViewById(R.id.field_actor);
        KeywordView = (EditText) findViewById(R.id.field_keyword);
        search = (Button) findViewById(R.id.button_search);
        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            	if(search()){
            		adapter.refresh(movie_data);
            		mDrawerLayout.openDrawer(Gravity.LEFT);
            	}
            }
        });
     
		
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
		String[] passArgs = args.toArray(new String[args.size()]);
		titles = siteData.getCol("Title", selection, passArgs, null, null, sortBy, "", "30");
		ratings = siteData.getCol("imdbRating", selection, passArgs, null, null, sortBy, "","30");
		for(int i = 0; i < titles.size(); i++){
			movie_data.add(new Movie(titles.get(i), ratings.get(i)));
		}
		return true;
		
	}
	private void popField(){
		fields = reset.clone();
		if(TitleView.getText().toString().length() != 0){
			fields[0] = TitleView.getText().toString();
		}
		if(GenreView.getText().toString().length() != 0){
			fields[1] = GenreView.getText().toString();
		}
		if(ActorView.getText().toString().length() != 0){
			fields[2] = ActorView.getText().toString();
		}
		if(KeywordView.getText().toString().length() != 0){
			fields[3] = KeywordView.getText().toString();
		}
		for(int i = 0; i < fields.length; i++){
			Log.v("fields", fields[i]);
		}
		sortBy = null;
	}
	private int populate(){
		int size = 0;
		args.clear();
		selection = "";
		for(int i = 0; i < numFields; i++){
			if(fields[i] != ""){
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
