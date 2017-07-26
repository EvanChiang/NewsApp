package com.example.evan.newsapp;

import android.net.Uri;

import com.example.evan.newsapp.data.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class NetworkUtils {

    public static final String TAG = "NetworkUtils";
    public static final String BASE_URL = "https://newsapi.org/v1/articles?";
    public static final String PARAM_SORT = "sortBy";
    public static final String PARAM_SORT_LATEST = "latest";
    public static final String PARAM_SOURCE = "source";
    public static final String PARAM_SOURCE_NEXT = "the-next-web";
    public static final String PARAM_APIKEY = "apiKey";
    public static final String MY_APIKEY = "f8273dc641f84585aabb246b762012d3";

    public static URL makeURL(){
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, PARAM_SOURCE_NEXT)
                .appendQueryParameter(PARAM_SORT, PARAM_SORT_LATEST)
                .appendQueryParameter(PARAM_APIKEY, MY_APIKEY)
                .build();

        URL url = null;
        try{
            String urlString = uri.toString();
            url = new URL(uri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static ArrayList<NewsItem> parseJSON(String jsonFile) throws JSONException
    {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        JSONObject json = new JSONObject(jsonFile);
        JSONArray articles = json.getJSONArray("articles");

        for (int i = 0; i < articles.length(); i++)
        {
            JSONObject article = articles.getJSONObject(i);
            NewsItem newsItem = new NewsItem(article.getString("author"),
                                            article.getString("title"),
                                            article.getString("description"),
                                            article.getString("url"),
                                            article.getString("urlToImage"),
                                            article.getString("publishedAt"));
            newsItems.add(newsItem);
        }
        return newsItems;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");
            boolean hasInput = sc.hasNext();
            if (hasInput)
                return sc.next();
            else
                return null;
        }finally {
            urlConnection.disconnect();
        }
    }
}
