package com.example.evan.newsapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by EVAN on 7/25/2017.
 */

public class DatabaseUtils {

    // returns all items from the db
    public static Cursor getAll(SQLiteDatabase db){
        Cursor cursor = db.query(
                Contract.TABLE_ITEMS.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    // inserts items into the db by bulk
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> news)
    {
        db.beginTransaction();
        try{
            for (NewsItem item: news)
            {
                ContentValues cv = new ContentValues();
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_TITLE, item.getTitle());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_AUTHOR, item.getAuthor());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_DESCRIPTION, item.getDescription());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_PUBLISHEDAT, item.getPublishedAt());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_URL, item.getUrl());
                cv.put(Contract.TABLE_ITEMS.COLUMN_NAME_URLTOIMAGE, item.getUrlToImage());
                db.insert(Contract.TABLE_ITEMS.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db){
        db.delete(Contract.TABLE_ITEMS.TABLE_NAME, null, null);
    }
}
