
package com.example.movieandtvshowcatalogue.favorite.favorite_movie;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandtvshowcatalogue.R;
import com.example.movieandtvshowcatalogue.adapter.movie_adapter.FavoriteMovieAdapter;
import com.example.movieandtvshowcatalogue.entity.movie.FavoriteMovie;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.example.movieandtvshowcatalogue.MainActivity.favoriteMovieDatabase;

public class FavoriteMovieActivity extends AppCompatActivity implements FavMovieListCallback{
    private FavoriteMovieAdapter favoriteMovieAdapter;
    private RecyclerView rvFavoriteMovie;
    private List<FavoriteMovie> favoriteMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        rvFavoriteMovie = findViewById(R.id.rv_favorite_movie);
        rvFavoriteMovie.setLayoutManager(new LinearLayoutManager(this));
        favoriteMovies = null;
        FavoriteMovieAsync favoriteMovieAsync = new FavoriteMovieAsync(this);
        favoriteMovieAsync.execute(favoriteMovies);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.list_of_favorite_movie));
        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(List<FavoriteMovie> favoriteMovies) {
        if (favoriteMovies != null) {
            this.favoriteMovies = favoriteMovies;
        }
        favoriteMovieAdapter = new FavoriteMovieAdapter(this, favoriteMovies);
        rvFavoriteMovie.setAdapter(favoriteMovieAdapter);
    }

    private static class FavoriteMovieAsync extends AsyncTask<List<FavoriteMovie>,Void, List<FavoriteMovie>> {
        WeakReference<FavMovieListCallback> myCallback;
        FavoriteMovieAsync(FavMovieListCallback favMovieListCallback) {
            this.myCallback = new WeakReference<>(favMovieListCallback);
        }

        @Override
        protected List<FavoriteMovie> doInBackground(List<FavoriteMovie>... lists) {
            try {
                lists[0] = favoriteMovieDatabase.favoriteMovieDao().getFavoriteMovieList();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lists[0];
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            FavMovieListCallback myCallback = this.myCallback.get();
            if (myCallback != null) {
                myCallback.onPreExecute();
            }
        }


        @Override
        protected void onPostExecute(List<FavoriteMovie> favoriteMovies) {
            super.onPostExecute(favoriteMovies);

            FavMovieListCallback myCallback = this.myCallback.get();
            if (myCallback != null) {
                myCallback.onPostExecute(favoriteMovies);
            }
        }
    }
}


interface FavMovieListCallback {
    void onPreExecute();
    void onPostExecute(List<FavoriteMovie> favoriteMovies);
}
