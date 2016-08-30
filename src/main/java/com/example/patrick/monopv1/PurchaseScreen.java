package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PurchaseScreen extends AppCompatActivity implements YesNoDF.Communicator {
    Globals g;
    ArrayList<PropertyCard> properties = new ArrayList<PropertyCard>();
    ArrayList<Player> players = new ArrayList<Player>();
    Player currentPlayer;
    ListView listView;
    String playerID;
    Bundle passedData;

    //Property card info
    PropertyCard propertyCard;
    String propertyName;
    int purchasePrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("myTag","PurchaseScreen launched");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_screen);

        g = (Globals)getApplication();
        properties = g.getEmptyProperties();
        players = g.getPlayers();
        listView = (ListView) findViewById(R.id.listView);
        //get extras
        passedData = getIntent().getExtras();
        playerID = passedData.getString("playerID");
        //get current player based on playerID. now we know who is doing the purchasing
        for(Player p : players){
            if (p.getId().equals(playerID)){
                currentPlayer = p; break;
            }
        }
        Log.d("la","PurchaseScreen launched for " + currentPlayer.getName() + " with ID:" + currentPlayer.getId());

        if(properties.size() > 0){
            listView.setAdapter(new PMAdapter(this,properties));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    propertyCard = (PropertyCard) listView.getItemAtPosition(i);;

                    propertyName = propertyCard.getPropertyName();
                    purchasePrice = propertyCard.getPurchasePrice();

                    //create dialog box
                    String message = "Are you sure you want to purchase " + propertyName + "for $" + purchasePrice + "?";
                    Bundle args = new Bundle();
                    args.putInt("purchasePrice",purchasePrice);
                    args.putString("message",message);
                    args.putString("propertyName",propertyName);
                    YesNoDF yesNoDF = new YesNoDF();
                    yesNoDF.setArguments(args);
                    yesNoDF.show(getFragmentManager(),"purchaseDF");

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
            if(p.getPropertyName().equals(propertyName)){
                p.setOwner(playerID);
                g.setProperties(properties);
            }
        }
    }


    @Override
    public void performActions() {
        Log.d("myTag","performing--actions");
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
        setResult(RESULT_OK);
        finish();
    }
}