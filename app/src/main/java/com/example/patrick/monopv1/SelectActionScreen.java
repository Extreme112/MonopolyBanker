package com.example.patrick.monopv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectActionScreen extends AppCompatActivity {
    public static final int CHLD_REQ2 = 2;
    TextView text_cash;
    String fragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action_screen);

        Bundle passedData = getIntent().getExtras();
        int passedCashNumber = passedData.getInt("mainActivityCash");
        fragmentTag = passedData.getString("fragmentTag");

        text_cash = (TextView) findViewById(R.id.text_cash);
        text_cash.setText(String.valueOf(passedCashNumber));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHLD_REQ2 && resultCode == RESULT_OK){
            //receives int from purchase screen
            Bundle recievedData = data.getExtras();
            int passedPrice = recievedData.getInt("price");

            text_cash.setText(String.valueOf(passedPrice));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void but_purchase(View v){
        Intent i = new Intent(this,PurchaseScreen.class);
        i.putExtra("currentCash",Integer.parseInt(text_cash.getText().toString()));
        i.putExtra("fragmentTag",fragmentTag);
        startActivityForResult(i,CHLD_REQ2);
    }

    public void but_confirm(View v){
        Intent i = new Intent();
        int passNumber = Integer.parseInt(text_cash.getText().toString());
        i.putExtra("newNumber",passNumber);
        setResult(RESULT_OK,i);
        finish();
    }


}
