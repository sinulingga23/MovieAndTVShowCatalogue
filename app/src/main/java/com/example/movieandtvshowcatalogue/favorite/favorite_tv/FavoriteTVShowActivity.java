package com.example.movieandtvshowcatalogue.favorite.favorite_tv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.movieandtvshowcatalogue.R;
import com.example.movieandtvshowcatalogue.adapter.tv_adapter.FavoriteTVShowAdapter;
import com.example.movieandtvshowcatalogue.entity.tv_show.FavoriteTVShow;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.example.movieandtvshowcatalogue.MainActivity.favoriteTVShowDatabase;

public class FavoriteTVShowActivity extends AppCompatActivity implements FavTVShowCallback{
    private FavoriteTVShowAdapter favoriteTVShowAdapter;
    private RecyclerView rvFavoriteTVShow;
    private List<FavoriteTVShow> favoriteTVShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_tvshow);

        rvFavoriteTVShow = findViewById(R.id.rv_favorite_tv_show);
        rvFavoriteTVShow.setLayoutManager(new LinearLayoutManager(this));
        favoriteTVShowList = null;
        FavoriteTVShowAsync favoriteTVShowAsync = new FavoriteTVShowAsync(this);
        favoriteTVShowAsync.execute(favoriteTVShowList);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.list_of_favorite_tv_show));
        }

    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(List<FavoriteTVShow> favoriteTVShows) {
        if (favoriteTVShowList != null) {
            this.favoriteTVShowList = favoriteTVShows;
        }
        favoriteTVShowAdapter = new FavoriteTVShowAdapter(this, favoriteTVShows);
        rvFavoriteTVShow.setAdapter(favoriteTVShowAdapter);
    }

    private static class FavoriteTVShowAsync extends AsyncTask<List<FavoriteTVShow>, Void, List<FavoriteTVShow>> {
        WeakReference<FavTVShowCallback> myCallback;
        FavoriteTVShowAsync(FavTVShowCallback favTVShowCallback) {
            this.myCallback = new WeakReference<>(favTVShowCallback);
        }

        @Override
        protected List<FavoriteTVShow> doInBackground(List<FavoriteTVShow>... lists) {
            try {
                lists[0] = favoriteTVShowDatabase.favoriteTVShowDao().getFavoriteTVShowList();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lists[0];
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            FavTVShowCallback myCallback = this.myCallback.get();
            if (myCallback != null) {
                myCallback.onPreExecute();
            }
        }

        @Override
        protected void onPostExecute(List<FavoriteTVShow> favoriteTVShows) {
            super.onPostExecute(favoriteTVShows);

            FavTVShowCallback myCallback = this.myCallback.get();
            if (myCallback != null) {
                myCallback.onPostExecute(favoriteTVShows);
            }
        }
    }
}

interface FavTVShowCallback {
    void onPreExecute();
    void onPostExecute(List<FavoriteTVShow> favoriteTVShows);
}
