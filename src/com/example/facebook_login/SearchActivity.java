package com.example.facebook_login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.facebook_login.facebook_profile.FbMainActivity;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.model.GraphLocation;
import com.facebook.model.GraphPlace;








//import edu.cmu.yahoo.travelog.DisplayPics.FetchBitmaps;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class SearchActivity extends ActionBarActivity implements OnClickListener, LocationListener{
	 
	private UiLifecycleHelper uiHelper;
	String textlocation, gpslocation;
	String filename;
	Button about_me;
	private EditText text_nearby;
	AutoCompleteTextView text_search;
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
		
		
		
	
		 
//		txtLat = (TextView) findViewById(R.id.textView11);
		
//		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
//		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//		double longitude = location.getLongitude();
//		double latitude = location.getLatitude();
		
		
		/* working code
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
			}
			else{
				Toast.makeText(this, "Not able to fetch current Location", Toast.LENGTH_LONG).show();
			}
			
			*/
			
			
	
		
		
		//txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
//		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
			
			
			Session session = Session.getActiveSession();
//			fbaccesstoken = session.getAccessToken(); 
		    if (session.isOpened()) {
		    	fbaccesstoken = session.getAccessToken();
		        //Toast.makeText(this, session.getAccessToken(), Toast.LENGTH_LONG).show();
		        Log.d("Access Token", session.getAccessToken());
		    }
			
			//working code
//		txtLat = (TextView) findViewById(R.id.textView11);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
		
		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		locationData = Double.toString(location.getLatitude()) + ","+Double.toString(location.getLongitude());
		
		about_me = (Button)findViewById(R.id.about_me_button);
		
//		text_search = (EditText)findViewById(R.id.text_search);
		text_search = (AutoCompleteTextView)findViewById(R.id.text_search);
//		text_search.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));

//		text_search.setOnClickListener(this);
		String place = text_search.getText().toString();
//		text_nearby = (EditText)findViewById(R.id.text_nearby);
//		access_token = (TextView)findViewById(R.id.access_token);
		search_button = (ImageButton)findViewById(R.id.search_button);
		nearby_button = (ImageButton)findViewById(R.id.gpsButton);
	
		
		
    
		search_button.setOnClickListener(this);
		nearby_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				testHttp();
				Intent i = new Intent(SearchActivity.this, PlacesListViewActivity.class);
				i.putExtra("Filename",filename);
				startActivity(i);
//                Toast.makeText(getBaseContext(),"Opening Map..",Toast.LENGTH_SHORT).show();
//                onClickGPS();
               
            }
           
        });
		
		about_me.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				testHttp();
				Intent i = new Intent(SearchActivity.this, FbMainActivity.class);
				startActivity(i);
//                Toast.makeText(getBaseContext(),"Opening Map..",Toast.LENGTH_SHORT).show();
//                onClickGPS();
               
            }
           
        });

		
		//		Intent intent = getIntent();
//		Bundle newbundle = intent.getExtras();
//		String access = newbundle.getString("Access Token");
//		access_token.setText(access);
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
//	      Log.d("GPSLocationData", locationData);
	      gpslocation = locationData;
//	      String gps = "'" + gpslocation + "'";
//	      Log.d("GPSLocation", gpslocation);
	      nameValuePairs.add(new BasicNameValuePair("gps_location", gpslocation));
	      nameValuePairs.add(new BasicNameValuePair("access_token", fbaccesstoken));
	      if(text_search.getText().toString()!=null){
	    	  textlocation = text_search.getText().toString();
	    	  nameValuePairs.add(new BasicNameValuePair("text_location", textlocation));
	      	}
			else{
				
//				textlocation = locationData.toString();
//				nameValuePairs.add(new BasicNameValuePair("gps_location", textlocation));
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
		
//			HttpClient httpclient = new DefaultHttpClient();
//		    HttpPost httppost = new HttpPost("https://posttestserver.com/post.php?location="+text_search.getText());
			testHttp();
		    Intent intent = new Intent(SearchActivity.this, PlacesListViewActivity.class);
//			intent.putExtra("Location",locationData);
			intent.putExtra("Filename",filename);
			
//			if(text_search.toString()!=null){
//				intent.putExtra("Location", text_search.getText().toString());
//				intent.putExtra("AccessToken",fbaccesstoken);
//			}
//			else{
//				intent.putExtra("Filename",filename);
//			intent.putExtra("AccessToken",fbaccesstoken);
//			}
			startActivity(intent);

//			HttpClient client = new DefaultHttpClient();
//			URI uri = new URI("https","posttestserver.com/post.php?location="+ text_search.getText()+"latitude=''&longitude=''&access_token=" + fbaccesstoken , null);
//			HttpGet get = new HttpGet(uri.toASCIIString());
//			HttpResponse responseGet;
//			responseGet = client.execute(get);
//			HttpEntity responseEntity = responseGet.getEntity();
//			String response = EntityUtils.toString(responseEntity);
//			getPhotoURL(response);
//			Log.d("TestURL", response);						
//			images = new FetchBitmaps().execute(fbPicURLs).get();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		
//		String text_from_search = text_search.getText().toString();
//		Toast.makeText(SearchActivity.this,text_from_search,Toast.LENGTH_SHORT).show();
		
		
		
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
