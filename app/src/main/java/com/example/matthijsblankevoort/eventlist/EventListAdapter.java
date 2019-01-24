package com.example.matthijsblankevoort.eventlist;

import android.arch.persistence.room.ColumnInfo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {
    private List<Event> mBucketListItems = new ArrayList<>();

    private final RequestManager glide;
    private Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameText;
        public TextView dateText;
        public ImageView imageView;
        public CardView cardView;

        public MyViewHolder(View v) {
            super(v);
            dateText = v.findViewById(R.id.dateText);
            nameText = v.findViewById(R.id.nameText);
            imageView = v.findViewById(R.id.coverImage);
            cardView = v.findViewById(R.id.card_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventListAdapter(List<Event> myDataset, RequestManager glide, Context context) {
        this.glide = glide;
        this.mBucketListItems = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.
                nameText.setText(mBucketListItems.get(position).getName());
        holder.dateText.setText(LocalDate.parse(mBucketListItems.get(position).getDates().getStart().getLocalDate()).format(DateTimeFormatter.ofPattern("dd MMM")));
//        Glide.with(EventListAdapter.this)
        this.glide.load(mBucketListItems.get(position).getImages().get(0).getUrl()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetailActivity.class);
                Bundle b = new Bundle();
                b.putString("id", mBucketListItems.get(position).getId()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.mBucketListItems.size();
    }
}

