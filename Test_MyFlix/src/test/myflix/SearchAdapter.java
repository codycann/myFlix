package test.myflix;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchAdapter extends ArrayAdapter<Movie>{

    Context context; 
    int layoutResourceId;    
    ArrayList<Movie> data = new ArrayList<Movie>();
    double width;
    double height;
    
    public SearchAdapter(Context context, int layoutResourceId, ArrayList<Movie> data, double width, double height) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.width = width;
        this.height = height;
    }
    public void refresh(ArrayList<Movie> list) {  
        data = list;  
        notifyDataSetChanged();  
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
            holder.titleView.setMaxWidth((int) (width*0.8));
            holder.ratingView = (TextView)row.findViewById(R.id.textview_rating);
            holder.ratingView.setMaxWidth((int) (width*0.2));
            
            row.setTag(holder);
        }
        else
        {
            holder = (MovieHolder)row.getTag();
        }
        
        Movie movie = data.get(position);
        holder.titleView.setText(movie.title);
        String rating = movie.rating;
        if(movie.rating == null)
        {
        	rating = "Ñ";
        }
        holder.ratingView.setText(rating);
        
        return row;
    }
    
    static class MovieHolder
    {
        TextView titleView;
        TextView ratingView;
    }
}
