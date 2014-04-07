package test.myflix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

 
public class ImageDownloader extends AsyncTask<ArrayList<String>, Void, ArrayList<Bitmap>>
	    {
	        private Context context;
	        private ProgressDialog pDialog;
	        String image_url;
	        URL myFileUrl = null;
	        Bitmap bmImg = null;
	        boolean done = false;
	        ArrayList<Bitmap> images;
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

	        protected ArrayList<Bitmap> doInBackground(ArrayList<String>... args) {
	            // TODO Auto-generated method stub
	        	for(int i = 0; i < args[0].size(); i++){
		            try {
		            	String url = "http://cannonmovies.us/posters/"+args[0].get(i)+".jpeg";
		            	url = url.replace(" ", "%20");
		                myFileUrl = new URL(url);
		                Log.v("url", url);
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
						File file = new File(extStorageDirectory,""+args[0].get(i)+".jpeg");
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
	        	}
	        	done = true;
	            return images;
	        }
	        @Override
	        protected void onPostExecute(ArrayList<Bitmap> args) {
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