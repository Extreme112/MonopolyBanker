package com.example.patrick.monopv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Properties;

public class PurchaseScreen extends AppCompatActivity {
    Globals g;
    ArrayList<PropertyCard> properties = new ArrayList<PropertyCard>();
    ArrayList<Button> buttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_purchase_screen);
        g = (Globals)getApplication();
        //assign
        properties = g.getProperties();

        //Change Layout
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        if(properties == null){
            Log.d("myTag","properties(PurchaseScreen) is null");
        } else {
            for(PropertyCard p : properties){
                final Button button = new Button(this);
                button.setText(p.getName());
                button.setTextAppearance(R.style.fontForNotificationLandingPage);

                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int valueToPass = getPurchasePrice(button.getText().toString());
                        Log.d("myTag",String.valueOf(valueToPass));

                        Intent i = new Intent();
                        i.putExtra("price",valueToPass);
                        setResult(RESULT_OK,i);
                        finish();
                    }
                });
                buttons.add(button);
                linearLayout.addView(button);
            }
        }
    }

    public int getPurchasePrice(String s){
        for(PropertyCard p: properties){
            if(p.getName().equals(s)){
                return p.getPrice();
            }
        }
        return 0;
    }


}
