package test.myflix;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

public class GridFragment extends Fragment {

	 public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	 GridView gv;
	 

	 public static final GridFragment newInstance(String genre) {
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
	   
	   TextView messageTextView = (TextView)v.findViewById(R.id.textview_genre);
	   messageTextView.setText(genre);
	   return v;

	 }

	}

