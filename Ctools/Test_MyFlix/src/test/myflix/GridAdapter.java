package test.myflix;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private Context context;
	ArrayList<Bitmap> imageList;
	ArrayList<String> titles;
	ArrayList<String> rating;
	ImageStorage imageContainer;
	DatabaseQuery siteData;
	String newGenre;
	ImageView poster;
	ScaleableTextView description;
	TextView imdb;
	double width;
	double height;

	
	public GridAdapter(Context context, String genre) {
		super();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();		
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		newGenre = genre;
		this.context = context;
		imageContainer = new ImageStorage(this.context);
		siteData = new DatabaseQuery(context);
		populate();
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
		View gridView;
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
		poster.getLayoutParams().height = (int) (height/(2.2));
		poster.getLayoutParams().width = (int) (width/(2.2));
		poster.setOnClickListener(new movieListener(titles.get(position)));
	    if(imageList != null){
	    	poster.setImageBitmap(imageList.get(position));
	    }
        return gridView;
	}

	public void populate(){
		titles = siteData.getCol("title", "genre like ?",new String[]{"%"+newGenre+"%"}, null, null, null, "");
		rating = siteData.getCol("imdbRating", "genre like ?",new String[]{"%"+newGenre+"%"}, null, null, null, "");
		siteData.manualClose();
		imageList = imageContainer.getThumbnail(titles);

		return;
	}
}

