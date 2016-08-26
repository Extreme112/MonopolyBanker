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


public class MainActivity extends AppCompatActivity implements PlayerFragment.PaymentCommunicator{
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
        transaction.commit();
    }

    @Override
    public void respond(String fragmentTagofPlayerToPay, int amountOfMoneyToGive) {
        Log.d("myTag","respond function called in main");
        //find fragment of player
        PlayerFragment playerFragment =(PlayerFragment)getFragmentManager().findFragmentByTag(fragmentTagofPlayerToPay);
        playerFragment.receiveMoney(amountOfMoneyToGive);
    }
}
