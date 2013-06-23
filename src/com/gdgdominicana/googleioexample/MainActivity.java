package com.gdgdominicana.googleioexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

 /*
  * Author: Alberto Estrella <aeflorenzan@gmail.com>
  * Google Dev-Tools
  */
public class MainActivity extends Activity implements Observer, OnItemLongClickListener {

	private ArrayAdapter<User> usersAdapter;
	private List<User> users;
	
	private ListView mUsersListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		usersAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1);
		
		mUsersListView = (ListView) findViewById(R.id.users_list);
		mUsersListView.setAdapter(usersAdapter);
		
		mUsersListView.setOnItemLongClickListener(this);
		
		new UsersListTask(this).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.action_refresh:
			refreshList();
			break;
			
		case R.id.action_add:
			addUser();
			break;

		default:
			break;
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

	private void refreshList(){
		new UsersListTask(this).execute();
	}
	
	private void addUser(){
		Intent intent = new Intent(this, UserActivity.class);
		startActivityForResult(intent, 100);
	}
	
 	private class UsersListTask extends AsyncTask<Void, Void, List<User>>  {

		private Observer observer;
		
		public UsersListTask(Observer observer){
			this.observer = observer;
		}
		
		
		@Override
		protected List<User> doInBackground(Void... params) {
			
			List<User> users = UserInfoService.getUsers();
			
			return users;
		}
		
		
		@Override
		protected void onPostExecute(List<User> result) {
			
			observer.update(null, result);
			
			super.onPostExecute(result);
		}
		
	}

	@Override
	public void update(Observable observable, Object data) {
		
		
		if(null != data) {
		
			this.users = (List<User>) data;
			usersAdapter.clear();
			
			for (User user : this.users) {
				usersAdapter.add(user);
			}
			
			usersAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(this, "Error obteniendo la lista de usuarios", Toast.LENGTH_LONG).show();
		}
	}
	

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (resultCode) {
		case Activity.RESULT_OK:
			
			refreshList();
			
			break;

		case Activity.RESULT_CANCELED:
			
			Toast.makeText(this, "Actividad cancelada por el usaurio", Toast.LENGTH_LONG).show();
			
			break;
		default:
			break;
		}
		
		
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos,
			long id) {
		
		
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Desea eliminar el usuario " + users.get(pos).getUsername() + "?")
					.setCancelable(false)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});
			
			AlertDialog dialog = builder.create();
			dialog.show();
			
			
		
		return false;
	}
}
