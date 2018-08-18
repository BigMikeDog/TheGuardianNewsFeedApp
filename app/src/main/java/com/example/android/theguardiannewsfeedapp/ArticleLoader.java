package com.example.android.theguardiannewsfeedapp;

import android.content.Context;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ArticleLoader extends android.support.v4.content.AsyncTaskLoader {

    private String mUrlString;

    ArticleLoader(Context context, String urlString){
        super(context);
        mUrlString=urlString;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        if (mUrlString==null){
            return null;
        }
        URL url = WebUtility.convertToUrl(mUrlString);
        String jsonString = WebUtility.makeHttpRequest(url);
        List<Article> articles = WebUtility.extractJson(jsonString);

        if(articles==null){
            articles = new ArrayList<>();
        }
        return articles;
    }
}
