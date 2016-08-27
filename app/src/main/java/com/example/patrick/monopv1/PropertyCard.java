package com.example.patrick.monopv1;

/**
 * Created by Patrick on 8/21/2016.
 */
public class PropertyCard {
    private String name;
    private int price;
    private int rent;
    private int house_1;
    private int house_2;
    private int house_3;
    private int house_4;
    private int hotel;
    private int mortgage;
    private String color;
    private String owner = "none";
    private int img;

    public PropertyCard(String init_propertyName, int init_price, int init_rentCost,int init_house_1Cost, int init_house_2Cost,
                        int init_house_3Cost, int init_house_4Cost, int init_hotelCost, int init_mortgage, String init_color){
        name = init_propertyName;
        price = init_price;
        rent = init_rentCost;
        house_1 = init_house_1Cost;
        house_2 = init_house_2Cost;
        house_3 = init_house_3Cost;
        house_4 = init_house_4Cost;
        hotel = init_hotelCost;
        mortgage = init_mortgage;
        color = init_color;

        switch (color){
            case "dark purple": img = R.drawable.label_purple; break;
            case "light blue": img = R.drawable.label_lightblue; break;
            case "pink" : img = R.drawable.label_pink; break;
            case "orange" : img = R.drawable.label_orange; break;
            case "yellow" : img = R.drawable.label_yellow; break;
            case "red" : img = R.drawable.label_red; break;
            case "green" : img = R.drawable.label_green; break;
            case "dark blue": img = R.drawable.label_blue; break;
            default: img = 0; break;
        }
    }

    public PropertyCard(String init_propertyName, int init_price, int init_rentCost, int init_mortgage, String init_color){
        name = init_propertyName;
        price = init_price;
        rent = init_rentCost;
        mortgage = init_mortgage;
        color = init_color;
        switch (color){
            case "dark purple": img = R.drawable.label_purple; break;
            case "light blue": img = R.drawable.label_lightblue; break;
            case "pink" : img = R.drawable.label_pink; break;
            case "orange" : img = R.drawable.label_orange; break;
            case "yellow" : img = R.drawable.label_yellow; break;
            case "red" : img = R.drawable.label_red; break;
            case "green" : img = R.drawable.label_green; break;
            case "dark blue": img = R.drawable.label_blue; break;
            default: img = 0; break;
        }
    }

    public int getImg() {
        return img;
    }

    public int getRent(){
        return rent;
    }

    public int getHouse_1(){
        return house_1;
    }

    public int getHouse_2(){
        return house_2;
    }

    public int getHouse_3(){
        return house_3;
    }

    public int getHouse_4(){
        return house_4;
    }

    public int getHotel(){
        return hotel;
    }

    public int getMortgage(){
        return mortgage;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public String getColor(){
        return color;
    }

    public String getOwner(){
        return owner;
    }

    public boolean setOwner(String fragmentTag){
        owner = fragmentTag;
        return true;
    }

    public boolean setOwnerToNone(){
        owner = "none";
        return true;
    }



}
