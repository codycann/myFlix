package test.myflix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class RegisterActivity extends Activity {

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;
	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private TextView mLoginStatusMessageView;
	
	PostQuery query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// Set up the login form.
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);


		findViewById(R.id.register_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(attemptRegister()){
							Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
							SharedPreferences prefs = getApplicationContext().getSharedPreferences("test.myflix", Context.MODE_PRIVATE);
							prefs.edit().putString("email", mEmail).commit();
							prefs.edit().putString("password", mPassword).commit();
							Intent mIntent = new Intent(getApplicationContext(), LoginActivity.class);
							startActivity(mIntent); 
						}
					}
				});
	}

	public boolean attemptRegister() {

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
		} else if ((mPassword.length() < 4) || (mPassword.length() > 15) ) {
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
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			query = new PostQuery(this, "Registering...");
			query.execute("SELECT * FROM `login` WHERE `email` = \""+mEmail+"\"");
			while(query.finished == false)
			{
		    	try { Thread.sleep(100); 
		    		Log.v("mytag","still looping!");
		    	}
		    	catch (InterruptedException e) { e.printStackTrace(); }
			}
			if (query.getResult() == null) 
			{
				Log.v("mytag","email not present");
				query = new PostQuery(this, "Registering...");
				query.execute("INSERT INTO `login`(`email`, `password`) VALUES (\""+mEmail+"\", \""+mPassword+"\")");
				while(query.finished == false)
				{
			    	try { Thread.sleep(100); 
			    		Log.v("mytag","still looping!");
			    	}
			    	catch (InterruptedException e) { e.printStackTrace(); }
				}
				return true;
			}
			else{
				mEmailView.setError(getString(R.string.error_invalid_email));
				focusView = mEmailView;
				return false;
			}
		}
	}
}
