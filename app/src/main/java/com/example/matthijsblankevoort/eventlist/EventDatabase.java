package com.example.matthijsblankevoort.eventlist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {EventEntity.class}, version = 1)
public abstract class EventDatabase extends RoomDatabase {

    private static EventDatabase INSTANCE;

    public abstract EventDAO BucketListItemDao();

    public static EventDatabase getBucketListDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), EventDatabase.class, "bucketlist-database")
                            // allow quer
                            // ies on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }
}