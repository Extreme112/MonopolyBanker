package com.example.patrick.monopv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Patrick on 8/26/2016.
 */
public class PlayerAdapter extends BaseAdapter {

    Context context;
    ArrayList<Player> players = new ArrayList<Player>();
    //Constructor(s)
    PlayerAdapter(Context c,ArrayList<Player> p){
        context = c;
        players = p;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int i) {
        return players.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.player_adapter_single_row,viewGroup,false);
        TextView name = (TextView) row.findViewById(R.id.player_adapter_textViewName);
        TextView cash = (TextView) row.findViewById(R.id.player_adapter_textViewCash);

        name.setText(players.get(i).getName());
        cash.setText(String.valueOf(players.get(i).getCash()));
        return row;
    }
}
