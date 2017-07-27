package com.example.evan.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evan.newsapp.data.Contract;
import com.example.evan.newsapp.data.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Evan on 6/26/2017.
 */

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsItemViewHolder> {


    private ItemClickListener listener;
    private Cursor cursor;
    private Context context;
    public static final String TAG = "newsItemAdapter";

    //updated the adapter to have cursors for the database instead of an arraylist of data
    public NewsItemAdapter(Cursor cursor, ItemClickListener listener)
    {
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface ItemClickListener{
        void onItemClick(Cursor cursor, int itemIndex);
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int list_item_id = R.layout.articles_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(list_item_id, parent, false);
        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    //updated with cursor instead of getting from network
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView newsItemTextViewTitle;
        public final TextView newsItemTextViewDescription;
        public final TextView newsItemTextViewTime;
        public final ImageView newsItemImageViewImage;

        public NewsItemViewHolder(View view)
        {
            super(view);
            newsItemTextViewTitle = (TextView) view.findViewById(R.id.article_title);
            newsItemTextViewDescription = (TextView) view.findViewById(R.id.article_description);
            newsItemTextViewTime = (TextView) view.findViewById(R.id.article_time);
            newsItemImageViewImage = (ImageView) view.findViewById(R.id.article_image);
            view.setOnClickListener(this);
        }

        //binds the views with the database columns
        public void bind(int index)
        {
            cursor.moveToPosition(index);
            newsItemTextViewTitle.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_TITLE)));
            newsItemTextViewDescription.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_DESCRIPTION)));
            newsItemTextViewTime.setText((new java.util.Date()).toString());
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ITEMS.COLUMN_NAME_URLTOIMAGE));
            Log.d(TAG, "urlToImage: " + url);
            if (url != null){
                Picasso.with(context) //adding image using picasso
                        .load(url)
                        .into(newsItemImageViewImage);
            }

        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            listener.onItemClick(cursor, index);
        }
    }
}
