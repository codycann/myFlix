package test.myflix;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class MovieCollection extends FragmentActivity {
	  PageviewApapter pageAdapter;
	  boolean dataPulled = false;
	  pullDatabase dataTask; 
	  @Override
	  public void onCreate(Bundle savedInstanceState) {

		    super.onCreate(savedInstanceState);
		    dataTask = new pullDatabase(this);
		    dataTask.execute("");
		    while (dataTask.done == false) { //Loops at .1 sec intervals until AsyncTask has finished  
		    	try { Thread.sleep(100); 
			    	Log.v("mytag","still looping!");
		        }
		        catch (InterruptedException e) { e.printStackTrace(); }
		    }
		    Log.v("mytag","made it out of the loop!");
		    setContentView(R.layout.moviecollection);
		    List<Fragment> fragments = getFragments();
		    pageAdapter = new PageviewApapter(getSupportFragmentManager(), fragments);
		    ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
		    pager.setPageTransformer(true, new PageTransformer());
		    pager.setAdapter(pageAdapter);
	  }
private List<Fragment> getFragments(){
	  List<Fragment> fList = new ArrayList<Fragment>();
	  fList.add(GridFragment.newInstance("Comedy",this.getApplicationContext()));
	  fList.add(GridFragment.newInstance("Drama", this)); 
	  fList.add(GridFragment.newInstance("Romance", this));
	  //add to list for every genre page

	  return fList;
	}
}
