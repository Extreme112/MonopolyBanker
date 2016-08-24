package com.example.patrick.monopv1;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Patrick on 8/21/2016.
 */
public class Globals extends Application{
    private ArrayList<PropertyCard> properties = new ArrayList<PropertyCard>();
    private int startingCash = 1500;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("myTag","Starting...");
        try {
            String  tempJson = loadJSONFromAsset();
            JSONObject jsonObj = new JSONObject(tempJson);
            JSONArray m_jarray = jsonObj.getJSONArray("properties");

            Log.d("myTag","Collecting data...");
            for(int i = 0; i < m_jarray.length(); i++){
                JSONObject tempObj = m_jarray.getJSONObject(i);
                String tempName = tempObj.getString("name");
                int tempPrice = tempObj.getInt("price");
                int tempRent = tempObj.getInt("rent");
                int tempMortgage = tempObj.getInt("mortgage");
                String tempColor = tempObj.getString("color");

                PropertyCard tempPropertyCared = new PropertyCard(tempName,tempPrice,tempRent,tempMortgage,tempColor);
                properties.add(tempPropertyCared);

                Log.d("Details-->",tempObj.getString("name"));
            }

            for(PropertyCard p : properties){
                Log.d("Details-->", p.getName());
            }
        } catch (JSONException e){
            Log.d("myTag","Exception-called in onCreate");
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        Log.d("myTag","loadJSONFromAsset called");
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("properties.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.d("myTag","Exception in loadJSONFromAsset");
            return null;
        }
        Log.d("myTag","returning json");
        return json;
    }

    public ArrayList<PropertyCard> getProperties(){
        return properties;
    }

    public int getStartingCash(){
        return startingCash;
    }

    public void setStartingCash(int startCash){
        startingCash = startCash;
    }
}
