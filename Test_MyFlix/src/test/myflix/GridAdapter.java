package test.myflix;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private Context context;
	Bitmap[] imageList;
	ArrayList<String> titles;
	ImageStorage imageContainer;
	DatabaseQuery siteData;
	String newGenre;
	
	public GridAdapter(Context context, String genre) {
		super();
		newGenre = genre;
		this.context = context;
		imageContainer = new ImageStorage(context);
		siteData = new DatabaseQuery(context);
		populate();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageList.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           View gridView;

          if (convertView == null) {

	     gridView = new View(context);

	// get layout from android.xml
	gridView = inflater.inflate(R.layout.movie_view, null);

	// set image
	ImageView imageView = (ImageView) gridView.findViewById(R.id.movie_poster);
	TextView textView = (TextView) gridView.findViewById(R.id.movie_title);

	textView.setText(titles.get(position));
	imageView.setImageBitmap(imageList[position]);

} else {
	gridView = (View) convertView;
}

return gridView;

	}


	public void populate(){
		for(int i = 0; i < titles.size(); i++){
			titles = siteData.getCol("title", "genre like ?",new String[]{"%"+newGenre+"%"}, null, null, null, null);
			imageList[i] = imageContainer.getThumbnail(titles.get(i));
		}
	}
}

