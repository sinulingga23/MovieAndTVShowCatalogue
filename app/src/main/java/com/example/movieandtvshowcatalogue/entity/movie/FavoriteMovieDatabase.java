package com.example.movieandtvshowcatalogue.entity.movie;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = FavoriteMovie.class, exportSchema = false, version = 1)
public abstract class FavoriteMovieDatabase extends RoomDatabase {
    private static final String DB_NAME = "favoriteMovie_db";
    private static FavoriteMovieDatabase instance;

    public static synchronized FavoriteMovieDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FavoriteMovieDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract FavoriteMovieDao favoriteMovieDao();
}
