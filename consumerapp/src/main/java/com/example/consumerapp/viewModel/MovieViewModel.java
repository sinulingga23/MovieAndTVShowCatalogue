package com.example.consumerapp.viewModel;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.consumerapp.FavoriteMovie;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {
    private static final String TAG = MovieViewModel.class.getSimpleName();
    private MutableLiveData<List<FavoriteMovie>> listMovies = new MutableLiveData<>();
    private List<FavoriteMovie> listMovie = new ArrayList<>();

    public void setListMovies(Cursor cursor) {
        while (cursor.moveToNext()) {
            listMovie.add(converToFavMoive(cursor));
        }
        cursor.close();
        listMovies.postValue(listMovie);
    }

    public LiveData<List<FavoriteMovie>> getMovies() {
        return listMovies;
    }

    public FavoriteMovie converToFavMoive(Cursor cursor) {
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setId(cursor.getLong(cursor.getColumnIndex("id")));
        favoriteMovie.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        favoriteMovie.setPosterPath(cursor.getString(cursor.getColumnIndex("posterPath")));
        favoriteMovie.setBackdropPath(cursor.getString(cursor.getColumnIndex("backdropPath")));
        favoriteMovie.setReleaseDate(cursor.getString(cursor.getColumnIndex("releaseDate")));
        favoriteMovie.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
        favoriteMovie.setVoteAverage(cursor.getFloat(cursor.getColumnIndex("voteAverage")));
        favoriteMovie.setRuntime(cursor.getInt(cursor.getColumnIndex("runtime")));
        return favoriteMovie;
    }

}
