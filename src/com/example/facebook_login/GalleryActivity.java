package com.example.facebook_login;

import java.io.IOException;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;







import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.example.facebook_login.facebook_profile.User;
import com.example.facebook_login.facebook_profile.pictureAsync;
import com.example.facebook_login.facebook_profile.profileAsync;
import com.example.facebook_login.facebook_profile.pictureAsync.onImageDownloaded;
import com.example.facebook_login.facebook_profile.profileAsync.profileImplement;
import com.example.facebook_login.model.Movie;
import com.facebook.Session;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewFlipper;

public class GalleryActivity extends Activity implements profileImplement, onImageDownloaded{
	
	public int currentimageindex=0;

  ImageView imageView,i;
  ImageButton openmap ;
  double lat, longi;
  private ViewFlipper viewFlipper;
  private float lastX;
  Bitmap bitmap;
  
  List<String> splitter;
  
  private TextView dispData, titleView,category;
	private int pics[]={R.drawable.antartica1, R.drawable.antartica2, R.drawable.antartica3,
	        R.drawable.antartica4, R.drawable.antartica5,R.drawable.antartica6, R.drawable.antartica7,
	        R.drawable.antartica8, R.drawable.antartica9
	        };
	
	
//  ArrayList<Bitmap> imagepics = new ArrayList<Bitmap>();
//  ArrayList<ImageView> imagearray = new ArrayList<ImageView>();
//	ViewFlipper viewFlipper;
//	ImageSwitcher iSwitcher;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.places_listview);
        setContentView(R.layout.places_listview);
        
        String url = "http://images.nymag.com/travel/visitorsguide/brooklynattractions_560.jpg";
		Log.d("URL",url);
		pictureAsync picTask = new pictureAsync(this);
		profileAsync task = new profileAsync(this);
		picTask.execute(url + "/picture?type=large");
//		picTask.delegate = this;
//		task.delegate = this;
		task.execute(url);
		
        dispData = (TextView)findViewById(R.id.textView1);
//        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        
        Bundle bundle = getIntent().getExtras();
        Gallery ga = (Gallery)findViewById(R.id.Gallery01);
        imageView = (ImageView)findViewById(R.id.ImageView01);
        titleView = (TextView)findViewById(R.id.textView2);
        category = (TextView)findViewById(R.id.textView3);
        ga.setAdapter(new ImageAdapter(this));
               
        
//        Intent i = getIntent();
//        Movie movie = (Movie)getIntent().getExtras().getParcelable("latLong");
//        Log.d("Movie", movie.toString());
        
//        UrlImageViewHelper.setUrlDrawable(imageView, "http://example.com/image.png");
        
        ga.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                Toast.makeText(getBaseContext(), 
                        "You have selected picture " + (arg2+1) + " of Antartica", 
                        Toast.LENGTH_SHORT).show();
                imageView.setImageResource(pics[arg2]);
//                imageView.setImageBitmap(pics[arg2]);
            }
           
        });
//        bundle.get("Title");
        String title = bundle.getString("Title");
        String thumbnailurl = bundle.getString("URL");
        String year = bundle.getString("Category");
        String rating = bundle.getString("Checkins");
        String genre = bundle.getString("Description");
        String images = bundle.getString("Images");
        
        splitter = Arrays.asList(images.split(","));
//        Log.d("Splitter", splitter.toString());
        for(int i=0;i<splitter.size();i++){
        	
        	Log.d("Splitter", splitter.get(i).toString());
        }
        
//        try {
//        	  i = (ImageView)findViewById(R.id.image);
//        	  bitmap = BitmapFactory.decodeStream((InputStream)new URL(splitter.get(0).toString()).getContent());
//        	  i.setImageBitmap(bitmap); 
//        	} catch (MalformedURLException e) {
//        	  e.printStackTrace();
//        	} catch (IOException e) {
//        	  e.printStackTrace();
//        	}
        
