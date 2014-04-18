package test.myflix;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private TextView mLoginStatusMessageView;
	
	pullDatabase dataTask;
	PostQuery query;
	ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dataTask = new pullDatabase(this);

		setContentView(R.layout.activity_login);
		
		
		// Set up the login form.
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(attemptLogin()){
							Intent mIntent = new Intent(getApplicationContext(), MovieCollection.class);
							startActivity(mIntent); 
						}
					}
				});
		
		findViewById(R.id.register_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent mIntent = new Intent(getApplicationContext(), RegisterActivity.class);
						startActivity(mIntent); 
					}
				}); 
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
	    MenuItem item= menu.findItem(R.id.action_video);
	    item.setEnabled(false);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent mIntent;
    	switch (item.getItemId()) {
    		case R.id.action_search:
    			mIntent = new Intent(getApplicationContext(), SearchActivity.class);
				startActivity(mIntent); 
    			return true;
    		case R.id.action_collection:
    			mIntent = new Intent(getApplicationContext(), MovieCollection.class);
				startActivity(mIntent); 
    			return true;
    		case R.id.action_video:
    			mIntent = new Intent(getApplicationContext(), VideoViewActivity.class);
				startActivity(mIntent); 
    			return true;
    		default:
    			return true;
    		}
    }
    @Override
    protected void onPostCreate (Bundle savedInstanceState){
    	super.onPostCreate(savedInstanceState);
    	onStartup();
    }
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public boolean attemptLogin() {

		// Reset errors.
		mEmailView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
			return false;
		} else 
		{
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			query = new PostQuery(this, "Signing in...");
			query.execute("SELECT * FROM login WHERE email = "+mEmail+" AND password = "+mPassword+"");
			while(query.finished == false)
			{
		    	try { Thread.sleep(100); 
		    		Log.v("mytag","still looping!");
		    	}
		    	catch (InterruptedException e) { e.printStackTrace(); }
			}
			if (query.getResult()!=null) 
			{
				return true;
			}
		}
		return false;
	}
	private void onStartup(){
		//checks for the last time the app was updated
		SharedPreferences prefs = this.getSharedPreferences("test.myflix", Context.MODE_PRIVATE);
		Time now = new Time();
		Time previous = new Time();
		String lastUpdate = prefs.getString("pullDate", "2000-01-01");
		previous.parse3339(lastUpdate);
		now.setToNow();
		now.parse3339(now.format3339(true));
		prefs.edit().putString("pullDate", now.format3339(true)).commit();
		if (now.after(previous)){
			dataTask = new pullDatabase(this);
			/*ProgressDialog pDialog = new ProgressDialog(getApplicationContext());
	        pDialog.setTitle("New Content");
            pDialog.setMessage("Updating ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show(); 
            */
			dataTask.execute(lastUpdate);
			while (dataTask.done == false) { //Loops at .1 sec intervals until AsyncTask has finished  
				try { Thread.sleep(100); 
			    	Log.v("mytag","still looping!");
			    	
			    }
			    catch (InterruptedException e) { e.printStackTrace(); }
			}
			//pDialog.dismiss();
			Log.v("mytag","made it out of the loop!");
		}
	}
}

	