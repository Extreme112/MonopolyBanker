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

public class PurchaseScreen extends AppCompatActivity{
    Globals g;
    ArrayList<PropertyCard> properties = new ArrayList<PropertyCard>();
    ArrayList<Player> players = new ArrayList<Player>();
    Player currentPlayer;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("myTag","Purchase screen launched");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_screen);

        g = (Globals)getApplication();
        properties = g.getEmptyProperties();
        players = g.getPlayers();
        listView = (ListView) findViewById(R.id.listView);
        //get extras
        final Bundle passedData = getIntent().getExtras();
        final String playerID = passedData.getString("playerID");
        //get current player based on playerID. now we know who is doing the purchasing

        for(Player p : players){
            if (p.getId().equals(playerID)){
                currentPlayer = p; break;
            }
        }
        Log.d("myTag",properties.get(0).getName());

        Log.d("myTag",String.valueOf(properties.size()));

        if(properties.size() > 0){
            listView.setAdapter(new PMAdapter(this,properties));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    final PropertyCard propertyCard = (PropertyCard) listView.getItemAtPosition(i);
                    Log.d("myTag",propertyCard.getName() + "clicked");

                    final int purchasePrice = propertyCard.getPrice();
                    final String propertyName = propertyCard.getName();
                    //final int newCash = passedData.getInt("currentCash");

                    //Create Yes/No Dialogue Box
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(PurchaseScreen.this);
                    builder1.setMessage("Purchase " + propertyName +" for $" + purchasePrice +"?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //modify player value
                                    currentPlayer.subtractFromCash(purchasePrice);
                                    //replace old currentPlayer with newly updated currentPlayer in players.
                                    for(int i = 0; i < players.size();i++){
                                        if (players.get(i).getId().equals(currentPlayer.getId())){
                                            players.set(i,currentPlayer); break;
                                        }
                                    }
                                    //update the globals with the new players list
                                    g.setPlayers(players);
                                    //modify and update the properties list using the setOwner function
                                    setOwner(propertyName,playerID);
                                    //
                                    Log.d("asdf",g.getOwnerOfProperty(propertyName));
                                    //
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
            AlertDialog.Builder builder1 = new AlertDialog.Builder(PurchaseScreen.this);
            builder1.setMessage("No properties to buy");
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

    public void setOwner(String propertyCardName, String playerID){
        for(PropertyCard p: properties){
            if(p.getName().equals(propertyCardName)){
                p.setOwner(playerID);
                g.setProperties(properties);
            }
        }
    }
}