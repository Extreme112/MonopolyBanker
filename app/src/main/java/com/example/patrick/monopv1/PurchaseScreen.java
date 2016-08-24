package com.example.patrick.monopv1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

        final Bundle passedData = getIntent().getExtras();

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
                //customize button
                button.setText(p.getName());
                button.setTextAppearance(R.style.fontForNotificationLandingPage);
                setBackgroundColor(button,p);
                //Set event handler
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int newCash = passedData.getInt("currentCash");
                        int valueToPass = newCash - getPurchasePrice(button.getText().toString());
                        Log.d("myTag",String.valueOf(valueToPass));

                        Intent i = new Intent();
                        i.putExtra("price",valueToPass);
                        setResult(RESULT_OK,i);
                        finish();
                    }
                });
                buttons.add(button);
                //add button to view
                linearLayout.addView(button);
            }
        }

        ScrollView scrollView = new ScrollView(this);
        setContentView(scrollView);
        scrollView.addView(linearLayout);
    }

    public int getPurchasePrice(String s){
        for(PropertyCard p: properties){
            if(p.getName().equals(s)){
                return p.getPrice();
            }
        }
        return 0;
    }

    public void setBackgroundColor(Button b, PropertyCard p){
        String color = p.getColor();
        switch (color){
            case "dark purple":
                b.getBackground().setColorFilter(Color.parseColor("#9575CD"), PorterDuff.Mode.DARKEN);
                return;
            case "light blue":
                b.getBackground().setColorFilter(Color.parseColor("#29B6F6"), PorterDuff.Mode.DARKEN);
                return;
            case "pink":
                b.getBackground().setColorFilter(Color.parseColor("#F06292"), PorterDuff.Mode.DARKEN);
                return;
            case "orange":
                b.getBackground().setColorFilter(Color.parseColor("#FF7043"), PorterDuff.Mode.DARKEN);
                return;
            case "yellow":
                b.getBackground().setColorFilter(Color.parseColor("#FDD835"), PorterDuff.Mode.DARKEN);
                return;
            case "red":
                b.getBackground().setColorFilter(Color.parseColor("#ef5350"), PorterDuff.Mode.DARKEN);
            default:
                return;
        }
    }
}
