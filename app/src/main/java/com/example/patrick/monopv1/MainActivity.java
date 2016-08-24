package com.example.patrick.monopv1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin_Layout);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        for (int i = 1; i < 5;i++){
            FrameLayout frameLayout1 = new FrameLayout(this);
            frameLayout1.setId(View.generateViewId());

            PlayerFragment frag1 = new PlayerFragment();
            transaction.add(frameLayout1.getId(),frag1,"P" + i);

            linearLayout.addView(frameLayout1);
        }

//        //Add first fragment
//        FrameLayout frameLayout1 = new FrameLayout(this);
//        frameLayout1.setId(View.generateViewId());
//
//        PlayerFragment frag1 = new PlayerFragment();
//        transaction.add(frameLayout1.getId(),frag1,"Player1");
//
//        linearLayout.addView(frameLayout1);
//        //end of first fragment
//        //add second fragment
//        FrameLayout frameLayout2 = new FrameLayout(this);
//        frameLayout2.setId(View.generateViewId());
//
//        PlayerFragment frag2 = new PlayerFragment();
//        transaction.add(frameLayout2.getId(),frag2,"Player2");
//
//        linearLayout.addView(frameLayout2);
//        //end of second fragment
        transaction.commit();
    }


}
