package com.example.facebook_login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

//import edu.cmu.yahoo.travelog.DisplayPics.FetchBitmaps;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.facebook_login.facebook_profile.FbMainActivity;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;


public class SearchActivity extends ActionBarActivity implements OnClickListener, LocationListener{
	 
	private UiLifecycleHelper uiHelper;
	String textlocation, gpslocation;
	String filename;
	ImageButton about_me;
	private EditText text_nearby;
	AutoCompleteTextView text_search;
	private ImageButton search_button, nearby_button;
	private TextView access_token;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	private ProgressDialog pDialog;
	private final static int
    CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	private Location location;
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	TextView txtLat;
	private String locationData;
	String lat;
	String provider;
	protected String latitude,longitude; 
	protected boolean gps_enabled,network_enabled;
	
	private String fbaccesstoken;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
			
		Session session = Session.getActiveSession();

	    if (session.isOpened()) {
	    	fbaccesstoken = session.getAccessToken();
	        Log.d("Access Token", session.getAccessToken());
	    }
			
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
		
		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		locationData = Double.toString(location.getLatitude()) + ","+Double.toString(location.getLongitude());
		
		about_me = (ImageButton)findViewById(R.id.about_me_button);
		text_search = (AutoCompleteTextView)findViewById(R.id.text_search);
		String place = text_search.getText().toString();
		search_button = (ImageButton)findViewById(R.id.search_button);
		nearby_button = (ImageButton)findViewById(R.id.gpsButton);
 
		search_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				testHttp();
				Intent intent = new Intent(SearchActivity.this, PlacesListViewActivity.class);
				intent.putExtra("Filename",filename);
				startActivity(intent);
				pDialog = new ProgressDialog(SearchActivity.this);
				// Showing progress dialog before making http request
				pDialog.setMessage("Loading...Please Wait");
				pDialog.show();
			}
		});
		
		nearby_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				testHttp();
				Intent i = new Intent(SearchActivity.this, PlacesListViewActivity.class);
				i.putExtra("Filename",filename);
				startActivity(i);
				pDialog = new ProgressDialog(SearchActivity.this);
				// Showing progress dialog before making http request
				pDialog.setMessage("Loading...Please Wait");
				pDialog.show();
            }
           
        });
		
		
		about_me.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SearchActivity.this, FbMainActivity.class);
				startActivity(i);
				pDialog = new ProgressDialog(SearchActivity.this);
				// Showing progress dialog before making http request
				pDialog.setMessage("Loading...Please Wait");
				pDialog.show();
            }
           
        });

		
	}
	
	@Override
	public void onLocationChanged(Location location) {
	txtLat = (TextView) findViewById(R.id.text_nearby);
	txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
//	locationData = String.valueOf(location.getLatitude()) + ","+String.valueOf(location.getLongitude());
	locationData = Double.toString(location.getLatitude()) + ","+Double.toString(location.getLongitude());
//	Log.d("Location Data", locationData);
	}
	
	private void testHttp() {
		// TODO Auto-generated method stub
		

	    Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	HttpClient client = new DefaultHttpClient();
			    HttpPost post = new HttpPost("http://ec2-54-164-195-102.compute-1.amazonaws.com/api_places/");
	    try {
	    	
	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      gpslocation = locationData;
	      nameValuePairs.add(new BasicNameValuePair("gps_location", gpslocation));
	      nameValuePairs.add(new BasicNameValuePair("access_token", fbaccesstoken));
	      if(text_search.getText().toString()!=null){
	    	  textlocation = text_search.getText().toString();
	    	  nameValuePairs.add(new BasicNameValuePair("text_location", textlocation));
	      	}
			else{
				
			}
	      
		    Log.d("JSON",nameValuePairs.toString());
	      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	      HttpResponse response = client.execute(post);
	      Log.d("Response",response.toString());
	      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	      String line = "";
	      while ((line = rd.readLine()) != null) {
	        System.out.println(line);
		    Log.d("Line",line);
		    try {
		        JSONObject json= (JSONObject) new JSONTokener(line).nextValue();
//		        JSONObject json2 = json.getJSONObject("filename");
		        filename = (String) json.get("filename");
		        Log.d("Test",filename);
		    } catch (JSONException e) {
		        e.printStackTrace();
		    }
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	    });
	    thread.start();
	    try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
    protected void onStart() {
        super.onStart();
   }

    @Override
    protected void onStop() {
        super.onStop();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    
        // Save the current setting for updates
    
//        super.onPause();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
      
    }
    
    @Override
    public void onResume() {
        super.onResume();
      
        // If the app already has a setting for getting location updates, get it
       

    }
    
    private void onError(Exception exception) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage(exception.getMessage()).setPositiveButton("OK", null);
        builder.show();
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        @Override
//        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            uiHelper.onActivityResult(requestCode, resultCode, data);
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
            if (Session.getActiveSession() != null || Session.getActiveSession().isOpened()){
                        Intent i = new Intent(SearchActivity.this,SearchActivity.class);
                        startActivity(i);
                    }
           
        
    }
    
      
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			testHttp();
		    Intent intent = new Intent(SearchActivity.this, PlacesListViewActivity.class);
			intent.putExtra("Filename",filename);
			startActivity(intent);
//			pDialog = new ProgressDialog(SearchActivity.this);
//			// Showing progress dialog before making http request
//			pDialog.setMessage("Loading...Please Wait");
//			pDialog.show();
	}
	private void showErrorDialog(int errorCode) {
		// TODO Auto-generated method stub
		
    }
	

}
