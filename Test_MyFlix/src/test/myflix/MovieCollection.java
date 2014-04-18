package test.myflix;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MovieCollection extends FragmentActivity {
	 public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	private ActionBar mActionBar;
	  PageviewApapter pageAdapter;
	  boolean dataPulled = false;
	  pullDatabase dataTask;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar = this.getActionBar();
		//onStartup();
		setContentView(R.layout.moviecollection);
		List<Fragment> fragments = getFragments();
		mActionBar.setTitle(fragments.get(0).getArguments().getString(EXTRA_MESSAGE));
		ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
		pageAdapter = new PageviewApapter(getSupportFragmentManager(), fragments, this, pager);
		pager.setPageTransformer(true, new PageTransformer());
		pager.setAdapter(pageAdapter);
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
	  @Override
	  protected void onSaveInstanceState(Bundle outState) {
		  super.onSaveInstanceState(outState);
	  }
private List<Fragment> getFragments(){
	  List<Fragment> fList = new ArrayList<Fragment>();
	  fList.add(GridFragment.newInstance(new String[] {"%Action%"},"Action",this));
	  fList.add(GridFragment.newInstance(new String[] {"%Adventure%"},"Adventure",this));
	  fList.add(GridFragment.newInstance(new String[] {"%Comedy%"},"Comedy",this));
	  fList.add(GridFragment.newInstance(new String[] {"%Crime%"},"Crime",this));
	  fList.add(GridFragment.newInstance(new String[] {"%Drama%"},"Drama", this));
	  fList.add(GridFragment.newInstance(new String[] {"%Honor%"},"Honor", this)); 
	  fList.add(GridFragment.newInstance(new String[] {"%Romance%"},"Romance", this));
	  fList.add(GridFragment.newInstance(new String[] {"%Science Fiction%"},"Science Fiction", this));
	  fList.add(GridFragment.newInstance(new String[] {"%War%"},"War", this));

	  //add to list for every genre page

	  return fList;
}
}
