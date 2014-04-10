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
	 GridView gv;
	 static int width;
	 static int height;
	 public static final GridFragment newInstance(String genre, Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();		
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		GridFragment f = new GridFragment();
		Bundle bdl = new Bundle(1);
		bdl.putString(EXTRA_MESSAGE, genre);
		f.setArguments(bdl);
		return f;
	 }

	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	   String genre = getArguments().getString(EXTRA_MESSAGE);
	   View v = inflater.inflate(R.layout.fragment_grid, container, false);
       gv=(GridView)v.findViewById(R.id.gridview_genre);
       gv.setAdapter(new GridAdapter(getActivity(), genre));
	   
       ScaleableTextView messageTextView = (ScaleableTextView)v.findViewById(R.id.textview_genre);
	   messageTextView.getLayoutParams().height = height/15;
	   messageTextView.setText(genre);
	   return v;

	 }

	}

