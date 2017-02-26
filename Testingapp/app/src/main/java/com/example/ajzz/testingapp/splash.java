package com.example.ajzz.testingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;
import android.util.Log;


public class splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH=2000;
      public static final String MyPreferences="testPref";
//    public static final String name="nameKey";
//    public static final String token="LOGGED_IN_STATUS";
    String msg = "logging_test";
    public static final String url="http://192.168.43.54:8080";

    SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences=this.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                String status=sharedPreferences.getString("token",null);
                Log.d(msg," shared pref"+status);
                    if(TextUtils.equals(status,null))
                    {
                        Toast t1=Toast.makeText(getApplicationContext(),"prefernce worked",Toast.LENGTH_SHORT);
                        t1.show();
                        Intent mainIntent = new Intent(splash.this, MainActivity.class);
                        splash.this.startActivity(mainIntent);
                        splash.this.finish();
                    }
                    //else if(TextUtils.equals(status,"on"))
                    else
                    {
                        Intent mainIntent=new Intent(splash.this,slidingpanellayouttest.class);
                        splash.this.startActivity(mainIntent);
                        splash.this.finish();
                    }


            }
        },SPLASH_DISPLAY_LENGTH);
    }
}
