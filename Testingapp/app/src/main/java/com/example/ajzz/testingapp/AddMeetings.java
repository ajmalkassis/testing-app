package com.example.ajzz.testingapp;

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

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.ajzz.testingapp.splash.url;

public class AddMeetings extends AppCompatActivity implements View.OnClickListener {
    Button submitButton;
    String msg = "logging_test";
    EditText meetingName, venueName, groupName,conductedOn;
    String conducted_On;
    String meeting_Name,venue_Name, group_Name;
    SharedPreferences sharedPreferences;
    Toast t1;
    Calendar cal;
    JSONObject test_Json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meetings);
        submitButton = (Button) findViewById(R.id.submit_Meeting);
        meetingName = (EditText) findViewById(R.id.meetingName);
        venueName=(EditText) findViewById(R.id.venue);
        groupName = (EditText) findViewById(R.id.groupInvited);
        conductedOn = (EditText) findViewById(R.id.conducted_on);
        meetingName.requestFocus();
        Log.d(msg, " button " + submitButton);
        submitButton.setOnClickListener(AddMeetings.this);
    }

    @Override
    public void onClick(View v) {

        meeting_Name=meetingName.getText().toString();
        venue_Name=venueName.getText().toString();
        group_Name=groupName.getText().toString();
        Log.d(msg,conductedOn.getText().toString());
        conducted_On=conductedOn.getText().toString();

        //conducted_On=conductedOn.getText().toString();
        if(TextUtils.isEmpty(meeting_Name))
        {
            t1 = Toast.makeText(getApplicationContext(), " Enter meeting Name", Toast.LENGTH_SHORT);
            meetingName.requestFocus();
            t1.show();
        }
        else if(TextUtils.isEmpty(venue_Name))
        {
            t1 = Toast.makeText(getApplicationContext(), " Enter venue name", Toast.LENGTH_SHORT);
            venueName.requestFocus();
            t1.show();
        }
        else if(TextUtils.isEmpty(group_Name))
        {
            t1 = Toast.makeText(getApplicationContext(), " Select any group", Toast.LENGTH_SHORT);
            groupName.requestFocus();
            t1.show();
        }
        else if(TextUtils.isEmpty(conducted_On))
        {
            t1 = Toast.makeText(getApplicationContext(), " Enter conducted on", Toast.LENGTH_SHORT);
            conductedOn.requestFocus();
            t1.show();
        }
        else
        {
            test_Json = new JSONObject();
            try {
                test_Json.put("meetingName", meeting_Name);
                test_Json.put("venueName", venue_Name);
                test_Json.put("groupName",group_Name);
                test_Json.put("conductedOn",conducted_On);

                Log.d(msg, "test jsonvalue" + test_Json.toString());
                ServerRequest sr = new ServerRequest();
                Log.d(msg, "server request object " + sr.toString());
                JSONObject json = sr.getJSON(url+"/addMeeting", test_Json);//login in the place of register
                //Log.d(msg, "response is " + json.toString());
                if (json != null)
                {

                    try {
                        String jsonstr = json.getString("response");
                        if (json.getBoolean("res")) {
                            String token = json.getString("token");
                            String grav = json.getString("grav");
                            Boolean isAdmin=json.getBoolean("isAdmin");
                            Log.d(msg,isAdmin.toString());
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            //Storing Data using SharedPreferences
                            edit.putString("token", token);
                            edit.putString("grav", grav);
                            edit.putBoolean("isAdmin",isAdmin);
                            edit.commit();
                            Intent profactivity = new Intent(AddMeetings.this,Right_meetings.class);
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



    }
}
