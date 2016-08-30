package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MortgageScreen extends AppCompatActivity{
    Globals g;
    ArrayList<PropertyCard> properties = new ArrayList<PropertyCard>();
    ArrayList<Player> players = new ArrayList<Player>();
    Player currentPlayer;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_screen);

        final Bundle passedData = getIntent().getExtras();
        final String playerID = passedData.getString("playerID");

        g = (Globals)getApplication();
        properties = g.getOwnedProperties(playerID);
        Log.d("asdf","getOwnedProperties = " + String.valueOf(properties.size()));

        players = g.getPlayers();
        listView = (ListView) findViewById(R.id.listView);

        for(Player p : players){
            if (p.getId().equals(playerID)){
                currentPlayer = p; break;
            }
        }

        if(properties.size() > 0){
            listView.setAdapter(new PMAdapter(this,properties));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    final PropertyCard propertyCard = (PropertyCard) listView.getItemAtPosition(i);
                    Log.d("myTag",propertyCard.getName() + "clicked");

                    final int mortgagePrice = propertyCard.getMortgage();
                    final String propertyName = propertyCard.getName();

                    //Create Yes/No Dialogue Box
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MortgageScreen.this);
                    builder1.setMessage("Mortgage " + propertyName +" for $" + mortgagePrice +"?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    //modify player cash
                                    currentPlayer.addToCash(mortgagePrice);
                                    //replace old currentPlayer with newly updated currentPlayer in players.
                                    for(int i = 0; i < players.size();i++){
                                        if (players.get(i).getId().equals(currentPlayer.getId())){
                                            players.set(i,currentPlayer); break;
                                        }
                                    }
                                    //update the globals with the new players list
                                    g.setPlayers(players);
                                    //modify and update the properties list using the setOwner function
                                    setOwnerToNone(playerID);
                                    //end
                                    setResult(RESULT_OK);
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
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(MortgageScreen.this);
            builder1.setMessage("No properties owned");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
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
}
