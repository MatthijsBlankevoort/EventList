package com.example.matthijsblankevoort.eventlist;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.example.matthijsblankevoort.eventlist.MainActivity.bucketListViewModel;

public class MyEventListFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<EventEntity> eventList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
        if( bucketListViewModel.getBucketListItems().getValue() != null) {
            eventList = bucketListViewModel.getBucketListItems().getValue();
        }

        mRecyclerView = rootView.findViewById(R.id.my_events_recyclerView);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyEventListAdapter(eventList, Glide.with(this), getContext());
        mRecyclerView.setAdapter(mAdapter);

        bucketListViewModel.getBucketListItems().observe(this, new Observer<List<EventEntity>>() {
            @Override
            public void onChanged(@Nullable List<EventEntity> reminders) {
            eventList.clear();
            eventList.addAll(reminders);
            mAdapter.notifyDataSetChanged();
            }
        });


        return rootView;
    }

}
