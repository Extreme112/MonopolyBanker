package com.example.patrick.monopv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static final int CHLD_REQ1 = 1;
    Globals g;
    int startingCash;

    //button references
    TextView but_cash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        g = (Globals)getApplication();
        startingCash = g.getStartingCash();

        but_cash = (TextView) findViewById(R.id.but_cash);
        but_cash.setText(String.valueOf(startingCash));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHLD_REQ1 && resultCode == RESULT_OK){
            Bundle recievedData = data.getExtras();
            //recievedData.getInt("newNumber");
            but_cash.setText(String.valueOf(recievedData.getInt("newNumber")));

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void nameClick(View v){
        Intent i = new Intent(this,SelectActionScreen.class);
        //pass integer via i.putExtras
        int currentCashNumber = Integer.parseInt(but_cash.getText().toString());
        i.putExtra("mainActivityCash",currentCashNumber);
        startActivityForResult(i,CHLD_REQ1);
    }


}
