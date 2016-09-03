package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NotCancellableEditTextDF.NotCancellableEditTextDFInterface{
    public static final int CHLD_REQ1 = 1;
    Globals g;
    private ArrayList<Player> players = new ArrayList<Player>();

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        g = (Globals)getApplication();

        NotCancellableEditTextDF notCancellableEditTextDF = new NotCancellableEditTextDF();
        notCancellableEditTextDF.show(getFragmentManager(),"EnterNumberOfPlayers");

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

    private void updateAdapter(){
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


    @Override
    public void performActions(int numberOfPlayers) {
        g.setNumberOfPlayers(numberOfPlayers);
        ///////////////////////////////////////////
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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Player currentPlayer = (Player) adapterView.getItemAtPosition(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Edit name");
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.edit_name_layout, null);
                final EditText nameField = (EditText) v.findViewById(R.id.editName_entry);
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(v)
                        // Add action buttons
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //modify
                                String newName = nameField.getText().toString();
                                currentPlayer.setName(newName);
                                //update local players
                                for (Player p : players){
                                    if (currentPlayer.getId().equals(p.getId())){
                                        p = currentPlayer;
                                    }
                                }
                                //update global players
                                g.setPlayers(players);
                                updateAdapter();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                alertDialog.show();

                return true;
            }
        });
    }
}
