package com.example.ajzz.testingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class slidingpanellayouttest extends FragmentActivity implements left.SendContentOption {
SlidingPaneLayout pane;
    String msg = "logging_test";
    Fragment a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(msg," on create in slidingpanelayot");
        Log.d(msg," view " +findViewById(R.id.activity_slidingpanellayouttest));
        Log.d(msg, String.valueOf((R.layout.activity_slidingpanellayouttest)));
        setContentView(R.layout.activity_slidingpanellayouttest);

        pane = (SlidingPaneLayout) findViewById(R.id.activity_slidingpanellayouttest);
        pane = (SlidingPaneLayout) findViewById(R.id.activity_slidingpanellayouttest);
        Log.d(msg," pane " + pane);
        pane.setPanelSlideListener(new PaneListener());

        if (!pane.isSlideable()) {
//            a=getSupportFragmentManager().findFragmentById(R.id.g22);
//            System.out.println(a);
//            Log.d(msg, "pane value:"+a);

            System.out.println(getSupportFragmentManager().findFragmentById(R.id.g11));
            System.out.println(getSupportFragmentManager().findFragmentById(R.id.g22));
            Log.d(msg, "left id"+getSupportFragmentManager().findFragmentById(R.id.g11));
            Log.d(msg, "right id"+getSupportFragmentManager().findFragmentById(R.id.g22));
            Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();
//            getFragmentManager().findFragmentById(R.id.activity_left).setHasOptionsMenu(false);
//            getFragmentManager().findFragmentById(R.id.activity_right).setHasOptionsMenu(true);
        }
    }

    @Override
   public void sendoption(String option) {
        right recFragment = (right) getSupportFragmentManager().findFragmentById(R.id.g11);
        if (null != recFragment && recFragment.isInLayout()) {
            recFragment.setRecievedOption(option);

        }
    }




    private class PaneListener implements SlidingPaneLayout.PanelSlideListener {

        @Override
        public void onPanelClosed(View view) {
            System.out.println("Panel closed "+ view);
            Log.d(msg, "closed panel "+ view);
            System.out.println(getSupportFragmentManager().findFragmentById(R.id.g11));
            System.out.println(getSupportFragmentManager().findFragmentById(R.id.g22));
            Log.d(msg, "left id on panel closed  "+getSupportFragmentManager().findFragmentById(R.id.g11));
            Log.d(msg, "right id on panel closed  "+getSupportFragmentManager().findFragmentById(R.id.g22));

//            getFragmentManager().findFragmentById(R.id.activity_left).setHasOptionsMenu(false);
//            getFragmentManager().findFragmentById(R.id.activity_right).setHasOptionsMenu(true);
        }

        @Override
        public void onPanelOpened(View view) {
            System.out.println("Panel opened"+view);
            System.out.println(getSupportFragmentManager().findFragmentById(R.id.g11));
            System.out.println(getSupportFragmentManager().findFragmentById(R.id.g22));
            Log.d(msg, "left id on panel opened  "+getSupportFragmentManager().findFragmentById(R.id.g11));
            Log.d(msg, "right id on panel opened  "+getSupportFragmentManager().findFragmentById(R.id.g22));
//            getFragmentManager().findFragmentById(R.id.activity_left).setHasOptionsMenu(true);
//            getFragmentManager().findFragmentById(R.id.activity_right).setHasOptionsMenu(false);
        }



        @Override
        public void onPanelSlide(View panel, float slideOffset) {
            Log.d(msg," sliding panel"+panel);
        }

        }
    }

