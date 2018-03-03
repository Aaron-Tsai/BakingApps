package com.sctech.baking_apps.recipes;

public class steps {
    private int mId;
    private String mShortDescription;
    private String mLongDescription;
    private String mVideoUrl;

    public steps(int id, String sDesc, String lDesc, String url) {
        mId = id;
        mShortDescription = sDesc;
        mLongDescription = lDesc;
        mVideoUrl = url;
    }

    public int getId() {return mId;}
    public String getShortDescription() {return mShortDescription;}
    public String getmLongDescription() {return mLongDescription;}
    public String getVideoUrl() {return mVideoUrl;}
}