package com.example.movieandtvshowcatalogue.details.details_movie;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.movieandtvshowcatalogue.R;
import com.example.movieandtvshowcatalogue.entity.movie.FavoriteMovie;
import com.example.movieandtvshowcatalogue.model.Movie;
import com.example.movieandtvshowcatalogue.preferences.FavoritePreference;
import com.example.movieandtvshowcatalogue.widget.movie.MovieBannerWidget;

import static com.example.movieandtvshowcatalogue.MainActivity.favoriteMovieDatabase;

public class MovieDetails extends AppCompatActivity {
    private static final String TAG = MovieDetails.class.getSimpleName();
    public static final String EXTRA_MOVIE = "extra_movie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        final Movie extraMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        final FavoritePreference favoritePreference = new FavoritePreference(getApplicationContext(), String.valueOf(extraMovie.getId()));
        TextView title = findViewById(R.id.tv_title_detail);
        ImageView imgPoster = findViewById(R.id.img_poster_detail);
        TextView releaseDate = findViewById(R.id.tv_release_date_detail);
        TextView rating = findViewById(R.id.tv_star);
        TextView overview = findViewById(R.id.tv_overview_detail);
        final ProgressBar pbLoadPoster = findViewById(R.id.pb_load_poster);
        final Button btnLike = findViewById(R.id.btn_favorite);

        if (extraMovie != null) {
            title.setText(extraMovie.getTitle());
            String urlPoster = "https://image.tmdb.org/t/p/w780" + extraMovie.getBackdropPath();

            Glide.with(getApplicationContext())
                    .load(urlPoster)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pbLoadPoster.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pbLoadPoster.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imgPoster);

            releaseDate.setText(extraMovie.getReleaseDate());
            rating.setText(String.valueOf(extraMovie.getVoteAverage()));
            overview.setText(extraMovie.getOverview());
        }

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteMovie favoriteMovie = new FavoriteMovie();
                favoriteMovie.setId(extraMovie.getId());
                favoriteMovie.setTitle(extraMovie.getTitle());
                favoriteMovie.setOverview(extraMovie.getOverview());
                favoriteMovie.setPosterPath(extraMovie.getPoster());
                favoriteMovie.setBackdropPath(extraMovie.getBackdropPath());
                favoriteMovie.setReleaseDate(extraMovie.getReleaseDate());
                favoriteMovie.setRuntime(extraMovie.getRuntime());
                favoriteMovie.setVoteAverage(extraMovie.getVoteAverage());


                String temp = "";
                if (favoritePreference.getFavorite()) {
                    insertToFavoriteMovie(favoriteMovie);
                    favoritePreference.setFavorite(false);
                    MovieBannerWidget.updateWidget(getApplicationContext());
                    btnLike.setText(getResources().getString(R.string.delete_from_favorite));
                    temp = getResources().getString(R.string.added_to_favorite);
                    Log.d(TAG, "getFavorite: " + favoritePreference.getFavorite());
                } else if (!favoritePreference.getFavorite()) {
                    deleteFromFavorite(favoriteMovie);
                    favoritePreference.setFavorite(true);
                    MovieBannerWidget.updateWidget(getApplicationContext());
                    btnLike.setText(getResources().getString(R.string.add_to_favorite));
                    temp = getResources().getString(R.string.deleted_from_favorite);
                    Log.d(TAG, "getFavorite: " + favoritePreference.getFavorite());
                }

                Toast.makeText(getBaseContext(), temp + " \"" + extraMovie.getTitle() + "\"", Toast.LENGTH_SHORT).show();
            }
        });

        if (favoritePreference.getFavorite()) {
            btnLike.setText(getResources().getString(R.string.add_to_favorite));
        } else if (!favoritePreference.getFavorite()) {
            btnLike.setText(getResources().getString(R.string.delete_from_favorite));
        }

    }

    private static void insertToFavoriteMovie(final FavoriteMovie favoriteMovie) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                favoriteMovieDatabase.favoriteMovieDao().insertFavoriteMovie(favoriteMovie);
                return null;
            }
        }.execute();
    }

    private static void deleteFromFavorite(final FavoriteMovie favoriteMovie) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                favoriteMovieDatabase.favoriteMovieDao().deleteFavoriteMovie(favoriteMovie);
                return null;
            }
        }.execute();
    }
}
