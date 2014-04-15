package test.myflix;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private Context context;
	ArrayList<Bitmap> imageList;
	ArrayList<String> titles;
	ArrayList<String> rating;
	ImageStorage imageContainer;
	DatabaseQuery siteData;
	String [] genreArray;
	ImageView poster;
	ScaleableTextView description;
	TextView imdb;
	double width;
	double height;
	View gridView;
	GridFragment grid;

	
	public GridAdapter(GridFragment parent, Context context, String [] genres) {
		super();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();		
		Point size = new Point();
		display.getSize(size);
		genreArray = genres;
		width = size.x;
		height = size.y;
		this.context = context;
		this.grid = parent;
		imageContainer = new ImageStorage(this.context);
		siteData = new DatabaseQuery(context);
		populate();
	}
	public void onConfigurationChanged(Configuration newConfig) {
		setOrientation();
	}

	@Override
	public int getCount() {
		return titles.size();
	}

	@Override
	public Object getItem(int position) {
		return imageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		gridView = new View(context);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    gridView = inflater.inflate(R.layout.movie_view, parent, false);
		}
		else {
			gridView = (View) convertView;
		}

	    description = (ScaleableTextView) gridView.findViewById(R.id.movie_title);
		description.setText(titles.get(position));
		
		imdb = (TextView) gridView.findViewById(R.id.movie_rating);
		imdb.setText(rating.get(position));
		

		poster = (ImageView) gridView.findViewById(R.id.movie_poster);
		setOrientation();
		poster.setOnClickListener(new movieListener(titles.get(position)));
	    if(imageList != null){
	    	poster.setScaleType(ScaleType.FIT_XY);
	    	poster.setImageBitmap(imageList.get(position));
	    }
        return gridView;
	}

	public void populate(){
		String condition = "genre like ?";
		for(int i = 1; i < genreArray.length; i++){
			condition = condition + " AND genre like ?";
		}
		
		titles = siteData.getCol("title", condition, genreArray, null, null, null, "");
		rating = siteData.getCol("imdbRating", condition, genreArray, null, null, null, "");
		siteData.manualClose();
		imageList = imageContainer.getThumbnail(titles);

		return;
	}
	public void setOrientation(){
		int orientation = context.getResources().getConfiguration().orientation;
		if(orientation == 1){
			grid.refreshGrid(2);
			poster.getLayoutParams().height = (int) (height/(2.2));
			poster.getLayoutParams().width = (int) (width/(2.2));
		}
		else{
			grid.refreshGrid(3);
			poster.getLayoutParams().width = (int) (width/3.5);
			poster.getLayoutParams().height = (int) (height/1.5);
		}
	}
}

