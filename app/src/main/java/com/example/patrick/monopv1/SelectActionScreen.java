package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectActionScreen extends AppCompatActivity {
    public static final int CHLD_REQ2 = 2;
    //declarations
    Globals g;
    TextView textView_cash;
    TextView textView_playerName;
    String playerID;
    ArrayList<Player> players = new ArrayList<Player>();
    Player currentPlayer;
    Player playerToPay;

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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHLD_REQ2 && resultCode == RESULT_OK){
            //update player
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

<<<<<<< HEAD
    public void but_pay(View v){
=======

    public void but_payToPlayer(View v){
>>>>>>> feature/Pay_Players
        //button will pay 50 to player 2
        final ArrayList<String> playerNames = new ArrayList<String>();
        for (Player p : players){
            playerNames.add(p.getName());
        }
<<<<<<< HEAD
        CharSequence[] cs = {"James","jill"};
=======
        CharSequence[] cs = playerNames.toArray(new CharSequence[playerNames.size()]);
>>>>>>> feature/Pay_Players

        //Create Yes/No Dialogue Box
        final AlertDialog.Builder builder = new AlertDialog.Builder(SelectActionScreen.this);
        builder.setTitle("Who to pay?");
        //builder.setCancelable(true);
        builder.setItems(
                cs,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String selectedPlayerID = "P" + id;
                        //modify cash value of player with selectedPlayerId
                        //
                        for (Player p : players){
                            if (p.getId().equals(selectedPlayerID)){
                                playerToPay = p; break;
                            }
                        }
                        Log.d("myTag","Launching second dialog.");
                        //Create another dialogue
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SelectActionScreen.this);
                        builder1.setTitle("Amount");
                        LayoutInflater inflater = getLayoutInflater();
                        View v = inflater.inflate(R.layout.amountopay, null);
                        final EditText editText = (EditText) v.findViewById(R.id.editText_amountToPay);

                        builder1.setView(v)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        int amountToPay = Integer.parseInt(editText.getText().toString());
                                        if (amountToPay > currentPlayer.getCash()){
                                            Toast.makeText(getBaseContext(),"Not enough cash.",Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        } else {
                                            currentPlayer.subtractFromCash(amountToPay);
                                            playerToPay.addToCash(amountToPay);
                                            //update current players
                                            for (Player p : players){
                                                if (p == currentPlayer){
                                                    p = currentPlayer;
                                                }
                                            }
                                            //update playerToPay
                                            for (Player p : players){
                                                if (p == playerToPay){
                                                    p = playerToPay;
                                                }
                                            }
                                            //update global g
                                            g.setPlayers(players);
                                            update();
                                        }

                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog pickAmount = builder1.create();
                        builder1.show();
                    }
                });
<<<<<<< HEAD
        AlertDialog alert11 = builder.create();
        alert11.show();
=======
        AlertDialog pickPlayer = builder.create();
        pickPlayer.show();

    }

    public void update(){
        players = g.getPlayers();
        for (Player p : players){
            if(p.getId().equals(playerID))
                currentPlayer = p;
        }
        textView_cash.setText(String.valueOf(currentPlayer.getCash()));
>>>>>>> feature/Pay_Players
    }
}
