package com.example.evan.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.evan.newsapp.data.Contract;
import com.example.evan.newsapp.data.DBHelper;
import com.example.evan.newsapp.data.DatabaseUtils;
import com.example.evan.newsapp.data.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

//changed MainActivity to implement the LoaderManager and NewsItemAdapter
public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void>, NewsItemAdapter.ItemClickListener{
    public static final String TAG = "mainActivity";
    private ProgressBar progress;
    private RecyclerView recyclerView;
    private NewsItemAdapter newsItemAdapter;
    private Cursor cursor; // added database and cursor objects
    private SQLiteDatabase db;

    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // checking if installed before, if not create database from network
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isFirst", true);

        if (isFirst)
        {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }
        ScheduleUtilities.scheduleRefresh(this);

        // loading what's currently in the db into the RV for display
        db = new DBHelper(MainActivity.this).getReadableDatabase(); //initializing database objects
        cursor = DatabaseUtils.getAll(db);
        newsItemAdapter = new NewsItemAdapter(cursor, this);
        recyclerView.setAdapter(newsItemAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase(); //initializing database objects
        cursor = DatabaseUtils.getAll(db);
        newsItemAdapter = new NewsItemAdapter(cursor, this);
        recyclerView.setAdapter(newsItemAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close(); // close database
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.search)
            load();
        return true;
    }

    // when loading, creates asyncTaskLoader to show loading bar and to refresh the articles, replcaed AsyncTask
    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                RefreshTasks.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }

    //after loading, load new newsItems from the database and update the recyclerview
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        progress.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        newsItemAdapter = new NewsItemAdapter(cursor, this);
        recyclerView.setAdapter(newsItemAdapter);
        newsItemAdapter.notifyDataSetChanged();
    }

    // this was left blank in the example
    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }

    //opens the article url when the item is clicked
    @Override
    public void onItemClick(Cursor cursor, int itemIndex) {
        cursor.moveToPosition(itemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s", url));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


    //not 100% sure what this is for. My best guess is it forces the mainActivity to reload
    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
    }

    //    class NetworkTask extends AsyncTask<URL, Void, ArrayList<NewsItem>>
//    {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progress.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected ArrayList<NewsItem> doInBackground(URL... params) {
//            ArrayList<NewsItem> result = null;
//            URL url = NetworkUtils.makeURL();
//            Log.d(TAG, "Url: " + url.toString());
//            try{
//                String json = NetworkUtils.getResponseFromHttpUrl(url);
//                result = NetworkUtils.parseJSON(json);
//
//            }catch (IOException e){
//                e.printStackTrace();
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(final ArrayList<NewsItem> data) {
//
//        }
//    }
}
