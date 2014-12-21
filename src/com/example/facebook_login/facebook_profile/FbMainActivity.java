package com.example.facebook_login.facebook_profile;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.facebook_login.MainActivity;
import com.example.facebook_login.R;
import com.example.facebook_login.facebook_profile.pictureAsync.onImageDownloaded;
import com.example.facebook_login.facebook_profile.profileAsync.profileImplement;
import com.facebook.Session;
import com.facebook.model.GraphUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class FbMainActivity extends Activity implements profileImplement, onImageDownloaded {
	
	ProgressDialog dialog;
	MainActivity mainactivity = new MainActivity();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fbprofile_main);
			
		String url = "https://graph.facebook.com/" + mainactivity.userid;
		Log.d("URL",url);
		pictureAsync picTask = new pictureAsync(this);
		profileAsync task = new profileAsync(this);
		picTask.execute(url + "/picture?type=large");
		picTask.delegate = this;
		task.delegate = this;
		task.execute(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	public static User parseJSON(String jsonString) throws JSONException{
		JSONObject top = new JSONObject(jsonString);
		String name = "NA";
		if(top.has("name"))
		name = top.getString("name");
		String username = top.getString("username");
		String id = "NA";
		if(top.has("id"))
		id = top.getString("id");
		String gender = "NA";
		if(top.has("gender"))
		gender = top.getString("gender");
		String link = "https://www.facebook.com/"+username;
		if(top.has("link"))
		link = top.getString("link");
		User output = new User(name, username, id, gender, link);
		return output;
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		if(user == null){
			Toast t = Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT);
			t.show();
		}
		else{
			TextView name = (TextView) findViewById(R.id.name);
//			TextView id = (TextView) findViewById(R.id.id);
//			TextView gender = (TextView) findViewById(R.id.gender);
			TextView link = (TextView) findViewById(R.id.link);
			name.setText(user.name);
			link.setText(user.link);
		}
	}

	@Override
	public void setImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		if(bitmap != null){
			ImageView image = (ImageView) findViewById(R.id.imageView1);
			image.setImageBitmap(bitmap);
		}
	}

}
