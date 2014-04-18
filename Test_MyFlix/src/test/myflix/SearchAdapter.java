package test.myflix;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchAdapter extends ArrayAdapter<Movie>{

    Context context; 
    int layoutResourceId;    
    ArrayList<Movie> data = new ArrayList<Movie>();
    
    public SearchAdapter(Context context, int layoutResourceId, ArrayList<Movie> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MovieHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new MovieHolder();
            holder.titleView = (TextView)row.findViewById(R.id.textview_title);
            holder.ratingView = (TextView)row.findViewById(R.id.textview_rating);
            
            row.setTag(holder);
        }
        else
        {
            holder = (MovieHolder)row.getTag();
        }
        
        Movie movie = data.get(position);
        holder.titleView.setText(movie.title);
        holder.ratingView.setText(movie.rating);
        
        return row;
    }
    
    static class MovieHolder
    {
        TextView titleView;
        TextView ratingView;
    }
}
