package test.myflix;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class Connect_Class {	
	String result = "";
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	InputStream is = null;

public JSONArray newQuery(String query) {
	JSONArray jArray = null;
	try{
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("query",query));
			//nameValuePairs.add(new BasicNameValuePair("type", type));
			httpclient = new DefaultHttpClient();
	        httppost = new HttpPost("http://www.cannonmovies.us/sql_post.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
	}
	catch(Exception e){
	        Log.e("log_tag", "Error in http connection "+e.toString());
	}
	try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	        }
	        is.close();
	        result=sb.toString();
			Log.e("result",""+result);
	}
	catch(Exception e){
	        Log.e("log_tag", "Error converting result "+e.toString());
	}
	try{
			jArray = new JSONArray(result);
	}catch(JSONException e){
	        Log.e("log_tag", "Error parsing data "+e.toString());
	}
	return jArray;
}
}
