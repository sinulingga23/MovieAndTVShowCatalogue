package com.example.consumerapp.viewModel;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.consumerapp.FavoriteTVShow;

import java.util.ArrayList;
import java.util.List;

public class TVShowViewModel extends ViewModel {
    private static final String TAG = TVShowViewModel.class.getSimpleName();
    private MutableLiveData<List<FavoriteTVShow>> listTVShow = new MutableLiveData<>();
    private List<FavoriteTVShow> list = new ArrayList<>();

    public void setListTVShow(Cursor cursor) {
        while (cursor.moveToNext()) {
            list.add(convertToFavTVShow(cursor));
        }
        cursor.close();
        listTVShow.postValue(list);
    }

    public LiveData<List<FavoriteTVShow>> getTVShow() {
        return listTVShow;
    }

    public FavoriteTVShow convertToFavTVShow(Cursor cursor) {
        FavoriteTVShow favoriteTVShow = new FavoriteTVShow();
        favoriteTVShow.setId(cursor.getLong(cursor.getColumnIndex("id")));
        favoriteTVShow.setName(cursor.getString(cursor.getColumnIndex("name")));
        favoriteTVShow.setPosterPath(cursor.getString(cursor.getColumnIndex("posterPath")));
        favoriteTVShow.setBackdropPathh(cursor.getString(cursor.getColumnIndex("backdropPath")));
        favoriteTVShow.setFirstAirDate(cursor.getString(cursor.getColumnIndex("firstAirDate")));
        favoriteTVShow.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
        favoriteTVShow.setVoteAverage(cursor.getFloat(cursor.getColumnIndex("voteAverage")));
        favoriteTVShow.setRuntime(cursor.getInt(cursor.getColumnIndex("runtime")));
        return favoriteTVShow;
    }

}
