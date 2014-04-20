package test.myflix;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nineoldandroids.view.animation.AnimatorProxy;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;


public class SearchActivity extends Activity implements ListView.OnItemClickListener {
	ArrayList<String> titles;
	ArrayList<Movie> movie_data = new ArrayList<Movie>();
	ArrayList<String> ratings;
	private ListView mDrawerList;
	private  SlidingUpPanelLayout slidingPanel;
    DatabaseQuery siteData;
	EditText TitleView;
	Spinner GenreView;
	EditText KeywordView;
	EditText ActorView;
	RadioGroup SortView;
	RadioButton titleRadio;
	RadioButton ratingRadio;
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
	double width;
	double height;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
        //Get Screen dimensions
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();		
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		adapter = new SearchAdapter(this, R.layout.search_result_row, movie_data, width, height);
        mDrawerList = (ListView) findViewById(R.id.right_drawer);
 
        //Set sliding panel and listener
        slidingPanel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingPanel.setSlidingEnabled(false);
        slidingPanel.setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i("Sliding Panel", "onPanelSlide, offset " + slideOffset);
               // setActionBarTranslation(slidingPanel.getCurrentParalaxOffset());
//                if (slideOffset < 0.2) {
//                    if (getActionBar().isShowing()) {
//                        getActionBar().hide();
//                    }
//                } else {
//                    if (!getActionBar().isShowing()) {
//                        getActionBar().show();
//                    }
//                }
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.i("Sliding Panel", "onPanelExpanded");

            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.i("Sliding Panel", "onPanelCollapsed");

            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i("Sliding Panel", "onPanelAnchored");

            }
        });
        mDrawerList.setAdapter(adapter);
		mActionBar = this.getActionBar();
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setTitle("Movie Search");
        OnItemClickListener listener = new OnItemClickListener (){
      	  @Override
      	  public void onItemClick(AdapterView<?> parent, View view, int position, long id){
      		  String title = ((TextView) view.findViewById(R.id.textview_title)).getText().toString();
      		 Intent mIntent = new Intent(view.getContext(), InfoScreen.class);
      		 Bundle mBundle = new Bundle();
      		 mBundle.putString("title", title);      		
      		 mIntent.putExtras(mBundle);
      		 view.getContext().startActivity(mIntent); 
      	  }
      	};
      	mDrawerList.setOnItemClickListener(listener);
        TitleView = (EditText) findViewById(R.id.title_field);
        titleRadio = (RadioButton) findViewById(R.id.title_radio);
        ratingRadio = (RadioButton) findViewById(R.id.rating_radio);
        GenreView = (Spinner) findViewById(R.id.genre_field);
        ActorView = (EditText) findViewById(R.id.actor_field);
        KeywordView = (EditText) findViewById(R.id.keyword_field);
        SortView = (RadioGroup) findViewById(R.id.radioGroup1);
        search = (Button) findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            	movie_data.clear();
            	if(search()){
            		adapter.refresh(movie_data);
            		slidingPanel.setSlidingEnabled(true);
            		slidingPanel.expandPane();
            	}
            }
        });
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
    
	public boolean search(){
		String passSelection = null;
		DatabaseQuery siteData = new DatabaseQuery(this);
		popField();
		String sortOption;
		int size = populate();
		//if(size == 0) return false;
		sortBy = "Title";
		sortOption = " ASC";
		if(!titleRadio.isChecked())
		{
			sortBy = "imdbRating";
			sortOption = " DESC";
		}
		if(args.get(0) == ""){
			titles = siteData.getCol("Title", selection, null, null, null, sortBy, sortOption, "40");
			ratings = siteData.getCol("imdbRating", selection, null, null, null, sortBy, sortOption,"40");
		}
		else {
			String[] passArgs = args.toArray(new String[args.size()]);
			titles = siteData.getCol("Title", selection, passArgs, null, null, sortBy, sortOption, "40");
			ratings = siteData.getCol("imdbRating", selection, passArgs, null, null, sortBy, sortOption,"40");
		}
		for(int i = 0; i < titles.size(); i++){
			movie_data.add(new Movie(titles.get(i), ratings.get(i)));
		}
		return true;
		
	}
	private void popField(){
		fields = reset.clone();
		String genre;
		if(TitleView.getText().toString().length() != 0){
			fields[0] = TitleView.getText().toString();
		}
		if(GenreView.getSelectedItem().toString().length() != 0){
			genre = GenreView.getSelectedItem().toString();
			if(!genre.contentEquals("Select Genre")){
				fields[1] = GenreView.getSelectedItem().toString();
			}
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
		if(!args.isEmpty()){
			args.clear();
		}
		selection = "";
		for(int i = 0; i < numFields; i++){
			if(fields[i] != ""){
				if(size > 0) selection = selection + " AND ";
				selection = selection + fieldName[i] + " like ? ";
				args.add("%"+fields[i]+"%");
				size++;
			}
		}
		if(args.size() == 0){
			args.add("");
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
    
    //Not really needed but will keep here just in case
    @SuppressLint("NewApi")
    public void setActionBarTranslation(float y) 
    {
        // Figure out the actionbar height
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        // A hack to add the translation to the action bar
        ViewGroup content = ((ViewGroup) findViewById(android.R.id.content).getParent());
        int children = content.getChildCount();
        for (int i = 0; i < children; i++) {
            View child = content.getChildAt(i);
            if (child.getId() != android.R.id.content) 
            {
                if (y <= -actionBarHeight) 
                {
                    child.setVisibility(View.GONE);
                } 
                else 
                {
                    child.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
                    {
                        child.setTranslationY(y);
                    } else 
                    {
                        AnimatorProxy.wrap(child).setTranslationY(y);
                    }
                }
            }
        }
    }

}
