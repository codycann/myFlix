package test.myflix;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

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
		adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, genreList);
		mActionBar.setListNavigationCallbacks(adapter, this);
		
		pager = (ViewPager)findViewById(R.id.viewpager);
		pageAdapter = new PageviewApapter(getSupportFragmentManager(), fragments, this, pager);
		pager.setPageTransformer(true, new PageTransformer());
		pager.setAdapter(pageAdapter);
		
		//Set Listener on ViewPager for action bar navigation
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() 
		{
			
			@Override
			public void onPageSelected(int arg0) {
				mActionBar.setSelectedNavigationItem(arg0);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
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
	genreList.add("Horror");
	genreList.add("Romance");
	genreList.add("Science Fiction");
	genreList.add("War");
}
@Override
public boolean onNavigationItemSelected(int arg0, long arg1) {
	pager.setCurrentItem(arg0);
	return false;
}
}
