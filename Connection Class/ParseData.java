//JAVA Class Connect_Class function: JSON Array newQuery(String)

//Test Query in PHPMyAdmin before passing to newQuery to insure well formatted queries.
//Using Class: See code below.

String query = "SELECT ..." 
Connect_Class connect = new Connect_Class(); 
try{ 
	JSONArray jArray = connect.newQuery(query); 
	JSONObject json_data = null; 
	for(int i=0;i<jArray.length();i++){ //loops for each row in result 
	json_data = jArray.getJSONObject(i); 
	//repeat the below line for every parameter that you want 
	String var_value=json_data.getString("col_name").toString(); //col_name is the name of the sql column 
	} 
}
catch(JSONException e){ 
	Log.e("log_tag", "Error parsing data "+e.toString()); 
}