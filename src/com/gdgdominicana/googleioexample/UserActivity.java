package com.gdgdominicana.googleioexample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class UserActivity extends Activity implements Observer {

	EditText mUsernameView;
	EditText mPasswordView;
	EditText mSignatureView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		// Show the Up button in the action bar.
		
		mUsernameView = (EditText) findViewById(R.id.username);
		mPasswordView = (EditText) findViewById(R.id.password);
		mSignatureView = (EditText) findViewById(R.id.signature);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save :
			save();
			return true;
		case R.id.action_cancel :
			cancel();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void save(){
		
		if(TextUtils.isEmpty( mUsernameView.getText() )){
			mUsernameView.setError("Requerido");
			mUsernameView.requestFocus();
			
			return;
		}
		
		if(TextUtils.isEmpty( mPasswordView.getText() )){
			mPasswordView.setError("Requerido");
			mPasswordView.requestFocus();
			
			return;
		}
		
		
		User user = new User();
		user.setUsername(mUsernameView.getText().toString());
		user.setPassword(mPasswordView.getText().toString());
		user.setSignature(mSignatureView.getText().toString());
		
		new AddUserTask(this).execute(user);
		
		
	}

	
	private void cancel(){
		setResult(RESULT_CANCELED);
		finish();
	}
	
	
	
	public class AddUserTask extends AsyncTask<User, Void, User> {

		
		ProgressDialog dialog = null;
		Observer observer;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(UserActivity.this);
			dialog.setMessage("Registrando...");
			dialog.setCancelable(false);
			dialog.show();
			
			super.onPreExecute();
		}
		
		public AddUserTask(Observer observer) {
			this.observer = observer;
		}
		
		@Override
		protected User doInBackground(User... args) {
			User user = UserInfoService.addUser(args[0]);
			return user;
		}
		
		@Override
		protected void onPostExecute(User result) {
			
			if(null != dialog){
				dialog.dismiss();
				dialog = null;
			}
			
			
			this.observer.update(null, result);
			super.onPostExecute(result);
		}
		
	}



	@Override
	public void update(Observable observable, Object data) {
		
		if(null != data) {
			setResult(RESULT_OK);
			finish();
		}
		else {
			Toast.makeText(this, "Ocurrio un error creado el usuario", Toast.LENGTH_LONG).show();
		}
		
	}
	
	
	
}
