package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class MortgageScreen extends AppCompatActivity {
    Globals g;
    ArrayList<PropertyCard> properties = new ArrayList<PropertyCard>();
    ArrayList<Button> buttons = new ArrayList<Button>();
    String fragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle passedData = getIntent().getExtras();
        fragmentTag = passedData.getString("fragmentTag");
        Log.d("Properties","Fragment tag -->" + fragmentTag);
        //setContentView(R.layout.activity_purchase_screen);
        g = (Globals)getApplication();
        //obtain a copy of properties from globals
        properties = g.getProperties();

        //Change Layout
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        if(properties == null){
            Log.d("myTag","properties(PurchaseScreen) is null");
        } else {
            for(PropertyCard p : properties){
                Log.d("Properties",p.getName() + p.getOwner());
                if(p.getOwner().equals(fragmentTag)){           //Create a button if player owns the property
                    Log.d("Properties","match found");
                    final Button button = new Button(this);
                    //customize button
                    button.setText(p.getName());
                    button.setTextAppearance(R.style.fontForNotificationLandingPage);
                    setBackgroundColor(button,p);
                    //Set event handler
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //Create Yes/No Dialogue Box
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MortgageScreen.this);
                            builder1.setMessage("Mortgage " + button.getText().toString() +" for $" + getMortgagePrice(button.getText().toString()) +"?");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            int newCash = passedData.getInt("currentCash");
                                            int valueToPass = newCash + getMortgagePrice(button.getText().toString());
                                            setOwnerToNone(button.getText().toString());
//                                          Log.d("myTag",String.valueOf(valueToPass));

                                            Intent i = new Intent();
                                            i.putExtra("price",valueToPass);
                                            setResult(RESULT_OK,i);
                                            finish();
                                            dialog.cancel();
                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }
                    });
                    buttons.add(button);
                    //add button to view
                    linearLayout.addView(button);
                }

            }
        }

        ScrollView scrollView = new ScrollView(this);
        setContentView(scrollView);
        scrollView.addView(linearLayout);
    }

    public int getPurchasePrice(String buttonName){
        for(PropertyCard p: properties){
            if(p.getName().equals(buttonName)){
                return p.getPrice();
            }
        }
        return 0;
    }

    public int getMortgagePrice(String buttonName){
        for(PropertyCard p: properties){
            if(p.getName().equals(buttonName)){
                return p.getMortgage();
            }
        }
        return 0;
    }

    public void setOwner(String s, String fragmentTag){
        for(PropertyCard p: properties){
            if(p.getName().equals(s)){
                p.setOwner(fragmentTag);
                g.setProperties(properties);
            }
        }
    }

    public void setOwnerToNone(String s){
        for(PropertyCard p: properties){
            if(p.getName().equals(s)){
                p.setOwnerToNone();
                g.setProperties(properties);
            }
        }
    }



    public void setBackgroundColor(Button b, PropertyCard p){
        String color = p.getColor();
        switch (color){
            case "dark purple":
                b.getBackground().setColorFilter(Color.parseColor("#9575CD"), PorterDuff.Mode.DARKEN);
                return;
            case "light blue":
                b.getBackground().setColorFilter(Color.parseColor("#80DEEA"), PorterDuff.Mode.DARKEN);
                return;
            case "pink":
                b.getBackground().setColorFilter(Color.parseColor("#F06292"), PorterDuff.Mode.DARKEN);
                return;
            case "orange":
                b.getBackground().setColorFilter(Color.parseColor("#FFB74D"), PorterDuff.Mode.DARKEN);
                return;
            case "yellow":
                b.getBackground().setColorFilter(Color.parseColor("#FDD835"), PorterDuff.Mode.DARKEN);
                return;
            case "red":
                b.getBackground().setColorFilter(Color.parseColor("#e57373"), PorterDuff.Mode.DARKEN);
                return;
            case "green":
                b.getBackground().setColorFilter(Color.parseColor("#81C784"), PorterDuff.Mode.DARKEN);
                return;
            case "dark blue":
                b.getBackground().setColorFilter(Color.parseColor("#03A9F4"), PorterDuff.Mode.DARKEN);
                return;
            default:
                return;
        }
    }
}
