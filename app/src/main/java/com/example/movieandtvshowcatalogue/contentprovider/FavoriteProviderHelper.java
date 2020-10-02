package com.example.movieandtvshowcatalogue.contentprovider;

import android.database.Cursor;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.movieandtvshowcatalogue.entity.movie.FavoriteMovie;
import com.example.movieandtvshowcatalogue.entity.tv_show.FavoriteTVShow;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.movieandtvshowcatalogue.MainActivity.favoriteMovieDatabase;
import static com.example.movieandtvshowcatalogue.MainActivity.favoriteTVShowDatabase;

public class FavoriteProviderHelper {

    public static class GetLiveDataFavMovie extends AsyncTask<Void, Void, LiveData<List<FavoriteMovie>>> {
        @Override
        protected LiveData<List<FavoriteMovie>> doInBackground(Void... voids) {
            return favoriteMovieDatabase.favoriteMovieDao().getFavMovieList();
        }
    }

    public LiveData<List<FavoriteMovie>> getLiveDataFavMovie() {
        try {
            return new GetLiveDataFavMovie().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static class GetLiveDataTVShow extends AsyncTask<Void, Void, LiveData<List<FavoriteTVShow>>> {
        @Override
        protected LiveData<List<FavoriteTVShow>> doInBackground(Void... voids) {
            return favoriteTVShowDatabase.favoriteTVShowDao().getFavTVShowList();
        }
    }

    public LiveData<List<FavoriteTVShow>> getLiveDataTVShow() {
        try {
            return new GetLiveDataTVShow().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class GetCursorFavMovie extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return favoriteMovieDatabase.favoriteMovieDao().getCursorFavMovie();
        }
    }

    public Cursor getCursorFavMovie() {
        try {
            return new GetCursorFavMovie().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class GetCursorFavTVShow extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return favoriteTVShowDatabase.favoriteTVShowDao().getCursorFavTVShow();
        }
    }

    public Cursor getCursorFavTVShow() {
        try {
            return new GetCursorFavTVShow().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class InsertFavMovieCursor extends AsyncTask<FavoriteMovie, Void, Long> {
        @Override
        protected Long doInBackground(FavoriteMovie... favoriteMovies) {
            return favoriteMovieDatabase.favoriteMovieDao().insertMovieToCursor(favoriteMovies[0]);
        }
    }

    public long insertMovieCursor(FavoriteMovie favoriteMovie) {
        try {
            return new InsertFavMovieCursor().execute(favoriteMovie).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static class InsertFavTVShowCursor extends AsyncTask<FavoriteTVShow, Void, Long> {
        @Override
        protected Long doInBackground(FavoriteTVShow... favoriteTVShows) {
            return favoriteTVShowDatabase.favoriteTVShowDao().insertTVShowCursor(favoriteTVShows[0]);
        }
    }

    public long insertTVShowCursor(FavoriteTVShow favoriteTVShow) {
        try {
            return new InsertFavTVShowCursor().execute(favoriteTVShow).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
