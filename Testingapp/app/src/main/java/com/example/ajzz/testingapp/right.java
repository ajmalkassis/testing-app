package com.example.ajzz.testingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.ajzz.testingapp.splash.url;

public class right extends Fragment implements  View.OnClickListener {
    ListView lv;
    String msg="logging_test";
    FloatingActionButton fab;
    String contentType="MEETINGS";
    ArrayList<String> items=new ArrayList<String>();
    public static final String MyPreferences="testPref";
    SharedPreferences sharedPreferences;
    ArrayAdapter<String> adapter;
    JSONObject test_Json;




    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        Log.d(msg, String.valueOf(R.layout.activity_right));
        sharedPreferences = getActivity().getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        return inflater.inflate(R.layout.activity_right, parent, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        Log.d(msg, "created view  "+view);
        lv=(ListView) view.findViewById(R.id.contents_pane);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        /*Boolean isAdmin=sharedPreferences.getBoolean("isAdmin",false);
        Log.d(msg,"isAdmin : "+isAdmin.toString()+" and contentype :"+contentType);
        if(isAdmin && TextUtils.equals(contentType,"MEETINGS"))
        {
            fab.setVisibility(View.VISIBLE);
        }
        else
        {
            fab.setVisibility(View.INVISIBLE);
        }*/
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);
        items.add("a");
        items.add("b");
        items.add("C");
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
        lv.setAdapter(adapter);
        Log.d(msg, "content type "+contentType);
        Log.d(msg, "list item values "+items);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast t1 = Toast.makeText(getActivity(), " clicked in right list +", Toast.LENGTH_SHORT);
                t1.show();
                String selected = items.get(position);
                Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
                Log.d(msg, " selected in right pane"+selected);
                if(TextUtils.equals(contentType,"MEETINGS"))
                {
                    test_Json = new JSONObject();
                    String token=sharedPreferences.getString("token",null);
                    try {
                        test_Json.put("token", token);
                        test_Json.put("value",selected);
                        Log.d(msg, "test jsonvalue" + test_Json.toString());
                        ServerRequest sr = new ServerRequest();
                        Log.d(msg, "server request object " + sr.toString());
                        JSONObject json = sr.getJSON(url+"/api/viewMeet", test_Json);//login in the place of register
                        Log.d(msg, "response is " + json.toString());
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    Intent meeting=new Intent(getActivity(),Right_meetings.class);
                    Log.d(msg," new Intent"+meeting);
                    startActivity(meeting);
                }
                else if(TextUtils.equals(contentType,"MEMBERS"))
                {
                    test_Json = new JSONObject();
                    String token=sharedPreferences.getString("token",null);
                    try {
                        test_Json.put("token", token);
                        test_Json.put("value",selected);
                        Log.d(msg, "test jsonvalue" + test_Json.toString());
                        ServerRequest sr = new ServerRequest();
                        Log.d(msg, "server request object " + sr.toString());
                        JSONObject json = sr.getJSON(url+"/api/viewMem", test_Json);//login in the place of register
                        Log.d(msg, "response is " + json.toString());
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    Intent meeting=new Intent(getActivity(),Right_members.class);
                    Log.d(msg," new Intent"+meeting);
                    startActivity(meeting);
                }
                else if(TextUtils.equals(contentType,"REGISTRATION"))
                {
                    test_Json = new JSONObject();
                    String token=sharedPreferences.getString("token",null);
                    try {
                        test_Json.put("token", token);
                        test_Json.put("value",selected);
                        Log.d(msg, "test jsonvalue" + test_Json.toString());
                        ServerRequest sr = new ServerRequest();
                        Log.d(msg, "server request object " + sr.toString());
                        JSONObject json = sr.getJSON(url+"/api/viewReg", test_Json);//login in the place of register
                        Log.d(msg, "response is " + json.toString());
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    Intent meeting=new Intent(getActivity(),Right_pending_req.class);
                    Log.d(msg," new Intent"+meeting);
                    startActivity(meeting);
                }

                else
                {
                    Log.d(msg," new Intent"+contentType);

                }



            }
        });

        fab.setOnClickListener(this);






    }
    public void setRecievedOption(String option)
    {



        contentType=option;

        items.clear();
        Log.d(msg,"adapter value"+lv+""+adapter);
        Boolean isAdmin=sharedPreferences.getBoolean("isAdmin",false);
        Log.d(msg,"isAdmin : "+isAdmin.toString()+" and contentype :"+contentType);
        if(isAdmin && TextUtils.equals(contentType,"MEETINGS"))
        {
            fab.setVisibility(View.VISIBLE);
        }
        else
        {
            fab.setVisibility(View.INVISIBLE);
        }
        if(TextUtils.isEmpty(contentType))
        {
            System.out.println(contentType);
            Log.d(msg, "content type "+contentType);

        }
        else if(TextUtils.equals(contentType,"MEETINGS"))
        {
            test_Json = new JSONObject();
            String token=sharedPreferences.getString("token",null);
            try {
                test_Json.put("token", token);
                Log.d(msg, "test jsonvalue" + test_Json.toString());
                ServerRequest sr = new ServerRequest();
                Log.d(msg, "server request object " + sr.toString());
                JSONObject json = sr.getJSON(url+"/viewMeeting", test_Json);//login in the place of register
                Log.d(msg, "response is " + json.toString());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }



            items.add("a");
            items.add("b");
            items.add("C");
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
            lv.setAdapter(adapter);
            Log.d(msg, "content type "+contentType);
            Log.d(msg, "list item values "+items);
        }
        else if(TextUtils.equals(contentType,"MEMBERS"))
        {
            test_Json = new JSONObject();
            String token=sharedPreferences.getString("token",null);
            try {
                test_Json.put("token", token);
                Log.d(msg, "test jsonvalue" + test_Json.toString());
                ServerRequest sr = new ServerRequest();
                Log.d(msg, "server request object " + sr.toString());
                JSONObject json = sr.getJSON(url+"/viewMembers", test_Json);//login in the place of register
                Log.d(msg, "response is " + json.toString());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            items.add("text");
            items.add("not ");
            items.add("here");
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
            lv.setAdapter(adapter);
            Log.d(msg, "content type "+contentType);
            Log.d(msg, "list item values "+items);
        }
        else if(TextUtils.equals(contentType,"REGISTRATION"))
        {
            test_Json = new JSONObject();
            String token=sharedPreferences.getString("token",null);
            try {
                test_Json.put("token", token);
                Log.d(msg, "test jsonvalue" + test_Json.toString());
                ServerRequest sr = new ServerRequest();
                Log.d(msg, "server request object " + sr.toString());
                JSONObject json = sr.getJSON(url+"/viewRegistered", test_Json);//login in the place of register
                Log.d(msg, "response is " + json.toString());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            items.add("text");
            items.add("not ");
            items.add("here");
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
            lv.setAdapter(adapter);
            Log.d(msg, "content type "+contentType);
            Log.d(msg, "list item values "+items);
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.fab:
                Toast t1 = Toast.makeText(getActivity(), " clicked in right fab in right +", Toast.LENGTH_SHORT);
                Log.d(msg," fab button cliked");
                t1.show();
                Intent meeting=new Intent(getActivity(),AddMeetings.class);
                Log.d(msg," new Intent"+meeting);
                startActivity(meeting);
                break;
            default:
                break;

        }


    }
}
