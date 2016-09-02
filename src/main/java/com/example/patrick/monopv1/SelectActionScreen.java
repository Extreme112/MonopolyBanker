package com.example.patrick.monopv1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectActionScreen extends AppCompatActivity implements P2PAmountDF.P2PEditTextInterface,ListDF.ListDFInterface,P2BAmountDF.P2BEditTextInterface {
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

        Typeface cashFont = Typeface.createFromAsset(getAssets(),"fonts/CashCurrency.ttf");

        textView_cash = (TextView) findViewById(R.id.text_cash);
        textView_cash.setTypeface(cashFont);
        textView_cash.setText(String.valueOf(currentPlayer.getCash()));

        textView_playerName = (TextView) findViewById(R.id.textView_playerName);
        textView_playerName.setText(currentPlayer.getName());

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

    public void but_printProperties(View v){
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
            if (!p.getName().equals(currentPlayer.getName())){
                playerNames.add(p.getName());
            }
        }
        playerNames.add("All Players");
        CharSequence[] cs = playerNames.toArray(new CharSequence[playerNames.size()]);

        String method = "payment";
        Bundle args = new Bundle();
        args.putCharSequenceArray("cs",cs);
        args.putString("title","Pick Player");
        args.putString("method",method);

        ListDF listDF = new ListDF();
        listDF.setArguments(args);
        listDF.show(getFragmentManager(),"listDF");
    }

    public void but_collectFromPlayer(View v){
        final ArrayList<String> playerNames = new ArrayList<String>();
        for (Player p : players){
            if (!p.getName().equals(currentPlayer.getName())){
                playerNames.add(p.getName());
            }
        }
        playerNames.add("All Players");
        CharSequence[] cs = playerNames.toArray(new CharSequence[playerNames.size()]);


        String method = "collect";
        Bundle args = new Bundle();
        args.putCharSequenceArray("cs",cs);
        args.putString("title","Pick Player");
        args.putString("method",method);

        ListDF listDF = new ListDF();
        listDF.setArguments(args);
        listDF.show(getFragmentManager(),"listDF");
    }

    public void but_payToBank(View v){
        String method = "payment";
        String title = "Amount";
        Bundle args = new Bundle();
        args.putString("method",method);
        args.putString("title",title);
        P2BAmountDF p2BAmountDF = new P2BAmountDF();
        p2BAmountDF.setArguments(args);
        p2BAmountDF.show(getFragmentManager(),"payToBank");
    }

    public void but_collectFromBank(View v){
        String method = "collect";
        String title = "Amount";
        Bundle args = new Bundle();
        args.putString("method",method);
        args.putString("title",title);
        P2BAmountDF p2BAmountDF = new P2BAmountDF();
        p2BAmountDF.setArguments(args);
        p2BAmountDF.show(getFragmentManager(),"collectFromBank");
    }
    public void update(){
        players = g.getPlayers();
        for (Player p : players){
            if(p.getId().equals(playerID))
                currentPlayer = p;
        }
        textView_cash.setText(String.valueOf(currentPlayer.getCash()));
    }

    //P2PAmountDF
    @Override
    public void performActions(int price, String method, boolean toAllPlayers) {

        if (toAllPlayers && method.equals("payment")){
            //pay all players here
            int playerCounter = 0;
            for (Player p : players){
                if (!p.getName().equals(currentPlayer.getName())){
                    playerCounter++;
                }
            }

            if((playerCounter * price) > currentPlayer.getCash() && method.equals("payment")){
                Toast.makeText(getBaseContext(),"Not enough cash to pay players.",Toast.LENGTH_SHORT).show();
            } else {
                currentPlayer.subtractFromCash(playerCounter * price);
                for (Player p : players){
                    if (!p.getName().equals(currentPlayer.getName())){
                        p.addToCash(price);
                        playerCounter++;
                    }
                }
                g.setPlayers(players);
                update();
            }

        } else if (toAllPlayers && method.equals("collect")){
            int playerCounter = 0;
            for (Player p : players){
                if (p.getCash() >= price && !p.getName().equals(currentPlayer.getName())){
                    p.subtractFromCash(price);
                    playerCounter++;
                } else if (!p.getName().equals(currentPlayer.getName())){
                    Toast.makeText(getBaseContext(),"Cannot collect from player " + p.getName(),Toast.LENGTH_SHORT).show();
                }
            }
            currentPlayer.addToCash(playerCounter * price);
            g.setPlayers(players);
            update();

        } else if (price > currentPlayer.getCash() && method.equals("payment")){
            Toast.makeText(getBaseContext(),"Not enough cash.",Toast.LENGTH_SHORT).show();
        } else if (method.equals("payment")){
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
        } else if (method.equals("collect") && playerToPay.getCash() >= price){
            currentPlayer.addToCash(price);
            playerToPay.subtractFromCash(price);
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
        } else {
            Toast.makeText(getBaseContext(),"Player you are collecting from has insufficient funds.",Toast.LENGTH_SHORT).show();
        }
    }
    //ListDF
    @Override
    public void performActions(String selectedName, String method) {
        //find player to pay and from players and assign it to playerToPay based on selectedPlayerID
        //modify cash value of player with selectedPlayerId
        Log.d("df","selectedName = " + selectedName);
        boolean toAllPlayers;

        if (selectedName.equals("All Players")){
            toAllPlayers = true;
        } else {
            for (Player p : players){
                if (p.getName().equals(selectedName)){
                    playerToPay = p; break;
                }
            }
            toAllPlayers = false;
        }

        //create second dialogue
        String title = "Amount";
        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("method",method);
        args.putBoolean("toAllPlayers",toAllPlayers);
        P2PAmountDF p2PAmountDF = new P2PAmountDF();
        p2PAmountDF.setArguments(args);
        p2PAmountDF.show(getFragmentManager(),"amountDF");

    }
    //P2BAmountDF
    @Override
    public void performActions(String method, int price) {
        if (method.equals("payment") && currentPlayer.getCash() >= price) {
            currentPlayer.subtractFromCash(price);
        } else if (method.equals("collect")){
            currentPlayer.addToCash(price);
        }

        update();
        g.setPlayers(players);
    }
}
