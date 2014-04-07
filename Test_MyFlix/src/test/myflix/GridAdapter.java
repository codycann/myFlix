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
	ArrayList<Bitmap> imageList;
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
		return titles.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           View gridView;

          if (convertView == null) {

	     gridView = new View(context);

	gridView = inflater.inflate(R.layout.movie_view, null);

	TextView textView = (TextView) gridView.findViewById(R.id.movie_title);
	textView.setText(titles.get(position));
	
	//ImageView imageView = (ImageView) gridView.findViewById(R.id.movie_poster);
	//imageView.setImageBitmap(imageList.get(position));

} else {
	gridView = (View) convertView;
}

return gridView;

	}


	public void populate(){
		titles = siteData.getCol("title", "genre like ?",new String[]{"%"+newGenre+"%"}, null, null, null, "");
		imageList = imageContainer.getThumbnail(titles);
		siteData.manualClose();
		return;
	}
}

