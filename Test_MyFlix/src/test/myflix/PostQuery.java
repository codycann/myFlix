	package test.myflix;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

 
public class PostQuery extends AsyncTask<String, Void, JSONArray>
	    {
			JSONArray json = null;
	        private Context context;
	        String dialogText;
	        private ProgressDialog pDialog;
	        Connect_Class siteQuery = new Connect_Class();
	        public PostQuery(Context context, String dialog) {
	            this.context = context;
	            dialogText = dialog;
	        }
	        protected void onPreExecute() {
	            // TODO Auto-generated method stub
	            pDialog = new ProgressDialog(context);
	            pDialog.setMessage(dialogText);
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }

	        protected JSONArray doInBackground(String... args) {
	            // TODO Auto-generated method stub
	            try {  
	            	json = siteQuery.newQuery(args[0]);
	            }
	            catch (Exception e)
	            {   
	                e.printStackTrace();
	            }     
	            return json;
	        }
	        protected void onPostExecute(String args) {
	            // TODO Auto-generated method stub
	            pDialog.dismiss();
	        }
}
