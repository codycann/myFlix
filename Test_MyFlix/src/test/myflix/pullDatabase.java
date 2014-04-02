package test.myflix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

 
public class pullDatabase extends AsyncTask<String, Void, Void>
	    {
			String[] col = new String[]{"filename","title","year","rated","runtime",
					"awards","plot","imdbRating","Genre","Director","Writer","Actors"};
			JSONArray json;
	        private Context context;
	        private ProgressDialog pDialog;
	        Connect_Class siteQuery = new Connect_Class();
	    	DatabaseQuery siteData;
	        public pullDatabase(Context context) {
	            this.context = context;
	            siteData = new DatabaseQuery(context);
	            siteData.deleteAll();
	        }
	        protected void onPreExecute() {
	            // TODO Auto-generated method stub
	            pDialog = new ProgressDialog(context);
	            pDialog.setMessage("Updating ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }

	        protected Void doInBackground(String... site) {
	            // TODO Auto-generated method stub
	            try {  
	            	json = siteQuery.newQuery("SELECT * from movies");
            		JSONObject json_data = null; 
            		for(int i=0;i<json.length();i++){
	            		json_data = json.getJSONObject(i); 
	            		for(int j = 0; j < col.length; j++){
		            		String value=json_data.getString(col[j]).toString();
		            		siteData.appendData(col[j],value);
	            		}
		            	siteData.addRow(); 
            		} 
	            }
            	catch(JSONException e){ 
            		Log.e("log_tag", "Error parsing data "+e.toString()); 
            	}
				return null;    
	        }
	        protected void onPostExecute(String args) {
	            // TODO Auto-generated method stub
	            pDialog.dismiss();
	        }
}
