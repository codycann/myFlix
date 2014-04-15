package test.myflix;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageStorage {
    private Context context;
    ImageDownloader pullTask;
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    ArrayList<String> pullTitles = new ArrayList<String>();
    String file_title;
    
	String fullPath = Environment.getExternalStorageDirectory().toString();
	String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath().toString();

	public ImageStorage(Context context){
		this.context = context;
		Log.v("mytag", fullPath);
		Log.v("mytag", absolutePath);
	}
	public ArrayList<Bitmap> getThumbnail(ArrayList<String> titles) {
		for(int i = 0; i < titles.size(); i++){
			// Look for the file on the external storage
			Bitmap thumbnail = null;
			try {
				thumbnail = checkImage(titles.get(i));
				//If image is not on SD card add to list to download
				if(thumbnail == null){ 
					pullTitles.add(titles.get(i));
					Log.v("willPull", titles.get(i));
				}
			}
			catch (Exception e) {
				//Log.e("getThumbnail() on external storage", e.getMessage()); //catching throwing error?
			}
		}
		//Proceed to download any images not on SD card
		if(pullTitles != null){
			if(pullTitles.size() > 0){ 
				download();
				Log.v("downloaded","downloaded");
			}
		}
		for(int i = 0; i < titles.size(); i++){
			Bitmap poster = checkImage(titles.get(i));
			if(poster == null){
				Log.v("addimage", "null image!");
			}
			images.add(poster);
		}
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
			Log.i("isSdReadable", "External storage card is only readable.");
			mExternalStorageAvailable = true;
		} 
		else {
			mExternalStorageAvailable = false;
			Log.i("isSdReadable", "External storage card is not readable.");
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
			file_title = title.replace(":", "()");
			thumbnail = BitmapFactory.decodeFile(fullPath + "/myflix/" + title + ".jpeg");
			Log.v("checkimage", file_title);
		}
		if(thumbnail == null){
			Log.v("checkimage", "null image!");
		}
		return thumbnail;
	}
}
