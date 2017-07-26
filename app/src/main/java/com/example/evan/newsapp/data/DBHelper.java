package com.example.evan.newsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by EVAN on 7/25/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creating the SQL database table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE" + Contract.TABLE_ITEMS.TABLE_NAME + " (" +
                Contract.TABLE_ITEMS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_AUTHOR + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_PUBLISHEDAT + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_URL + " TEXT, " +
                Contract.TABLE_ITEMS.COLUMN_NAME_URLTOIMAGE + " TEXT, " +
                "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }


    //drop the table if the version is newer
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            db.execSQL("DROP TABLE " + Contract.TABLE_ITEMS.TABLE_NAME + " IF EXISTS;");
    }
}
