package com.sctech.baking_apps.recipes;

public class ingredient {
    private int    mQuantity;
    private String mMeasure; //measuring capacity
    private String mStrName; //ingredient name

    public ingredient(int qty, String mes, String name) {
        mQuantity = qty;
        mMeasure = mes;
        mStrName = name;
    }

    public int getQty() {return mQuantity;}
    public String getMes() {return mMeasure;}
    public String getName() {return mStrName;}

}
