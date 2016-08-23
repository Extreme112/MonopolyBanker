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

    public PropertyCard(String init_propertyName, int init_price, int init_rentCost,int init_house_1Cost, int init_house_2Cost,
                        int init_house_3Cost, int init_house_4Cost, int init_hotelCost, int init_mortgage){
        name = init_propertyName;
        price = init_price;
        rent = init_rentCost;
        house_1 = init_house_1Cost;
        house_2 = init_house_2Cost;
        house_3 = init_house_3Cost;
        house_4 = init_house_4Cost;
        hotel = init_hotelCost;
        mortgage = init_mortgage;
    }

    public PropertyCard(String init_propertyName, int init_price, int init_rentCost, int init_mortgage){
        name = init_propertyName;
        price = init_price;
        rent = init_rentCost;
        mortgage = init_mortgage;
    }

    public PropertyCard(){

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
}
