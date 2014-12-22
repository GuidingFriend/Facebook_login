package com.example.facebook_login;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.facebook_login.adater.CustomListAdapter;
import com.example.facebook_login.app.AppController;
import com.example.facebook_login.model.Movie;
//import com.example.facebook_login.JsonArrayRequest;

public class PlacesListViewActivity extends Activity implements OnItemClickListener{
	private static final String TAG = PlacesListViewActivity.class.getSimpleName();
	static String location="";
	static String filename ="";
	static String fbaccesstoken="";
	// Movies json url
//	private static final String url = "http://api.androidhive.info/json/movies.json";
	private static String url = "http://ec2-54-164-195-102.compute-1.amazonaws.com/static/"+filename+".json";
	private ProgressDialog pDialog;
	private List<Movie> movieList = new ArrayList<Movie>();
	private ListView listView;
	private CustomListAdapter adapter;
	private Movie getItem;
	Movie movie = new Movie();
	
//	int gallery_grid_Images[]={R.drawable.afghanistan,R.drawable.bangladesh,R.drawable.china,
//	        R.drawable.india,R.drawable.japan,R.drawable.nepal,R.drawable.srilanka,
//	        R.drawable.skorea
//	        };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places_list_view);
		
		pDialog = new ProgressDialog(this);
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...Please Wait");
		pDialog.show();
		
		Bundle bundle = getIntent().getExtras();
		filename = bundle.getString("Filename");
//		fbaccesstoken = bundle.getString("AccessToken");
//		location = bundle.getString("Location");
		Log.d("AccessToken",fbaccesstoken);
		Log.d("Location",location);
		
		url = "http://ec2-54-164-195-102.compute-1.amazonaws.com/static/"+filename+".json";
		Log.d("URL",url);
		listView = (ListView) findViewById(R.id.list);
		adapter = new CustomListAdapter(this, movieList);
		
		callJson();
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	
	        	String getData = listView.getItemAtPosition(position).toString();
//	        	Toast.makeText(PlacesListViewActivity.this,getData,Toast.LENGTH_SHORT).show();
	        	
	        	
	        	String thumbnailurl = movieList.get(position).getThumbnailUrl();
	        	String title = movieList.get(position).getTitle();
	        	String category = movieList.get(position).getCategory();
	        	String description = movieList.get(position).getDescription();
	        	int checkins = movieList.get(position).getCheckins();
	        	double latitude = movieList.get(position).getLatitude();
	        	double longitude = movieList.get(position).getLongitude();
	        	String images = movieList.get(position).getImages();
	    		
	        	Intent intent = new Intent(PlacesListViewActivity.this, FlipPlacesActivity.class);
	    		intent.putExtra("URL",thumbnailurl);
	    		intent.putExtra("Title",title);
	    		intent.putExtra("Category",category);
	    		intent.putExtra("Description",description);
	    		intent.putExtra("Images",images);
	    		intent.putExtra("Checkins",String.valueOf(checkins));
//	    		intent.putExtra("Latitude",String.valueOf(latitude));
	    		intent.putExtra("Latitude",latitude);
//	    		intent.putExtra("Longitude",String.valueOf(longitude));
	    		intent.putExtra("Longitude",(longitude));
	    		startActivity(intent);
	    		pDialog = new ProgressDialog(PlacesListViewActivity.this);
				// Showing progress dialog before making http request
				pDialog.setMessage("Loading...Please Wait");
				pDialog.show();
	        }
	       
		});
		
//		pDialog = new ProgressDialog(this);
//		// Showing progress dialog before making http request
//		pDialog.setMessage("Loading...Please Wait");
//		pDialog.show();
		 
				
		

		// changing action bar color
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1b1b1b")));
		
	
		
	}
	
	public void callJson(){
		// Creating volley request obj
				JsonArrayRequest movieReq = new JsonArrayRequest(url,
						new Response.Listener<JSONArray>() {
							
							@Override
							public void onResponse(JSONArray response) {
//								Log.d(TAG, response.toString());
								hidePDialog();

								// Parsing json
								for (int i = 0; i < response.length(); i++) {
									try {

										JSONObject obj = response.getJSONObject(i);
										Movie movie = new Movie();
										if(obj.getString("title").equals("Kota Batam")){
											
										
										}
										else{
											if(obj.getString("description").equals("")){
												movie.setDescription("");
											}
											else{
												movie.setDescription(obj.getString("description"));
											}
										movie.setThumbnailUrl(obj.getString("image"));
										movie.setTitle(obj.getString("title"));
										movie.setCategory(obj.getString("category"));
										
										movie.setCheckins(obj.getInt("checkins"));
										movie.setLatitude(((Number) obj.get("latitude")).doubleValue());
										movie.setLongitude(((Number) obj.get("longitude")).doubleValue());
										movie.setImages(obj.getString("images"));
										// Genre is json array
//										JSONArray genreArry = obj.getJSONArray("genre");
//										ArrayList<String> genre = new ArrayList<String>();
//										for (int j = 0; j < genreArry.length(); j++) {
//											genre.add((String) genreArry.get(j));
//										}
//										movie.setGenre(genre);

										// adding movie to movies array
										movieList.add(movie);
										}	
									} catch (JSONException e) {
										e.printStackTrace();
									}
									

								}
													
								
								// notifying list adapter about data changes
								// so that it renders the list view with updated data
								adapter.notifyDataSetChanged();
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								VolleyLog.d(TAG, "Error: " + error.getMessage());
								hidePDialog();

							}
						});

				// Adding request to request queue
				AppController.getInstance().addToRequestQueue(movieReq);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		hidePDialog();
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.places_list, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, FlipPlacesActivity.class);
		startActivity(intent);
	}

}

