package com.sctech.baking_apps.recipes;

import java.util.ArrayList;

public class recipes {
    private int mId;
    private String mName;
    public ArrayList<ingredient> mIngredients;
    public ArrayList<steps> mSteps;

    public recipes(int id, String name) {
        mId = id;
        mName = name;
        mIngredients = new ArrayList<ingredient>();
        mSteps = new ArrayList<steps>();
    }

    public String getName(){return mName;}
    public int getId() {return mId;}


}
