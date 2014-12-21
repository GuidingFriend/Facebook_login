package com.example.facebook_login;

import java.util.Locale;

import com.example.facebook_login.model.Movie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class FlipPlacesActivity extends Activity{
	
	public int currentimageindex=0;

  ImageView imageView;
  Button openmap ;
  double lat, longi;
  
  private TextView dispData;
	private int pics[]={R.drawable.antartica1, R.drawable.antartica2, R.drawable.antartica3,
	        R.drawable.antartica4, R.drawable.antartica5,R.drawable.antartica6, R.drawable.antartica7,
	        R.drawable.antartica8, R.drawable.antartica9
	        };
//	ViewFlipper viewFlipper;
//	ImageSwitcher iSwitcher;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_listview);
        dispData = (TextView)findViewById(R.id.textView1);
        
        Bundle bundle = getIntent().getExtras();
        Gallery ga = (Gallery)findViewById(R.id.Gallery01);
        ga.setAdapter(new ImageAdapter(this));
               
        
//        Intent i = getIntent();
//        Movie movie = (Movie)getIntent().getExtras().getParcelable("latLong");
//        Log.d("Movie", movie.toString());
        
        
        imageView = (ImageView)findViewById(R.id.ImageView01);
        ga.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                Toast.makeText(getBaseContext(), 
                        "You have selected picture " + (arg2+1) + " of Antartica", 
                        Toast.LENGTH_SHORT).show();
                imageView.setImageResource(pics[arg2]);
               
            }
           
        });
//        bundle.get("Title");
        String title = bundle.getString("Title");
        String thumbnailurl = bundle.getString("URL");
        String year = bundle.getString("Year");
        String rating = bundle.getString("Rating");
        String latitude = bundle.getString("Latitude");
        String longitude = bundle.getString("Longitude");
        String genre = bundle.getString("Genre");
        
        
        dispData.setText("Title : "+title+"\n"+"Year"+year+"\n"+"Rating"+rating+"\n"+"Genre"+genre+"\n"+"Latitude"+latitude+"Longitude"+longitude);
        lat=bundle.getDouble("Latitude");
        longi=bundle.getDouble("Longitude");
        final String uri = String.format("geo:%f,%f?z=%d&q=%f,%f (%s)",lat, longi, 15, lat, longi, title);
        openmap = (Button) findViewById(R.id.openMap);
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
            iv.setImageResource(pics[arg0]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(new Gallery.LayoutParams(150,120));
            iv.setBackgroundResource(imageBackground);
            return iv;
        }

    }
    
    public void onClick(View v) {
        
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
      }
    
    /**
     * Helper method to start the animation on the splash screen
     */
//    private void AnimateandSlideShow() {
//        
//        
//        slidingimage = (ImageView)findViewById(R.id.ImageView3_Left);
//           slidingimage.setImageResource(gallery_grid_Images[currentimageindex%gallery_grid_Images.length]);
//           
//           currentimageindex++;
//        
//           Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.fade_in);
//          
//        
//          slidingimage.startAnimation(rotateimage);
//          
//              
//        
//    }    
    
    



//    private void setFlipperImage(int res) {
//        Log.i("Set Filpper Called", res+"");
//        ImageView image = new ImageView(getApplicationContext());
//        image.setBackgroundResource(res);
//        viewFlipper.addView(image);
//    }
    
     
    
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
    	openmap = (Button)findViewById(R.id.openMap);
    	String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=%d&q=%f,%f (%s)",40.6944, -73.9865, 10, 40.6944, 73.9865, "NYU Poly");
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    	getBaseContext().startActivity(intent);
    }



}
