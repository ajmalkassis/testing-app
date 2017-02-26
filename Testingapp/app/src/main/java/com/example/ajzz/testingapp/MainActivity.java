package com.example.ajzz.testingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.ajzz.testingapp.splash.url;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String msg = "logging_test";
    Button loginbutton, registerButton;
    EditText ed1, ed2;
    int loginstatus = 0;
    ServerRequest sr;
    JSONObject test_Json;

    public static final String MyPreferences = "testPref";
    public static final String name = "nameKey";
    public static final String token = "LOGGED_IN_STATUS";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        Log.d(msg, "The onCreate() event");
        //sr = new ServerRequest();

        loginbutton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById((R.id.registerButton));
        loginbutton.setOnClickListener(MainActivity.this);
        registerButton.setOnClickListener(MainActivity.this);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed1.requestFocus();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, " the onReume() event");
//        if (loginstatus == 1) {
//            Toast t1 = Toast.makeText(getApplicationContext(), " back to the future", Toast.LENGTH_SHORT);
//            t1.show();
//            Intent i = new Intent(getApplicationContext(), Main2Activity.class);
//            startActivity(i);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, " the onStart()event");


    }


    protected void onRestart() {
        super.onRestart();
        Log.d(msg, " the onRestart() event");
    }


    protected void onStop() {
        super.onStop();
        Log.d(msg, "the Stop() event");
    }

    protected void onDestroy()

    {
        super.onDestroy();
        Log.d(msg, " the onDestroy() event");
    }


    protected void onPause() {
        super.onPause();
        Log.d(msg, " the onPause() event");

        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(name,username);
        // editor.putString(token,"off");
        editor.commit();

    }

    @Override
    public void onClick(View v) {
        Log.d(msg, " clicked button " + v.getId());
        switch (v.getId()) {
            case R.id.loginButton:
                String username = ed1.getText().toString();
                String password = ed2.getText().toString();
                Log.d(msg, "username and password are " + username + "  and " + password);
                Toast t1;
                if (TextUtils.isEmpty(username)) {
                    t1 = Toast.makeText(getApplicationContext(), " Enter your username", Toast.LENGTH_SHORT);
                    ed1.requestFocus();
                    t1.show();

                } else if (TextUtils.isEmpty(password)) {
                    t1 = Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT);
                    ed2.requestFocus();
                    t1.show();

                } else {

                    test_Json = new JSONObject();
                    try {
                        test_Json.put("user_name", username);
                        test_Json.put("password", password);

                        Log.d(msg, "test jsonvalue" + test_Json.toString());
                        ServerRequest sr = new ServerRequest();
                        Log.d(msg, "server request object " + sr.toString());
                        JSONObject json = sr.getJSON(url+"/login", test_Json);//login in the place of register
                        //Log.d(msg, "response is " + json.toString());
                        if (json != null)
                        {

                            try {
                                String jsonstr = json.getString("response");
                                if (json.getBoolean("res")) {
                                    String token = json.getString("token");
                                    //String grav = json.getString("grav");
                                    Boolean isAdmin=json.getBoolean("isAdmin");
                                    Log.d(msg,isAdmin.toString());
                                    SharedPreferences.Editor edit = sharedPreferences.edit();
                                    //Storing Data using SharedPreferences
                                    edit.putString("token", token);
                                   // edit.putString("grav", grav);
                                    edit.putBoolean("isAdmin",isAdmin);
                                    edit.commit();
                                    Intent profactivity = new Intent(MainActivity.this,slidingpanellayouttest.class);
                                    Log.d(msg, "jsonstr" + jsonstr + " and json is " + json.toString());
                                    startActivity(profactivity);
                                    finish();
                                }
                                Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
                break;

            case R.id.registerButton:
                Intent i = new Intent(getApplicationContext(), Registration.class);
                startActivity(i);
                break;
//TODO:send credentials to web service and get a validation from server side
            default:
                break;
        }
    }
}

/*if(TextUtils.isEmpty(username))
                {
                    t1= Toast.makeText(getApplicationContext()," Enter your username",Toast.LENGTH_SHORT);
                    ed1.requestFocus();
                    t1.show();

                }
                else if(TextUtils.isEmpty(password))
                {
                    t1= Toast.makeText(getApplicationContext(),"Enter your password",Toast.LENGTH_SHORT);
                    ed2.requestFocus();
                    t1.show();

                }
                else
                {

                    test_Json=new JSONObject();
                    try
                    {
                        test_Json.put("user_name",username);
                        test_Json.put("password",password);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    Log.d(msg,"test jsonvalue"+test_Json.toString());
                    ServerRequest sr=new ServerRequest();
                    Log.d(msg,"server request object "+sr.toString());
                    JSONObject json = sr.getJSON("http://192.168.1.3:8080/register",test_Json);//login in the place of register
                    Log.d(msg,"response is "+json.toString());
                    if(json != null) {

                        try {
                            String jsonstr = json.getString("response");
                            if (json.getBoolean("res")) {
                                String token = json.getString("token");
                                String grav = json.getString("grav");
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                //Storing Data using SharedPreferences
                                edit.putString("token", token);
                                edit.putString("grav", grav);
                                edit.commit();
                                Intent profactivity = new Intent(MainActivity.this, Main2Activity.class);
                                Log.d(msg,"jsonstr"+jsonstr+" and json is "+ json.toString());
                                startActivity(profactivity);
                                finish();
                            }
                            Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
*/

                   /* if(TextUtils.equals(username,"ajmal") &&  TextUtils.equals(password,"ajmal"))
                    {
                        //loginstatus=1;
                        // SharedPreferences.Editor editor=sharedPreferences.edit();
//                editor.putString(name,username);
//                editor.putString(token,"on");
//                editor.commit();
                        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(i);
                    }
                    else
                    {
                        t1=Toast.makeText(getApplicationContext(),"Enter valid credentials",Toast.LENGTH_SHORT);
                        t1.show();
                    }*/