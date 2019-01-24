package com.example.matthijsblankevoort.eventlist;
import android.app.usage.NetworkStats;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventListRepository {
    private EventDatabase bucketListDatabase;
    private EventDAO bucketListItemDAO;
    private LiveData<List<EventEntity>>     bucketListItems;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EventListRepository(Context context) {
        bucketListDatabase = EventDatabase.getBucketListDatabase(context);
        bucketListItemDAO = bucketListDatabase.BucketListItemDao();
        bucketListItems = bucketListItemDAO.getAllBucketListItems();
    }

    public LiveData<List<EventEntity>> getBucketListItems() {
        return bucketListItems;
    }

    public void insert(final EventEntity bucketListItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bucketListItemDAO.insert(bucketListItem);
            }
        });
    }

    public void update(final EventEntity bucketListItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bucketListItemDAO.update(bucketListItem);
            }
        });
    }

    public void delete(final EventEntity bucketListItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bucketListItemDAO.delete(bucketListItem);
            }
        });
    }
}
