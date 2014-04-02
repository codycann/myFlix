package test.myflix;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MovieCollection extends FragmentActivity {

	  PageviewApapter pageAdapter;
	  @Override

	  public void onCreate(Bundle savedInstanceState) {

	    super.onCreate(savedInstanceState);
	    new pullDatabase(this).execute("");
	    setContentView(R.layout.moviecollection);
	    List<Fragment> fragments = getFragments();
	    pageAdapter = new PageviewApapter(getSupportFragmentManager(), fragments);
	    ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
	    pager.setAdapter(pageAdapter);
	  }

private List<Fragment> getFragments(){

	  List<Fragment> fList = new ArrayList<Fragment>();
	  fList.add(GridFragment.newInstance("Action"));
	  fList.add(GridFragment.newInstance("Crime")); 
	  fList.add(GridFragment.newInstance("Thriller"));
	  
	  //add to list for every genre page

	 

	  return fList;

	}
}
