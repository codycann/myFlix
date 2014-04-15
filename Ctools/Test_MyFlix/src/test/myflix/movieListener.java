package test.myflix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

class movieListener implements OnClickListener{
	 private final String title;

 public movieListener(String title){
	 this.title = title;
 }

 public void onClick(View v){
	 Intent mIntent = new Intent(v.getContext(), VideoViewActivity.class);
	 Bundle mBundle = new Bundle();
	 mBundle.putString("title", title);
	 mIntent.putExtras(mBundle);
	 v.getContext().startActivity(mIntent); //prevent video from starting in testing.
 }
}