package test.myflix;


import java.util.ArrayList;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

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
    TextView actors;
    TextView director;
    TextView awards;
    TextView genres;
    TextView writer;
    TextView plot;
    Button playbutton;
    Button addbutton;
    ActionBar mActionBar;
    double height;
    double width;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_screen);
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();		
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
        mActionBar = getActionBar();
        mActionBar.setHomeButtonEnabled(true);
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
        
		
		movietitle = (TextView) findViewById(R.id.title);
		movietitle.setText(datalist.get(3)+" ("+datalist.get(4)+")"); //title (year)
		
		imdb = (TextView) findViewById(R.id.imdb);
		imdb.setText("IMDB Rating: "+datalist.get(9));
		
		rating = (TextView) findViewById(R.id.rated);
		rating.setText(datalist.get(5));
		
		runtime = (TextView) findViewById(R.id.runtime);
		runtime.setText(datalist.get(6));
		
		actors = (TextView) findViewById(R.id.actor);
		actors.setText("Stars: "+datalist.get(13));
		
		director = (TextView) findViewById(R.id.director);
		director.setText("Director: "+datalist.get(11));
		
		awards = (TextView) findViewById(R.id.awards);
		awards.setText("Awards: "+datalist.get(7));
		
		genres = (TextView) findViewById(R.id.genre);
		genres.setText(datalist.get(10));
		
		writer = (TextView) findViewById(R.id.writer);
		writer.setText("Writer(s): "+datalist.get(12));
		
		plot = (TextView) findViewById(R.id.plot);
		plot.setText(datalist.get(8));
		
		picture = (ImageView) findViewById(R.id.poster);
		picture.getLayoutParams().height = (int) (height/(2.6));
		picture.getLayoutParams().width = (int) (width/(2.3));
		picture.setScaleType(ScaleType.FIT_XY);
		picture.setImageBitmap(poster);
		
		playbutton = (Button) findViewById(R.id.play_button);
		playbutton.setWidth(picture.getLayoutParams().width);
        
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
			case android.R.id.home:
				mIntent = new Intent(getApplicationContext(), MovieCollection.class);
				startActivity(mIntent); 
				return true;
    		case R.id.action_search:
    			mIntent = new Intent(getApplicationContext(), SearchActivity.class);
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


