package com.example.movieandtvshowcatalogue.entity.movie;


import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM favoriteMovie")
    List<FavoriteMovie> getFavoriteMovieList();

    @Query("SELECT * FROM favoriteMovie")
    LiveData<List<FavoriteMovie>> getFavMovieList();

    @Query("SELECT * FROM favoriteMovie")
    Cursor getCursorFavMovie();

    @Insert
    long insertMovieToCursor(FavoriteMovie favoriteMovie);

    @Insert
    void insertFavoriteMovie(FavoriteMovie movie);

    @Delete
    void deleteFavoriteMovie(FavoriteMovie movie);

}
