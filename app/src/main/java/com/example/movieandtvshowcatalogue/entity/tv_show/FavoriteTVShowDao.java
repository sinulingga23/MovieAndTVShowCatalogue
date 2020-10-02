package com.example.movieandtvshowcatalogue.entity.tv_show;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteTVShowDao {
    @Query("SELECT * FROM favoriteTVShow")
    List<FavoriteTVShow> getFavoriteTVShowList();

    @Query("SELECT * FROM favoriteTVShow")
    LiveData<List<FavoriteTVShow>> getFavTVShowList();

    @Query("SELECT * FROM favoriteTVShow")
    Cursor getCursorFavTVShow();

    @Insert
    long insertTVShowCursor(FavoriteTVShow favoriteTVShow);

    @Insert
    void insertFavoriteTVShow(FavoriteTVShow favoriteTVShow);

    @Delete
    void deleteFavoriteTVShow(FavoriteTVShow favoriteTVShow);
}
