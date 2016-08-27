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
public class DisplayAdapter extends BaseAdapter {
    ArrayList<PropertyCard> properties = new ArrayList<PropertyCard>();
    Context context;

    DisplayAdapter(Context c,ArrayList<PropertyCard> p){
        properties = p;
        context = c;
    }


    @Override
    public int getCount() {
        return properties.size();
    }

    @Override
    public Object getItem(int i) {
        return properties.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.sing_row,viewGroup,false);
        TextView title = (TextView) row.findViewById(R.id.textView);
        ImageView img = (ImageView) row.findViewById(R.id.imageView);

        properties.get(i).getName();
        properties.get(i).getImg();

        title.setText(properties.get(i).getName());
        img.setImageResource(properties.get(i).getImg());
        return row;
    }
}
