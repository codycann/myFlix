package test.myflix;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

 
public class PostQuery extends AsyncTask<String, Void, JSONArray>
{
	public interface PostQueryListener{
		void onFinished(JSONArray json);
	}
	JSONArray json = null;
	public boolean finished;
	private PostQueryListener mListener = null;
	private Context context;
	String dialogText;
	private ProgressDialog pDialog;
	Connect_Class siteQuery = new Connect_Class();
	public PostQuery(Context context, String dialog) {
		this.context = context;
		dialogText = dialog;
	}
	@Override
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
		finished = false;
		json = null;
		try {  
			json = siteQuery.newQuery(args[0]);
		}
		catch (Exception e)
		{   
			e.printStackTrace();
		}
		finished = true;
		return json;
	}
	@Override 
	protected void onPostExecute(JSONArray result) {
		// TODO Auto-generated method stub
		pDialog.dismiss();
		if(mListener != null)
		{
			mListener.onFinished(result);
		}
	}
	
	public void setListener(PostQueryListener listen)
	{
		mListener = listen;
	}
	
	public void removeListener()
	{
		mListener = null;
	}
	
	boolean finished()
	{
		return finished;
	}
	JSONArray getResult()
	{
		return json;
	}
}
