package test.myflix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

 
public class ImageDownloader extends AsyncTask<String, Void, Void>
	    {
	        private Context context;
	        private ProgressDialog pDialog;
	        String image_url;
	        URL myFileUrl = null;
	        Bitmap bmImg = null;
	        public ImageDownloader(Context context) {
	            this.context = context;
	        }
	        protected void onPreExecute() {
	            // TODO Auto-generated method stub
	            pDialog = new ProgressDialog(context);
	            pDialog.setMessage("Downloading Image ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();

	        }

	        protected Void doInBackground(String... args) {
	            // TODO Auto-generated method stub

	            try {  
	                myFileUrl = new URL("http://cannonmovies.us/posters/"+args[0]+".jpeg");
	                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();   
	                conn.setDoInput(true);   
	                conn.connect();     
	                InputStream is = conn.getInputStream();
	                bmImg = BitmapFactory.decodeStream(is); 
	            }
	            catch (IOException e)
	            {   
	                e.printStackTrace();  
	            }     
	            //store image
				if(isSdReadable()){
					String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
					OutputStream outStream = null;
					File file = new File(extStorageDirectory, "er.jpeg");
					try {
				    outStream = new FileOutputStream(file);
				    bmImg.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
				    outStream.flush();
				    outStream.close();          		   
					} catch (FileNotFoundException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
					} catch (IOException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
					}
				}
	            return null;
	        }
	        protected void onPostExecute(String args) {
	            // TODO Auto-generated method stub

	            pDialog.dismiss();

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