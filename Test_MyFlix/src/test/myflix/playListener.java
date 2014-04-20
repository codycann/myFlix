package test.myflix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class playListener implements OnClickListener{
		 private final String title;

	 public playListener(String title){
		 this.title = title;
	 }

	 public void onClick(View v){
		 Intent mIntent = new Intent(v.getContext(), VideoViewActivity.class);
		 Bundle mBundle = new Bundle();
		 mBundle.putString("title", title);
		 Log.v("listener", title);
		 mIntent.putExtras(mBundle);
		 v.getContext().startActivity(mIntent); 
	 }
	}
