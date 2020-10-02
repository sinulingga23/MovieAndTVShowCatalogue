package com.example.movieandtvshowcatalogue.widget.movie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.movieandtvshowcatalogue.R;
import com.example.movieandtvshowcatalogue.entity.movie.FavoriteMovie;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.movieandtvshowcatalogue.MainActivity.favoriteMovieDatabase;

public class MovieStackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = MovieStackRemoteViewsFactory.class.getSimpleName();
    private List<FavoriteMovie> movieWidgetItems;
    private Context context;

    public MovieStackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        movieWidgetItems = getListFavoriteMovie();
//        Log.d(TAG, "onCreate: " + movieWidgetItems.size());
    }

    @Override
    public void onDataSetChanged() {
        if (movieWidgetItems != null) {
            movieWidgetItems = getListFavoriteMovie();
        } else {
            movieWidgetItems = getListFavoriteMovie();
        }
//        Log.d(TAG, "onDataSetChanged: " + movieWidgetItems.size());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item_movie);

        if (movieWidgetItems.size() > 0) {
//            String urlPoster = "https://image.tmdb.org/t/p/w780/" + movieWidgetItems.get(position).getPosterPath();
            String urlPoster = "https://image.tmdb.org/t/p/w780/";
            if (movieWidgetItems.get(position).getPosterPath() == null) {
                urlPoster += movieWidgetItems.get(position).getBackdropPath();
            } else {
                urlPoster += movieWidgetItems.get(position).getPosterPath();
            }

            Log.d(TAG, "getViewAt: " + movieWidgetItems.size());
            try {
                Bitmap bitmap = Glide.with(context)
                        .asBitmap()
                        .load(urlPoster)
                        .submit(200,200)
                        .get();
                remoteViews.setImageViewBitmap(R.id.image_widget_item_movie, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Bundle extras = new Bundle();
            extras.putString(MovieBannerWidget.EXTRA_ITEM, movieWidgetItems.get(position).getTitle());
            Intent fillIntent = new Intent();
            fillIntent.putExtras(extras);

            remoteViews.setOnClickFillInIntent(R.id.image_widget_item_movie, fillIntent);
            return remoteViews;
        } else {
            return null;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private List<FavoriteMovie> getListFavoriteMovie() {
        try {
            return new GetList().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetList extends AsyncTask<Void, Void, List<FavoriteMovie>> {
        @Override
        protected List<FavoriteMovie> doInBackground(Void... voids) {
            return favoriteMovieDatabase.favoriteMovieDao().getFavoriteMovieList();
        }
    }
}
