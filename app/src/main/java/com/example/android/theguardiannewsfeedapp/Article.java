package com.example.android.theguardiannewsfeedapp;

import android.support.annotation.Nullable;

class Article {
    private String mSectionName;
    private String mWebTitle;
    private String mWebURl;
    private String mDate;
    private String mAuthor;

    Article(String sectionName, String webTitle, String webUrl, String date, @Nullable String name){
        mSectionName=sectionName;
        mWebTitle=webTitle;
        mWebURl=webUrl;
        mDate=date;
        mAuthor=name;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmWebTitle() {
        return mWebTitle;
    }

    public String getmWebURl() {
        return mWebURl;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}
