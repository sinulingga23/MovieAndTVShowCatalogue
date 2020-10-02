package com.example.movieandtvshowcatalogue.widget.tv_show;

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
import com.example.movieandtvshowcatalogue.entity.tv_show.FavoriteTVShow;
import com.example.movieandtvshowcatalogue.widget.movie.MovieBannerWidget;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.movieandtvshowcatalogue.MainActivity.favoriteTVShowDatabase;

public class TVShowStackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = TVShowStackRemoteViewsFactory.class.getSimpleName();
    private List<FavoriteTVShow> tvShowWidgetItems;
    private Context context;

    public TVShowStackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        tvShowWidgetItems = getListFavoriteTVShow();
        Log.d(TAG, "onCreate: " + tvShowWidgetItems.size());
    }

    @Override
    public void onDataSetChanged() {
        if (tvShowWidgetItems != null) {
            tvShowWidgetItems = getListFavoriteTVShow();
        } else {
            tvShowWidgetItems = getListFavoriteTVShow();
        }
        Log.d(TAG, "onDataSetChanged: " + tvShowWidgetItems.size());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return tvShowWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item_tv_show);

        if (tvShowWidgetItems.size() > 0) {
            String urlPoster = "https://image.tmdb.org/t/p/w780/" + tvShowWidgetItems.get(position).getPosterPath();
            Log.d(TAG, "getViewAt: " + tvShowWidgetItems.size());
            try {
                Bitmap bitmap = Glide.with(context)
                        .asBitmap()
                        .load(urlPoster)
                        .submit(200,200)
                        .get();
                remoteViews.setImageViewBitmap(R.id.image_widget_item_tv_show, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Bundle extras = new Bundle();
            extras.putString(MovieBannerWidget.EXTRA_ITEM, tvShowWidgetItems.get(position).getName());
            Intent fillIntent = new Intent();
            fillIntent.putExtras(extras);

            remoteViews.setOnClickFillInIntent(R.id.image_widget_item_tv_show, fillIntent);
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

    private List<FavoriteTVShow> getListFavoriteTVShow() {
        try {
            return new GetList().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetList extends AsyncTask<Void, Void, List<FavoriteTVShow>> {
        @Override
        protected List<FavoriteTVShow> doInBackground(Void... voids) {
            return favoriteTVShowDatabase.favoriteTVShowDao().getFavoriteTVShowList();
        }
    }
}
