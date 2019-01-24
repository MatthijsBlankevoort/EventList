package com.example.matthijsblankevoort.eventlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class EventListViewModel extends ViewModel {
    private EventListRepository eventListRepository;
    private LiveData<List<EventEntity>> bucketListItems;

    public EventListViewModel(Context context) {
        eventListRepository = new EventListRepository(context);
        bucketListItems = eventListRepository.getBucketListItems();
    }

    public LiveData<List<EventEntity>> getBucketListItems() {
        return bucketListItems;
    }

    public void insert(EventEntity bucketListItem) {
        eventListRepository.insert(bucketListItem);
    }

    public void update(EventEntity bucketListItem) {
        eventListRepository.update(bucketListItem);
    }

    public void delete(EventEntity bucketListItem) {
        eventListRepository.delete(bucketListItem);
    }
}
