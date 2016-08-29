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

public class SelectActionScreen extends AppCompatActivity {
    public static final int CHLD_REQ2 = 2;
    //declarations
    Globals g;
    TextView text_cash;
    String fragmentTag;
    String playerID;
    ArrayList<Player> players = new ArrayList<Player>();
    Player currentPlayer;

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

        text_cash = (TextView) findViewById(R.id.text_cash);
        text_cash.setText(String.valueOf(currentPlayer.getCash()));


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
            text_cash.setText(String.valueOf(currentPlayer.getCash()));

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

    public void but_pay(View v){
        //button will pay 50 to player 2
        ArrayList<String> playerNames = new ArrayList<String>();
        for (Player p : players){
            playerNames.add(p.getName());
        }
        CharSequence[] cs = {"James","jill"};

        //Create Yes/No Dialogue Box
        AlertDialog.Builder builder = new AlertDialog.Builder(SelectActionScreen.this);
        builder.setMessage("Who to pay?");
        builder.setCancelable(true);
        builder.setItems(
                cs,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }
}
