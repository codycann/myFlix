package test.myflix;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.nineoldandroids.view.animation.AnimatorProxy;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;


public class SearchActivity extends Activity implements ListView.OnItemClickListener {
	ArrayList<String> titles;
	ArrayList<Movie> movie_data = new ArrayList<Movie>();
	ArrayList<String> ratings;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private  SlidingUpPanelLayout slidingPanel;
    DatabaseQuery siteData;
	EditText TitleView;
	Spinner GenreView;
	EditText KeywordView;
	EditText ActorView;
	RadioGroup SortView;
	RadioButton titleRadio;
	RadioButton ratingsRadio;
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
		setContentView(R.layout.activity_search);
		adapter = new SearchAdapter(this, R.layout.search_result_row, movie_data);
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
		mActionBar.setTitle("Movie Search");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        GenreView = (Spinner) findViewById(R.id.genre_field);
        ActorView = (EditText) findViewById(R.id.actor_field);
        KeywordView = (EditText) findViewById(R.id.keyword_field);
        SortView = (RadioGroup) findViewById(R.id.radioGroup1);
        search = (Button) findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
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
		if(GenreView.getSelectedItem().toString().length() != 0){
			fields[1] = GenreView.getSelectedItem().toString();
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
