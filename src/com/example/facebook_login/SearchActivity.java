package com.example.facebook_login;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class SearchActivity extends ActionBarActivity implements OnClickListener, LocationListener{
	private EditText text_search, text_nearby;
	private ImageButton search_button, nearby_button;
	private TextView access_token;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	
	private final static int
    CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	private Location location;
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	TextView txtLat;
	String lat;
	String provider;
	protected String latitude,longitude; 
	protected boolean gps_enabled,network_enabled;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		txtLat = (TextView) findViewById(R.id.textView11);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//		double longitude = location.getLongitude();
//		double latitude = location.getLatitude();
		
		// getting GPS status
		gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// getting network status
		network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		if (gps_enabled) {
			locationManager.requestLocationUpdates(
			LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
			MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
			} else if (network_enabled) {
			locationManager.requestLocationUpdates(
			LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
			MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
			};
		//txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
//		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
		txtLat = (TextView) findViewById(R.id.textView11);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
		
		text_search = (EditText)findViewById(R.id.text_search);
		text_nearby = (EditText)findViewById(R.id.text_nearby);
		access_token = (TextView)findViewById(R.id.access_token);
		search_button = (ImageButton)findViewById(R.id.search_button);
		nearby_button = (ImageButton)findViewById(R.id.nearby_button);
		search_button.setOnClickListener(this);
//		Intent intent = getIntent();
//		Bundle newbundle = intent.getExtras();
//		String access = newbundle.getString("Access Token");
//		access_token.setText(access);
	}
	

	@Override
	public void onLocationChanged(Location location) {
	txtLat = (TextView) findViewById(R.id.textView11);
	txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
	Log.d("Latitude","disable");
	}

	@Override
	public void onProviderEnabled(String provider) {
	Log.d("Latitude","enable");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	Log.d("Latitude","status");
	}
		
	


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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

	@Override
    protected void onStart() {
        super.onStart();
    //        if (!mResolvingError) {  // more about this later
//            mGoogleApiClient.connect();
//        }
    }

    @Override
    protected void onStop() {
//        mGoogleApiClient.disconnect();
//        super.onStop();
        
        
        // After disconnect() is called, the client is considered "dead".
    

        super.onStop();
    }
    
    @Override
    public void onPause() {

        // Save the current setting for updates
    
        super.onPause();
    }
    
    @Override
    public void onResume() {
        super.onResume();

        // If the app already has a setting for getting location updates, get it
       

    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String text_from_search = text_search.getText().toString();
		Toast.makeText(SearchActivity.this,text_from_search,Toast.LENGTH_SHORT).show();
		
		
		
//		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//      pref.edit().putString("autoSave", text_search.getText().toString()).commit();
//		text_search=;
	}


//	public void onLocationChanged(Location location) {
//
//        // Report to the UI that the location was updated
//		Location text_from_search = location;
//		Toast.makeText(SearchActivity.this,(CharSequence) text_from_search,Toast.LENGTH_SHORT).show();
//
//        // In the UI, set the latitude and longitude to the value received
////        mLatLng.setText(LocationUtils.getLatLng(this, location));
//    }
	
	private void showErrorDialog(int errorCode) {
		// TODO Auto-generated method stub
		
    }
	

}
