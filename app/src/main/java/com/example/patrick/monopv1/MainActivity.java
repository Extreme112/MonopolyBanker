package com.example.patrick.monopv1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final int CHLD_REQ1 = 1;
    Globals g;
    private ArrayList<Player> players = new ArrayList<Player>();

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        g = (Globals)getApplication();
        players = g.getPlayers();

        listView = (ListView) findViewById(R.id.mainActivity_listView);



        listView.setAdapter(new PlayerAdapter(this,players));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(),SelectActionScreen.class);

                Player player = (Player) listView.getItemAtPosition(i);
                //Send player ID
                intent.putExtra("playerID",player.getId());
                //Send players cash
                intent.putExtra("playerCash",player.getCash());
                startActivityForResult(intent,CHLD_REQ1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        players = g.getPlayers();
        listView.setAdapter(new PlayerAdapter(this,players));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(),SelectActionScreen.class);

                Player player = (Player) listView.getItemAtPosition(i);
                //Send player ID
                intent.putExtra("playerID",player.getId());
                //Send players cash
                intent.putExtra("playerCash",player.getCash());
                startActivityForResult(intent,CHLD_REQ1);
            }
        });

    }
    //    @Override
//    public void respond(String fragmentTagofPlayerToPay, int amountOfMoneyToGive) {
//        Log.d("myTag","respond function called in main");
//        Log.d("myTag","amountOfMoneyToGive = " + String.valueOf(amountOfMoneyToGive));
//        //find fragment of player
//        PlayerFragment playerFragment =(PlayerFragment)getFragmentManager().findFragmentByTag(fragmentTagofPlayerToPay);
//        playerFragment.receiveMoney(amountOfMoneyToGive);
//    }
}
