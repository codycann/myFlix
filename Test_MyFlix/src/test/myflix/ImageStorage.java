package test.myflix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageStorage {
    private Context context;
	
	public ImageStorage(Context context){
		this.context = context;
	}
	public Bitmap getThumbnail(String title) {

		String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		Bitmap thumbnail = null;

		// Look for the file on the external storage
		try {
		if (isSdReadable() == true) {
		thumbnail = BitmapFactory.decodeFile(fullPath + "/" + title+".jpeg");
		}
		} catch (Exception e) {
		Log.e("getThumbnail() on external storage", e.getMessage());
		}

		// If no file on external storage, pull from web
		if (thumbnail == null) {
		try {
			new ImageDownloader(context).execute(title);
			thumbnail = BitmapFactory.decodeFile(fullPath + "/" + title+".jpeg");
		} catch (Exception ex) {
		Log.e("getThumbnail() pulled from web", ex.getMessage());
		}
		}
		return thumbnail;
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
}
