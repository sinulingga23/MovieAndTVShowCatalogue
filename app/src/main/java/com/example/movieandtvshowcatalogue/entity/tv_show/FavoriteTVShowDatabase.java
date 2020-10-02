package com.example.movieandtvshowcatalogue.entity.tv_show;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = FavoriteTVShow.class, exportSchema = false, version = 1)
public abstract class FavoriteTVShowDatabase extends RoomDatabase {
    private static final String DB_NAME = "favoriteTVShow_db";
    private static FavoriteTVShowDatabase instance;

    public static synchronized FavoriteTVShowDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FavoriteTVShowDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract FavoriteTVShowDao favoriteTVShowDao();
}
