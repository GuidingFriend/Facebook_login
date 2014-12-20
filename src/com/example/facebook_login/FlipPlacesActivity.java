package com.example.facebook_login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class FlipPlacesActivity extends Activity{
	
	public int currentimageindex=0;

  ImageView imageView;
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
        

        if(bundle.getString("GetData") != null){
        	dispData.setText("Data : "+bundle.toString());
        }
        
//        final Handler mHandler = new Handler();
//
//        // Create runnable for posting
//        final Runnable mUpdateResults = new Runnable() {
//            public void run() {
//                
//                AnimateandSlideShow();
//                
//            }
//        };
//        
//        int delay = 100; // delay for 1 sec.
//
//        int period = 1500; // repeat every 4 sec.
//
//        Timer timer = new Timer();
//
//        timer.scheduleAtFixedRate(new TimerTask() {
//
//        public void run() {
//
//             mHandler.post(mUpdateResults);
//
//        }
//
//        }, delay, period);
        
        
               
        
        
        
        
//        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
//        setFlipperImage(gallery_grid_Images);
//        iSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);
//        iSwitcher.setFactory(this);
//        iSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
//                    android.R.anim.fade_in));
//        iSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
//                    android.R.anim.fade_out));   
//        
//   
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




}
