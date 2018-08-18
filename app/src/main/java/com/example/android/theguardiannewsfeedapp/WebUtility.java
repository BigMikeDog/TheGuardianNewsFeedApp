package com.example.android.theguardiannewsfeedapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class WebUtility {

    private WebUtility(){
    }

    static URL convertToUrl(String urlString){
        URL url = null;
        try {
            url =new URL(urlString);
        } catch (MalformedURLException e){
            Log.e("WebUtility","convertToURL: MalformedURLException: ",e);
        }
        return url;
    }

    static String makeHttpRequest(URL queryUrl){
        String jsonString=null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) queryUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonString = inputStreamToString(inputStream);
            } else {
                Log.e("WebUtility", "makeHttpRequest: Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("WebUtility", "makeHttpRequest: IOException thrown: ",e);
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("WebUtility", "makeHttpRequest: IOException thrown: ",e);
                }
            }
        }
        return jsonString;
    }

    private static String inputStreamToString(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line=br.readLine())!=null){
                sb.append(line).append("\n");
            }
        }catch (IOException e){
            Log.e("WebUtility", "inputStreamToString: IOException thrown: ",e);
        }finally {
            try{
                is.close();
            }catch (IOException e){
                Log.e("WebUtility", "inputStreamToString: IOException thrown: ",e);
            }
        }
        return sb.toString();
    }

    static List<Article> extractJson(String jsonString){
        if (jsonString==null){
            return null;
        }

        ArrayList<Article> articles = new ArrayList<>();

        try {
            JSONObject jsonArticle = new JSONObject(jsonString);
            JSONArray articleArray = jsonArticle.getJSONObject("response").getJSONArray("results");

            for (int i=0;i<articleArray.length();i++){
                JSONObject currentArticle = articleArray.getJSONObject(i);

                String sectionName = currentArticle.getString("sectionName");
                String webTitle = currentArticle.getString("webTitle");
                String webURL = currentArticle.getString("webUrl");
                String date = currentArticle.getString("webPublicationDate");

                JSONArray tags = currentArticle.getJSONArray("tags");

                String name;

                if (tags.length()>0){
                     name = tags.getJSONObject(0).getString("webTitle");
                }else {
                    name=null;
                }

                articles.add(new Article(sectionName,webTitle,webURL,date,name));
            }
        } catch (JSONException e) {
            Log.e("WebUtility","extractJson: ",e);
        }
        return articles;
    }
}
