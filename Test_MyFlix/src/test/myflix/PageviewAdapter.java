package test.myflix;

import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

class PageviewApapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

	 public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	  private List<Fragment> fragments;
	  private final Context mContext;
	  private final ActionBar mActionBar;
	  FragmentManager mFragmentManager;
	  private final ViewPager mViewPager;

	  public PageviewApapter(FragmentManager fm, List<Fragment> fragments, FragmentActivity activity, ViewPager pager) {
	    super(fm);
	    mViewPager = pager;
	    mContext = activity;
	    mFragmentManager = fm;
	    mActionBar = activity.getActionBar();
	    mViewPager.setOnPageChangeListener(this);
	    this.fragments = fragments;
	  }

	  @Override 
	  public Fragment getItem(int position) {
	    return this.fragments.get(position);
	  }

	  @Override
	  public int getCount() {
	    return this.fragments.size();
	  }
	  public void onSaveInstanceState (Bundle outState){
		  //this.saveState();
	  }
	  public void onActivityCreated (Bundle savedInstanceState){
	    	
	  }
	  @Override
	  public void finishUpdate(ViewGroup container) 
	  {
		  super.finishUpdate(container);
		  mFragmentManager.executePendingTransactions();
	  }

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		Fragment updateFrag = this.fragments.get(arg0);
		mActionBar.setTitle(updateFrag.getArguments().getString(EXTRA_MESSAGE));
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	}

