package com.example.evan.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Network;
import android.provider.ContactsContract;

import com.example.evan.newsapp.data.DBHelper;
import com.example.evan.newsapp.data.DatabaseUtils;
import com.example.evan.newsapp.data.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by EVAN on 7/25/2017.
 */

// refreshes the database
public class RefreshTasks {

    public static final String ACTION_REFRESH = "refresh";

    public static void refreshArticles(Context context){
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.makeURL();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try{
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DatabaseUtils.bulkInsert(db, result);
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        db.close();
    }
}
