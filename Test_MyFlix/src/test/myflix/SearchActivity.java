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

public class SearchActivity extends Activity implements ListView.OnItemClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayAdapter<String> titlesAdapter;
	private ArrayList<String> titles;
	private ArrayList<String> ratings;

	DatabaseQuery siteData;
	EditText TitleView;
	EditText GenreView;
	EditText KeywordView;
	EditText ActorView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		siteData = new DatabaseQuery(this);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String title = titles.get(position);
		Intent mIntent = new Intent(this, InfoScreen.class);
		mIntent.putExtra("title", title);
		startActivity(mIntent);
		
		
	}
	private void search(){
		String title;
		String genre;
		String Keyword;
		String ActorView;
		titles = siteData.getCol("title", condition, genreArray, null, null, null, "");
		ratings = siteData.getCol("imdbRating", condition, genreArray, null, null, null, "");
	}


}


