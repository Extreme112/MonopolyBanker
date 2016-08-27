package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PurchaseScreen extends AppCompatActivity{
    Globals g;
    ArrayList<PropertyCard> properties = new ArrayList<PropertyCard>();
    ArrayList<Button> buttons = new ArrayList<Button>();

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_purchase_screen);
        g = (Globals)getApplication();
        properties = g.getEmptyProperties();
        listView = (ListView) findViewById(R.id.listView);
        final Bundle passedData = getIntent().getExtras();
        final String fragmentTag = passedData.getString("fragmentTag");

        listView.setAdapter(new DisplayAdapter(this,properties));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                final PropertyCard propertyCard = (PropertyCard) listView.getItemAtPosition(i);
                Log.d("myTag",propertyCard.getName() + "clicked");

                final int purchasePrice = propertyCard.getPrice();
                final String propertyName = propertyCard.getName();
                final int newCash = passedData.getInt("currentCash");

                //Create Yes/No Dialogue Box
                AlertDialog.Builder builder1 = new AlertDialog.Builder(PurchaseScreen.this);
                builder1.setMessage("Purchase " + propertyName +" for $" + purchasePrice +"?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                int valueToPass = newCash - purchasePrice;
                                setOwner(propertyName,fragmentTag);

                                Intent i = new Intent();
                                i.putExtra("price",valueToPass);
                                setResult(RESULT_OK,i);
                                finish();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }



    public void setOwner(String s, String fragmentTag){
        for(PropertyCard p: properties){
            if(p.getName().equals(s)){
                p.setOwner(fragmentTag);
                g.setProperties(properties);
            }
        }
    }
}

class DisplayAdapter extends BaseAdapter {

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