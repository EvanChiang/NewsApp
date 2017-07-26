package com.example.evan.newsapp.data;

import android.provider.BaseColumns;

/**
 * Created by EVAN on 7/25/2017.
 */

// static values used by database
public class Contract {

    public static class TABLE_ITEMS implements BaseColumns {

        public static final String TABLE_NAME = "news";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_URLTOIMAGE = "url_to_image";
        public static final String COLUMN_NAME_PUBLISHEDAT = "published_at";
    }

}
