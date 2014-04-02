package test.myflix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Connect extends Activity {
    
    
    GridView gridView;
     
    static final String[] numbers = new String[] {

            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
        };
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.activity_connect);
 
        gridView = (GridView) findViewById(R.id.gridview1);  
 
      // Create adapter to set value for grid view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, numbers);
 
        gridView.setAdapter(adapter);
 
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {

               Toast.makeText(getApplicationContext(),
                ((TextView) v).getText()  , Toast.LENGTH_SHORT).show();

            }
        });
 
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connect, menu);
		return true;
	}
	public void function(){
			String query = "SELECT ...";
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
	}
}
