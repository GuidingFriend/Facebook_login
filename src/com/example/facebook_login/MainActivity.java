package com.example.facebook_login;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;



public class MainActivity extends FragmentActivity implements Serializable{

	public static String userid;
	private LoginButton loginBtn;
    private Button twitter_login_button;
    private Button instagram_login_button;
    private TextView access_token;
    private TextView userName;

    private UiLifecycleHelper uiHelper;
    
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

    Button enter_app_button;
    
    
    private static String APP_ID = "748523695187267";
    private ProgressDialog progress;
    private Facebook facebook;
    private AsyncFacebookRunner mAsyncRunner;
    private String user_access;
    
    final int totalProgressTime = 100;

    private Session session;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
            
        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);
        facebook = new Facebook(APP_ID);
        mAsyncRunner = new AsyncFacebookRunner(facebook);
//        access_token = (TextView) findViewById(R.id.access_token);
        userName = (TextView) findViewById(R.id.user_name);
        enter_app_button = (Button) findViewById(R.id.enter_app_button);

        
//        user_access = facebook.getAuthResponse();
//        user_access = "CAACEdEose0cBAIiZAtSy2cJeYpZCZCgbbNZBJzikCNDYSIlv7mSjpO3E7LJhOH0IvmnaGuwoM8PmhAsaGZAjAckK439BjZCuhXFukequiVF0ELQOUFTF3J3J3t5Hb3D9uCVSiRE4ZA2TMxZAF8OtTfIb4BGrFngVuDRhPbZCmcZBbQKEhlTDovL7mRAEqcCsWSBeuIO4ZAcD2gjsK8UsXJHP1q4";
        System.out.println("Access Token : " + user_access);
//        access_token.toString();
//        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//        intent.putExtra("Access Token", user_access);
        
        loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
        loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    userName.setText("              " + user.getName());
                    userid = user.getId();
                    Log.d("URLMain",userid);
                } else {
                    userName.setText("You are not logged");
                    enter_app_button.setVisibility(View.GONE);
                }
//                user.
            }
        });
        

   
    }

    
    
    
    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                Exception exception) {
            if (state.isOpened()) {
   
                Log.d("FacebookSampleActivity", "Facebook session opened");
            } else if (state.isClosed()) {
   
                Log.d("FacebookSampleActivity", "Facebook session closed");
            }
        }
    };
    
    private void onSessionStateChange(final Session session, final SessionState state, final Exception exception) {
        if (state.isOpened()) {
            Log.i("TAG", "Logged in...");
        } else if (state.isClosed()) {
            Log.i("TAG", "Logged out...");
        }
    }

    public boolean checkPermissions() {
        Session s = Session.getActiveSession();
        if (s != null) {
            return s.getPermissions().contains("publish_actions");
        } else
            return false;
    }

    public void requestPermissions() {
        Session s = Session.getActiveSession();
        if (s != null)
            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
                    this, PERMISSIONS));
    }

    @Override
    public void onResume() {
    	
        super.onResume();
        uiHelper.onResume();
        enter_app_button = (Button) findViewById(R.id.enter_app_button);
        //Session session = Session.getActiveSession();
        if (Session.getActiveSession() != null || Session.getActiveSession().isOpened()){
        	enter_app_button.setVisibility(View.VISIBLE);
        	enter_app_button.setOnClickListener(new View.OnClickListener()
            {
            public void onClick(View arg0){
            Intent i = new Intent(MainActivity.this,SearchActivity.class);
            startActivity(i);
            }
//        	enter_app_button.setonClickListener
//                    Intent i = new Intent(MainActivity.this,SearchActivity.class);
//                    startActivity(i);
               });
        }
        else{
        	
    		enter_app_button.setVisibility(View.GONE);
        }
       
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
        enter_app_button = (Button) findViewById(R.id.enter_app_button);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        if (Session.getActiveSession() != null || Session.getActiveSession().isOpened()){
        	enter_app_button.setVisibility(View.VISIBLE);
        	enter_app_button.setOnClickListener(new View.OnClickListener()
            {
            public void onClick(View arg0){
            Intent i = new Intent(MainActivity.this,SearchActivity.class);
            startActivity(i);
            }
//        	enter_app_button.setonClickListener
//                    Intent i = new Intent(MainActivity.this,SearchActivity.class);
//                    startActivity(i);
               });
        }
        else{
        	
    		enter_app_button.setVisibility(View.GONE);
        }
       
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }

}
