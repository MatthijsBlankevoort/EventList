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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        public TextView dateText;
        public TextView nameText;
        public ImageView imageView;
        public CardView cardView;

        public MyEventListViewHolder(View v) {
            super(v);
            dateText = v.findViewById(R.id.dateText);
            nameText = v.findViewById(R.id.nameText);
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
        holder.nameText.setText(mBucketListItems.get(position).getName());
        holder.dateText.setText(mBucketListItems.get(position).getDate());
        this.glide.load(mBucketListItems.get(position).getImageUrl()).into(holder.imageView);
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CharSequence text = mBucketListItems.get(position).getName() + " removed from your list!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                MainActivity.bucketListViewModel.delete(mBucketListItems.get(position));
                mBucketListItems.remove(position);
                notifyDataSetChanged();


                return true;
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBucketListItems.size();
    }
}
