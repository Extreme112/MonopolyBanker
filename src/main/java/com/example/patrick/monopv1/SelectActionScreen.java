package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectActionScreen extends AppCompatActivity implements EditTextDF.EditTextDFInterface,ListDF.ListDFInterface {
    public static final int CHLD_REQ2 = 2;
    //declarations
    Globals g;
    TextView textView_cash;
    TextView textView_playerName;
    String playerID;
    ArrayList<Player> players = new ArrayList<Player>();
    Player currentPlayer;
    Player playerToPay;
    Button but_printProperties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action_screen);
        g = (Globals)getApplication();
        players = g.getPlayers();

        //get passed data
        Bundle passedData = getIntent().getExtras();
        playerID = passedData.getString("playerID");
        //get currentPlayer based on playerID
        for (Player p : players){
            if(p.getId().equals(playerID))
                currentPlayer = p;
        }

        textView_cash = (TextView) findViewById(R.id.text_cash);
        textView_cash.setText(String.valueOf(currentPlayer.getCash()));

        textView_playerName = (TextView) findViewById(R.id.textView_playerName);
        textView_playerName.setText(currentPlayer.getName());

        //button for debugging purposes
        but_printProperties = (Button) findViewById(R.id.but_printProperties);
        but_printProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                but_printProperties();
            }
        });

        Log.d("myTag",this.getClass().getSimpleName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHLD_REQ2 && resultCode == RESULT_OK){
            //update players
            players = g.getPlayers();
            for (Player p : players){
                if(p.getId().equals(playerID))
                    currentPlayer = p;
            }
            Log.d("myTag",String.valueOf(currentPlayer.getCash()));
            textView_cash.setText(String.valueOf(currentPlayer.getCash()));

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void but_printProperties(){
        ArrayList<PropertyCard> properties;
        properties = g.getProperties();
        for (PropertyCard p : properties){
            Log.d("pp",p.getPropertyName() + p.getOwner());
        }
    }

    public void but_purchase(View v){
        if(g.getEmptyProperties().size() <= 0){
            Toast.makeText(getBaseContext(),"No properties available.",Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this,PurchaseScreen.class);
            i.putExtra("playerID",playerID);
            startActivityForResult(i,CHLD_REQ2);
        }

    }

    public void but_mortgage(View v){
        if(g.getOwnedProperties(playerID).size() <= 0){
            Toast.makeText(getBaseContext(),"No owned properties.",Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this,MortgageScreen.class);
            i.putExtra("playerID",playerID);
            startActivityForResult(i,CHLD_REQ2);
        }

    }

    public void but_payToPlayer(View v){
        final ArrayList<String> playerNames = new ArrayList<String>();
        for (Player p : players){
            playerNames.add(p.getName());
        }
        CharSequence[] cs = playerNames.toArray(new CharSequence[playerNames.size()]);

        Bundle args = new Bundle();
        args.putCharSequenceArray("cs",cs);
        args.putString("title","Pick Player");
        ListDF listDF = new ListDF();
        listDF.setArguments(args);
        listDF.show(getFragmentManager(),"listDF");
    }

    public void update(){
        players = g.getPlayers();
        for (Player p : players){
            if(p.getId().equals(playerID))
                currentPlayer = p;
        }
        textView_cash.setText(String.valueOf(currentPlayer.getCash()));
    }

    //EditTextDF
    @Override
    public void performActions(int price) {
        if (price > currentPlayer.getCash()){
            Toast.makeText(getBaseContext(),"Not enough cash.",Toast.LENGTH_SHORT).show();
        } else {
            currentPlayer.subtractFromCash(price);
            playerToPay.addToCash(price);
            //update current players
            for (Player p : players) {
                if (p == currentPlayer) {
                    p = currentPlayer;
                }
            }
            //update playerToPay
            for (Player p : players) {
                if (p == playerToPay) {
                    p = playerToPay;
                }
            }
            //update global g
            g.setPlayers(players);
            update();
        }
    }

    //listDF
    @Override
    public void performActions(String selectedPlayerID) {
        //find player to pay and from players and assign it to playerToPay based on selectedPlayerID
        //modify cash value of player with selectedPlayerId
        Log.d("df","selectedPlayerID = " + selectedPlayerID);
        for (Player p : players){
            if (p.getId().equals(selectedPlayerID)){
                playerToPay = p; break;
            }
        }
        //create second dialogue
        String title = "Amount";
        Bundle args = new Bundle();
        args.putString("title",title);
        EditTextDF editTextDF = new EditTextDF();
        editTextDF.setArguments(args);
        editTextDF.show(getFragmentManager(),"amountDF");

    }
}
