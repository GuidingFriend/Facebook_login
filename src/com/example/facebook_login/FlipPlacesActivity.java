package com.example.facebook_login;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.example.facebook_login.model.Movie;

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

public class FlipPlacesActivity extends Activity{
	
	public int currentimageindex=0;

  ImageView imageView;
  ImageButton openmap ;
  double lat, longi;
  private ViewFlipper viewFlipper;
  private float lastX;
  
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
        setContentView(R.layout.places_listview);
        dispData = (TextView)findViewById(R.id.textView1);
//        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        
        Bundle bundle = getIntent().getExtras();
        Gallery ga = (Gallery)findViewById(R.id.Gallery01);
        imageView = (ImageView)findViewById(R.id.ImageView01);
        titleView = (TextView)findViewById(R.id.textView2);
        category = (TextView)findViewById(R.id.textView3);
//        ga.setAdapter(new ImageAdapter(this));
               
        
//        Intent i = getIntent();
//        Movie movie = (Movie)getIntent().getExtras().getParcelable("latLong");
//        Log.d("Movie", movie.toString());
        
        
        
//        ga.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                    long arg3) {
//                Toast.makeText(getBaseContext(), 
//                        "You have selected picture " + (arg2+1) + " of Antartica", 
//                        Toast.LENGTH_SHORT).show();
//                imageView.setImageResource(pics[arg2]);
////                imageView.setImageBitmap(pics[arg2]);
//            }
//           
//        });
//        bundle.get("Title");
        String title = bundle.getString("Title");
        String thumbnailurl = bundle.getString("URL");
        String year = bundle.getString("Category");
        String rating = bundle.getString("Checkins");
        String genre = bundle.getString("Description");
        String images = bundle.getString("Images");
        
        final List<String> splitter = Arrays.asList(images.split(","));
//        Log.d("Splitter", splitter.toString());
        for(int i=0;i<splitter.size();i++){
        	
        	Log.d("Splitter", splitter.get(i).toString());
        }
        Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() { 
        URL newurl=null;
		try {
			newurl = new URL(splitter.get(0).toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Bitmap mIcon_val=null;
		try {
			mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        imageView.setImageBitmap(mIcon_val);
		    }
        });
        thread.start();
        
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

  
   
//    public boolean onTouchEvent(MotionEvent touchevent) 
//    {
//                 switch (touchevent.getAction())
//                 {
//                        // when user first touches the screen to swap
//                         case MotionEvent.ACTION_DOWN: 
//                         {
//                             lastX = touchevent.getX();
//                             break;
//                        }
//                         case MotionEvent.ACTION_UP: 
//                         {
//                             float currentX = touchevent.getX();
//                             
//                             // if left to right swipe on screen
//                             if (lastX < currentX) 
//                             {
//                                  // If no more View/Child to flip
////                                 if (viewFlipper.getDisplayedChild() == 0)
////                                     break;
//                                 
//                                 // set the required Animation type to ViewFlipper
//                                 // The Next screen will come in form Left and current Screen will go OUT from Right 
//                                 viewFlipper.setInAnimation(this, R.anim.in_from_left);
//                                 viewFlipper.setOutAnimation(this, R.anim.out_to_right);
//                                 // Show the next Screen
//                                 viewFlipper.showNext();
//                             }
//                             
//                             // if right to left swipe on screen
//                             if (lastX > currentX)
//                             {
////                                 if (viewFlipper.getDisplayedChild() == 1)
////                                     break;
//                                 // set the required Animation type to ViewFlipper
//                                 // The Next screen will come in form Right and current Screen will go OUT from Left 
//                                 viewFlipper.setInAnimation(this, R.anim.in_from_right);
//                                 viewFlipper.setOutAnimation(this, R.anim.out_to_left);
//                                 // Show The Previous Screen
//                                 viewFlipper.showPrevious();
//                             }
//                             break;
//                         }
//                 }
//                 return false;
//    }

    
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
//            iv.setImageResource((Integer)imagepics.get(arg0));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(new Gallery.LayoutParams(250,120));
            iv.setBackgroundResource(imageBackground);
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
    
//        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
//        if (Session.getActiveSession() != null || Session.getActiveSession().isOpened()){
//                    Intent i = new Intent(FlipPlacesActivity.this,SearchActivity.class);
//                    startActivity(i);
//                }
//       
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



}
