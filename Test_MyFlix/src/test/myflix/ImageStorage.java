package test.myflix;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageStorage {
    private Context context;
    ImageDownloader pullTask;
    ArrayList<Bitmap> images;
    ArrayList<String> pullTitles;
    
	String fullPath = Environment.getExternalStorageDirectory().toString();
	
	public ImageStorage(Context context){
		this.context = context;
	}
	public ArrayList<Bitmap> getThumbnail(ArrayList<String> titles) {
		//String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		for(int i = 0; i < titles.size(); i++){
			// Look for the file on the external storage
			Bitmap thumbnail = null;
			try {
				thumbnail = checkImage(titles.get(i));
				if(thumbnail == null){
					pullTitles.add(titles.get(i));
				}
			}
			catch (Exception e) {
				Log.e("getThumbnail() on external storage", e.getMessage());
			}
		}
		if(pullTitles.size() > 0){
			download();
		}
		for(int i = 0; i < titles.size(); i++)
			images.add(checkImage(titles.get(i)));
		return images;
	}
	public boolean isSdReadable() {
		boolean mExternalStorageAvailable = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mExternalStorageAvailable = true;
			Log.i("isSdReadable", "External storage card is readable.");
		} 
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			Log.i("isSdReadable", "External storage card is readable.");
			mExternalStorageAvailable = true;
		} 
		else {
			mExternalStorageAvailable = false;
		}
		return mExternalStorageAvailable;
	}
	@SuppressWarnings("unchecked")
	public void download(){
		try{
			pullTask = new ImageDownloader(context);
			pullTask.execute(pullTitles);
			while(pullTask.done == false){
		    	try { Thread.sleep(100); 
		    		Log.v("mytag","still looping!");
		    	}
		    	catch (InterruptedException e) { e.printStackTrace(); }
			}
		} 
		catch (Exception ex) {
		Log.e("getThumbnail() pulled from web", ex.getMessage());
		}
	}
	public Bitmap checkImage(String title){
		Bitmap thumbnail = null;
		if (isSdReadable() == true) {
			thumbnail = BitmapFactory.decodeFile(fullPath + "/" + title + ".jpeg");
			Log.v("mytag", title);
			images.add(thumbnail);
		}
		return thumbnail;
	}
}
