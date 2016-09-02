package com.example.patrick.monopv1;

/**
 * Created by Patrick on 8/26/2016.
 */
public class Player {
    private String name;
    private String id;
    private int cash;

    public Player(String name, String id, int cash) {
        this.name = name;
        this.id = id;
        this.cash = cash;
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){ name = newName; }

    public String getId(){
        return id;
    }

    public int getCash(){
        return cash;
    }

    public boolean addToCash(int amount){
        cash += amount;
        return true;
    }

    public boolean subtractFromCash(int amount){
        if ((cash - amount) < 0)
            return false;
        cash -= amount;
        return true;
    }
}
