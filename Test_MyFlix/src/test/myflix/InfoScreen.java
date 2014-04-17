package test.myflix;


import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoScreen extends Activity {

    ImageStorage image;
    DatabaseQuery data;
    ArrayList<String> titlelist;
    ArrayList<String> datalist;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_screen);
        Bundle extras = this.getIntent().getExtras();
        title = extras.getString("title");
        titlelist.add(title);
        poster = image.getThumbnail(titlelist).get(0);
        datalist = data.byTitle(title);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        return true;
    }
    
    protected void onCreateView(){
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
    
    
}


