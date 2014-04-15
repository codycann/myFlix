package test.myflix;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

public class GridFragment extends Fragment {

	 public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	 public static final String EXTRA_ARRAY = "EXTRA_ARRAY";
	 GridView gv;
	 static int width;
	 static int height;
	 static String genre;
	 String[] genreArray;
	 ScaleableTextView messageTextView;
	 public static final GridFragment newInstance(String [] genres, String message, Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();		
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		genre = message;
		GridFragment f = new GridFragment();
		Bundle bdl = new Bundle(1);
		bdl.putStringArray(EXTRA_ARRAY, genres);
		bdl.putString(EXTRA_MESSAGE, genre);
		f.setArguments(bdl);
		return f;
	 }
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
     }

	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
		 
	   genre = getArguments().getString(EXTRA_MESSAGE);
	   genreArray = getArguments().getStringArray(EXTRA_ARRAY);
	   View v = inflater.inflate(R.layout.fragment_grid, container, false);
       gv=(GridView)v.findViewById(R.id.gridview_genre);
       gv.setAdapter(new GridAdapter(this, getActivity(), genreArray));
 
       //messageTextView = (ScaleableTextView)v.findViewById(R.id.textview_genre);
	   //messageTextView.getLayoutParams().height = height/15;
	   //messageTextView.setText(genre);
	   return v;
	 }
     public void onActivityCreated(Bundle savedInstanceState) {
         super.onActivityCreated(savedInstanceState);

     }
     public void refreshGrid(int col){
    	 //gv.refreshDrawableState();
    	 gv.setNumColumns(col);
    	 gv.refreshDrawableState();
     }

	}

