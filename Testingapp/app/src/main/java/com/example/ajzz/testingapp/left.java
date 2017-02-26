package com.example.ajzz.testingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.BoolRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class left extends Fragment {
    ListView lv;
    String msg="logging_test";
    ArrayList<String> items=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;
    public static final String MyPreferences = "testPref";

    public SendContentOption sendcontentoption=null;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = (View) inflater.inflate(R.layout.activity_left, parent,false);
        lv=(ListView) view.findViewById(R.id.bookmarks_pane);
        sharedPreferences = getActivity().getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);

        items.add("MEETINGS");
        items.add("MEMBERS");
        items.add("LOG OUT");
        //if isAdmin, add pending requests, also on right pane , option for FAB to be active and also only in Meetings tab
        Boolean isAdmin=sharedPreferences.getBoolean("isAdmin",false);
        if(isAdmin)
        {
            items.add("REGISTRATION");
        }


        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = items.get(position);
                if(TextUtils.equals(selected,"LOG OUT"))
                {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    //Storing Data using SharedPreferences
                    edit.putString("token", null);
                    //edit.putString("grav", grav);
                    edit.commit();
                    Intent backtologinpage =new Intent(getActivity(),MainActivity.class);
                    startActivity(backtologinpage);
                }
                else
                {
                    sendData(selected);

                }

            }
        });
    return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);



   }
    public interface SendContentOption
    {
        public void sendoption(String option);
    }



    @Override
    public void onAttachFragment(Fragment childFragment) {
        System.out.println(childFragment);
        Log.d(msg," fragment in onattachfra "+childFragment);
        if(childFragment instanceof SendContentOption)
        {
            sendcontentoption=(SendContentOption) childFragment;
            Log.d(msg,"send content option object"+sendcontentoption);
            System.out.println(sendcontentoption);

        }
        else
        {
            throw new ClassCastException();
        }
        super.onAttachFragment(childFragment);

    }


    @Override
    public void onAttach(Context context) {

        Log.d(msg," fragment "+context);
        if(context instanceof SendContentOption)
        {
            sendcontentoption=(SendContentOption) context;
            Log.d(msg,"send content option object"+sendcontentoption);
            System.out.println(sendcontentoption);
        }
        else
        {
            throw new ClassCastException();
        }super.onAttach(context);
    }

    public void sendData(String msg)
    {
        System.out.println(msg);
        Log.d(msg,"message is"+msg);
        Log.d(msg,"content option object "+sendcontentoption);
        sendcontentoption.sendoption(msg);
    }
}
