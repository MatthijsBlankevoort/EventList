package com.example.matthijsblankevoort.eventlist;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class MyEventListAdapter extends RecyclerView.Adapter<MyEventListAdapter.MyEventListViewHolder> {
    private List<EventEntity> mBucketListItems = new ArrayList<>();

    private final RequestManager glide;
    private Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyEventListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;
        public CardView cardView;

        public MyEventListViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.dateText);
            imageView = v.findViewById(R.id.coverImage);
            cardView = v.findViewById(R.id.card_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyEventListAdapter(List<EventEntity> myDataset, RequestManager glide, Context context) {
        this.glide = glide;
        this.mBucketListItems = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyEventListAdapter.MyEventListViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);
        MyEventListAdapter.MyEventListViewHolder vh = new MyEventListAdapter.MyEventListViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyEventListAdapter.MyEventListViewHolder holder, final int position) {
        holder.
                textView.setText(mBucketListItems.get(position).getName());
        this.glide.load(mBucketListItems.get(position).getImageUrl()).into(holder.imageView);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBucketListItems.size();
    }
}
