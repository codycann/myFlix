package test.myflix;


import java.util.ArrayList;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoScreen extends Activity {

    ImageStorage image;
    DatabaseQuery data;
    ArrayList<String> titlelist;
    ArrayList<String> datalist;
    ArrayList<Bitmap> posterStorage;
    Bitmap poster;
    String title;
    ImageView picture;
    TextView movietitle;
    TextView imdb;
    TextView rating;
    TextView runtime;
    TextView year;
    TextView actors;
    TextView director;
    TextView awards;
    TextView genres;
    TextView writer;
    ScaleableTextView description;
    Button playbutton;
    Button addbutton;
    ActionBar mActionBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_screen);
        mActionBar = this.getActionBar();
        ArrayList<String> titlelist = new ArrayList<String>();
        image = new ImageStorage(getApplicationContext());
        data = new DatabaseQuery(getApplicationContext());
        Bundle extras = this.getIntent().getExtras();
        title = extras.getString("title");
        mActionBar.setTitle(title);
        titlelist.add(title);
        posterStorage = image.getThumbnail(titlelist);
        poster = posterStorage.get(0);
        datalist = data.byTitle(title);
        
    	description = (ScaleableTextView) findViewById(R.id.description);
		description.setText(datalist.get(6));
		
		movietitle = (TextView) findViewById(R.id.movie_title);
		movietitle.setText(datalist.get(1));
		
		imdb = (TextView) findViewById(R.id.imdb_rating);
		imdb.setText(datalist.get(7));
		
		rating = (TextView) findViewById(R.id.rating);
		rating.setText(datalist.get(3));
		
		runtime = (TextView) findViewById(R.id.runtime);
		runtime.setText(datalist.get(4));
		
		year = (TextView) findViewById(R.id.year);
		year.setText(datalist.get(2));
		
		actors = (TextView) findViewById(R.id.actors);
		actors.setText(datalist.get(11));
		
		director = (TextView) findViewById(R.id.director);
		director.setText(datalist.get(9));
		
		awards = (TextView) findViewById(R.id.awards);
		awards.setText(datalist.get(5));
		
		genres = (TextView) findViewById(R.id.genre);
		genres.setText(datalist.get(8));
		
		writer = (TextView) findViewById(R.id.writer);
		writer.setText(datalist.get(10));
		
		picture = (ImageView) findViewById(R.id.movie_poster);
		picture.setImageBitmap(poster);
        
    }
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
	    MenuItem item= menu.findItem(R.id.action_video);
	    item.setEnabled(false);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent mIntent;
    	switch (item.getItemId()) {
    		case R.id.action_search:
    			mIntent = new Intent(getApplicationContext(), SearchActivity.class);
				startActivity(mIntent); 
    			return true;
    		case R.id.action_collection:
    			mIntent = new Intent(getApplicationContext(), MovieCollection.class);
				startActivity(mIntent); 
    			return true;
    		case R.id.action_video:
    			mIntent = new Intent(getApplicationContext(), VideoViewActivity.class);
				startActivity(mIntent); 
    			return true;
    		default:
    			return true;
    		}
    }
}


