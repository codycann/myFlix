package test.myflix;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MovieCollection extends FragmentActivity implements OnNavigationListener{
	 public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	 private ActionBar mActionBar;
	 private ArrayAdapter<String> adapter;
	 private ArrayList<String> genreList;
	 private ViewPager pager;
	  PageviewApapter pageAdapter;
	  boolean dataPulled = false;
	  pullDatabase dataTask;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar = this.getActionBar();
		mActionBar.setHomeButtonEnabled(true);
		setContentView(R.layout.moviecollection);
		genreList = new ArrayList<String>();
		populateGenreList();
		List<Fragment> fragments = getFragments();
		
		//mActionBar.setTitle(fragments.get(0).getArguments().getString(EXTRA_MESSAGE));
		
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.search_result_list_item, R.id.result_item, genreList);
		mActionBar.setListNavigationCallbacks(adapter, this);
		
		pager = (ViewPager)findViewById(R.id.viewpager);
		pageAdapter = new PageviewApapter(getSupportFragmentManager(), fragments, this, pager);
		pager.setPageTransformer(true, new PageTransformer());
		pager.setAdapter(pageAdapter);
		//Set Listener on ViewPager for action bar navigation
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() 
		{
			int positionCurrent;
            boolean dontLoadList;
            
			
			@Override
			public void onPageSelected(int position) {
				mActionBar.setSelectedNavigationItem(position);
				final int pos = position;
				new Handler().postDelayed(new Runnable() {
                    public void run() {
                        {
                        	FragmentTransaction fragmentTrans = getSupportFragmentManager().beginTransaction();
            				Fragment fg = pageAdapter.getItem(pos);
            				fragmentTrans.detach(fg);
            				fragmentTrans.attach(fg);
            				fragmentTrans.commit();
                        }
                    }
                },200);
				
				
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) 
			{
				positionCurrent = position; 
                if( positionOffset == 0 && positionOffsetPixels == 0 )
                {
                	// the offset is zero when the swiping ends
                    dontLoadList = false;
                }
	
                else
                {
                    dontLoadList = true; // To avoid loading content for list after swiping the pager.
                }
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) 
			{
				if(state == 0){ 
					// the viewpager is idle as swipping ended
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if(!dontLoadList){
                            //async thread code to execute loading the list... 
                            }
                        }
                    },200);
                }
            }
		});
		
	  }
		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater( );
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
	  @Override
	  protected void onSaveInstanceState(Bundle outState) {
		  super.onSaveInstanceState(outState);
	  }
private List<Fragment> getFragments(){
	  List<Fragment> fList = new ArrayList<Fragment>();
	  for(int i = 0; i < genreList.size(); i++){
		  fList.add(GridFragment.newInstance(new String[] {"%"+genreList.get(i)+"%"},genreList.get(i),this));
	  }
	  //fList.add(GridFragment.newInstance(new String[] {"%Romance%"},"Romance", this));
	  //add to list for every genre page
	  return fList;
}
private void populateGenreList(){
	genreList.add("Action");
	genreList.add("Adventure");
	genreList.add("Comedy");
	genreList.add("Crime");
	genreList.add("Drama");
	genreList.add("Family");
	genreList.add("Fantasy");
	genreList.add("Mystery");
	genreList.add("Romance");
	genreList.add("Sci-Fi");
	genreList.add("Thriller");
}
@Override
public boolean onNavigationItemSelected(int arg0, long arg1) {
	pager.setCurrentItem(arg0);
	return false;
}
}
