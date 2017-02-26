package com.example.ajzz.testingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.ajzz.testingapp.splash.url;

public class Registration extends  AppCompatActivity implements View.OnClickListener {
    Button submitButton;
    String msg = "logging_test";
    EditText user_name, pass_word, password_re_entry, phone_number;
    String username, password, password_reentry, phonenumber;
    SharedPreferences sharedPreferences;
    Toast t1;
    JSONObject test_Json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        submitButton = (Button) findViewById(R.id.submitButton);
        user_name = (EditText) findViewById(R.id.nameVal);
        pass_word = (EditText) findViewById(R.id.passwordVal);
        password_re_entry = (EditText) findViewById(R.id.reEnterPassVal);
        phone_number = (EditText) findViewById(R.id.phoneNumber);
        user_name.requestFocus();
        Log.d(msg, " button " + submitButton);
        submitButton.setOnClickListener(Registration.this);
    }

    @Override
    public void onClick(View v)
    {
        username = user_name.getText().toString();
        password = pass_word.getText().toString();
        password_reentry = password_re_entry.getText().toString();
        phonenumber = phone_number.getText().toString();

        if (TextUtils.isEmpty(username))
        {
            t1 = Toast.makeText(getApplicationContext(), " Enter your username", Toast.LENGTH_SHORT);
            user_name.requestFocus();
            t1.show();

        }
        else if (TextUtils.isEmpty(password))
        {
            t1 = Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT);
            pass_word.requestFocus();
            t1.show();

        }
        else if (TextUtils.isEmpty(password_reentry))
        {
            t1 = Toast.makeText(getApplicationContext(), "Re Enter your password", Toast.LENGTH_SHORT);
            password_re_entry.requestFocus();
            t1.show();
        }
        else if (TextUtils.isEmpty(phonenumber))
        {
            t1 = Toast.makeText(getApplicationContext(), "Enter your phone number", Toast.LENGTH_SHORT);
            phone_number.requestFocus();
            t1.show();
        }
        else if (!(TextUtils.equals(password, password_reentry)))
        {
            t1 = Toast.makeText(getApplicationContext(), "password mismatch", Toast.LENGTH_SHORT);
            pass_word.requestFocus();
            t1.show();
        }
        else
        {

            test_Json = new JSONObject();
            try {
                test_Json.put("user_name", username);
                test_Json.put("password", password);
                test_Json.put("phone_number",phonenumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(msg, "test jsonvalue" + test_Json.toString());
            ServerRequest sr = new ServerRequest();
            Log.d(msg, "server request object " + sr.toString());
            JSONObject json = sr.getJSON(url+"/register", test_Json);//login in the place of register
            Log.d(msg, "response is " + json.toString());
            if (json != null)
            {
                try
                {
                    String jsonstr = json.getString("response");
                    if (json.getBoolean("res"))
                    {
                        String token = json.getString("token");
                        String grav = json.getString("grav");
                        //SharedPreferences.Editor edit = sharedPreferences.edit();
                        //Storing Data using SharedPreferences
                      /*  edit.putString("token", token);
                        edit.putString("grav", grav);
                        edit.commit();*/
                        Intent backtomain = new Intent(Registration.this, MainActivity.class);
                        Log.d(msg, "jsonstr" + jsonstr + " and json is " + json.toString());
                        startActivity(backtomain);
                        finish();
                    }
                    Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
