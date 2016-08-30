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

public class MortgageScreen extends AppCompatActivity implements PurchaseDialog.PurchaseDialogCommunicator{
    Globals g;
    ArrayList<PropertyCard> properties = new ArrayList<PropertyCard>();
    ArrayList<Player> players = new ArrayList<Player>();
    Player currentPlayer;
    ListView listView;
    String playerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_screen);

        final Bundle passedData = getIntent().getExtras();
        playerID = passedData.getString("playerID");
        g = (Globals)getApplication();
        properties = g.getOwnedProperties(playerID);
        players = g.getPlayers();
        listView = (ListView) findViewById(R.id.listView);
        //get extras

        //get current player based on playerID. now we know who is doing the mortgaging
        for(Player p : players){
            if (p.getId().equals(playerID)){
                currentPlayer = p; break;
            }
        }
        Log.d("la","MortgageScreen launched for " + currentPlayer.getName() + " with ID:" + currentPlayer.getId());

        if(properties.size() > 0){
            listView.setAdapter(new PMAdapter(this,properties));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    final PropertyCard propertyCard = (PropertyCard) listView.getItemAtPosition(i);
                    Log.d("myTag",propertyCard.getPropertyName() + "clicked");

                    final int mortgagePrice = propertyCard.getMortgagePrice();
                    final String propertyName = propertyCard.getPropertyName();

                    //create dialog box
                    String message = "Are you sure you want to mortgage " + propertyName + "for $" + mortgagePrice + "?";
                    Bundle args = new Bundle();
                    args.putInt("mortgagePrice",mortgagePrice);
                    args.putString("message",message);
                    args.putString("propertyName",propertyName);
                    PurchaseDialog purchaseDialog = new PurchaseDialog();
                    purchaseDialog.setArguments(args);
                    purchaseDialog.show(getFragmentManager(),"purchaseDialogFragment");
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
            if(p.getPropertyName().equals(s)){
                p.setOwnerToNone();
                g.setProperties(properties);
            }
        }
    }

    @Override
    public void performActions(int price, String propertyName) {
        //modify player cash
        currentPlayer.subtractFromCash(price);
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
    }
}
