package test.myflix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

 
public class pullDatabase extends AsyncTask<String, Void, Boolean>
	    {
			String[] col = new String[]{"filename","Title","Year","Rated","Runtime",
					"Awards","Plot","imdbRating","Genre","Director","Writer","Actors"};
			boolean done = false;
			JSONArray json;
	        private Context context;
	        Connect_Class siteQuery = new Connect_Class();
	    	DatabaseQuery siteData;
	        public pullDatabase(Context context) {
	            this.context = context;
	            siteData = new DatabaseQuery(context);
	            siteData.deleteAll();
	        }
	        @Override
	        protected void onPreExecute() {
	            // TODO Auto-generated method stub
	           
	        }
	        @Override
	        protected Boolean doInBackground(String... date) {
	            // TODO Auto-generated method stub
	            try {
	            	//json = siteQuery.newQuery("SELECT * from movies WHERE date_added > "+ date[0]);
	            	json = siteQuery.newQuery("SELECT * from movies");
            		JSONObject json_data = null;
            		if(json != null){
	            		for(int i=0;i<json.length();i++){
		            		json_data = json.getJSONObject(i); 
		            		for(int j = 0; j < col.length; j++){
			            		String value=json_data.getString(col[j]).toString();
			            		siteData.appendData(col[j],value);
		            		}
			            	siteData.addRow(); 
	            		}
            		}
	            }
            	catch(JSONException e){ 
            		Log.e("log_tag", "Error parsing data "+e.toString());
    	        	done = true;
            		return false;
            	}
	            Log.v("mytag","finished pulling data");
	        	done = true;
				return true;    
	        }
	        @Override
	        protected void onPostExecute(Boolean result) {
	            // TODO Auto-generated method stub
	            Log.v("mytag","post execute");
	            return;
	        }
}