//        Drawable d = LoadImageFromWebOperations(splitter.get(0).toString());
//        imageView.setImageDrawable(d);
//        
//        for(int i=0;i<splitter.size();i++){
//        	String imageurl = splitter.get(i);
//        	URL url = new URL(imageurl);
//        	HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
//
//        	InputStream is = connection.getInputStream();
//        	Bitmap img = BitmapFactory.decodeStream(is);  
//        	imagearray.add(img);
//        	imageView.setImageBitmap(img );
//        	
//        	
//        }
        
//        for(int i=0;i<splitter.size();i++){
//        	viewFlipper.addView(imagearray.get(i));
//        }
        
        lat=bundle.getDouble("Latitude");
        longi=bundle.getDouble("Longitude");
        titleView.setText(title);
        category.setText(year);
        
        
//        dispData.setText("Category"+year+"\n"+"Description"+genre+"\n"+"Checkins"+rating+"\n"+"Latitude"+lat+"Longitude"+longi);
//        dispData.setText(genre);
        dispData.setMovementMethod(new ScrollingMovementMethod());
        dispData.setText(genre);
        final String uri = String.format("geo:%f,%f?z=%d&q=%f,%f (%s)",lat, longi, 15, lat, longi, title);
        openmap = (ImageButton) findViewById(R.id.openMap);
        openmap.setOnClickListener(new View.OnClickListener()
        {
        public void onClick(View arg0){
        Intent i = new
        Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(i);
        }
        });
        
//        if(bundle.getString("Title") != null){
//        	dispData.setText("Title : "+bundle.toString());
//        }
        
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public class ImageAdapter extends BaseAdapter {

        private Context ctx;
        int imageBackground;
       
        public ImageAdapter(Context c) {
            ctx = c;
            TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
            imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
            ta.recycle();
        }

        @Override
        public int getCount() {
           
            return pics.length;
        }

        @Override
        public Object getItem(int arg0) {
           
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
           
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ImageView iv = new ImageView(ctx);
            
            URL url;
			
//				url = new URL(splitter.get(0).toString());
				
//            List<String> imageNames = new ArrayList<String>();
//            imageNames.add("");
            for(int i=0;i<splitter.size();i++){
            	try {
            	url = new URL(splitter.get(i).toString());
            	URLConnection conn = url.openConnection();
                conn.connect();
                Bitmap bmp = BitmapFactory.decodeStream(conn.getInputStream());
                iv.setImageBitmap(bmp);
                iv.setLayoutParams(new Gallery.LayoutParams(150,150));
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setBackgroundResource(imageBackground);
                
//            iv.setImageResource((Integer)imagepics.get(arg0));
//            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            iv.setLayoutParams(new Gallery.LayoutParams(150,120));
//            iv.setBackgroundResource(imageBackground);
            
            
            }catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		}
            return iv;
        }

    }
    
    public void onClick(View v) {
        
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
      }
    
    @Override
    public void onResume() {
        super.onResume();
    
        
    }

    @Override
    public void onPause() {
        super.onPause();
    
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        if (Session.getActiveSession() != null || Session.getActiveSession().isOpened()){
                    Intent i = new Intent(GalleryActivity.this,GalleryActivity.class);
                    startActivity(i);
                }
       
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
      
    }

    public void openOnMap(){
    	openmap = (ImageButton)findViewById(R.id.openMap);
    	String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=%d&q=%f,%f (%s)",40.6944, -73.9865, 10, 40.6944, 73.9865, "NYU Poly");
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    	getBaseContext().startActivity(intent);
    }

	@Override
	public void setImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		if(bitmap != null){
			ImageView image = (ImageView) findViewById(R.id.ImageView01);
			image.setImageBitmap(bitmap);
		}
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		if(user == null){
			Toast t = Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT);
			t.show();
		}
		else{
			Toast t = Toast.makeText(this, "Valid username", Toast.LENGTH_SHORT);
			t.show();
//			TextView name = (TextView) findViewById(R.id.name);
////			TextView id = (TextView) findViewById(R.id.id);
////			TextView gender = (TextView) findViewById(R.id.gender);
//			TextView link = (TextView) findViewById(R.id.link);
//			name.setText(user.name);
//			link.setText(user.link);
		}
	}



}
