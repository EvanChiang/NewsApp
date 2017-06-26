package com.example.evan.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Evan on 6/26/2017.
 */

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsItemViewHolder> {

    private String[] newsItemData;

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int list_item_id = R.layout.articles_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(list_item_id, parent, false);
        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        String text = newsItemData[position];
        holder.newsItemTextViewTitle.setText(text);
    }

    @Override
    public int getItemCount() {
        if (newsItemData == null)
            return 0;
        else
            return newsItemData.length;
    }

    public void setNewsItemData(String[] data)
    {
        newsItemData = data;
        notifyDataSetChanged();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {

        public final TextView newsItemTextViewTitle;
        public final TextView newsItemTextViewDescription;
        public final TextView newsItemTextViewTime;

        public NewsItemViewHolder(View view)
        {
            super(view);
            newsItemTextViewTitle = (TextView) view.findViewById(R.id.article_title);
            newsItemTextViewDescription = (TextView) view.findViewById(R.id.article_description);
            newsItemTextViewTime = (TextView) view.findViewById(R.id.article_time);
        }
    }
}
