package test.myflix;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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
	String[] fieldName = {"title", "genre", "actor", "keyword"};
	String[] fields = {null, null, null, null}; //Fields are title, genre, actor, keyword;
	int numFields = 4;
	String sortBy = null;
	String selection = "";
	ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_page);
        //titlesAdapter = new ArrayAdapter<String>(null, numFields, args)
		SearchAdapter adapter = new SearchAdapter(this, R.layout.search_result_row, movie_data);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);
        //mDrawerLayout.setDrawerListener(mDrawerToggle);
		mActionBar = this.getActionBar();
		mActionBar.setTitle("Movie Search");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.right_drawer);
        TitleView = (EditText) findViewById(R.id.field_title);
        GenreView = (EditText) findViewById(R.id.field_genre);
        ActorView = (EditText) findViewById(R.id.field_actor);
        KeywordView = (EditText) findViewById(R.id.field_keyword);
        search = (Button) findViewById(R.id.button_search);
        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            	if(search()){
            		titlesAdapter.addAll(titles);
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
		String[] passArgs = (String[]) args.toArray();
		titles = siteData.getCol("title", selection, passArgs, null, null, sortBy, null, "30");
		ratings = siteData.getCol("imdbRating", selection, passArgs, null, null, sortBy, null,"30");
		for(int i = 0; i < titles.size(); i++){
			movie_data.add(new Movie(titles.get(i), ratings.get(i)));
		}
		return true;
		
	}
	private void popField(){
		fields[1] = TitleView.getText().toString();
		fields[2] = GenreView.getText().toString();
		fields[3] = ActorView.getText().toString();
		fields[4] = KeywordView.getText().toString();
		sortBy = null;
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
