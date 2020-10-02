package com.example.movieandtvshowcatalogue.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieandtvshowcatalogue.model.TVShow;
import com.example.movieandtvshowcatalogue.network.ApiClient;
import com.example.movieandtvshowcatalogue.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowViewModel extends ViewModel {
    private static final String TAG = TVShowViewModel.class.getSimpleName();
    private MutableLiveData<ArrayList<TVShow>> listTVShow = new MutableLiveData<>();

    public void setListTVShow(String query) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TVShow> call;
        if (query != null) {
            call = apiInterface.getSearchTVShow(query, new ApiClient().getAPiClient());
        } else {
            call = apiInterface.getListTVShow(new ApiClient().getAPiClient());
        }

        call.enqueue(new Callback<TVShow>() {
            @Override
            public void onResponse(Call<TVShow> call, Response<TVShow> responseBody) {
                if (responseBody.isSuccessful()) {
                    listTVShow.setValue(responseBody.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<TVShow> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    public LiveData<ArrayList<TVShow>> getTVShow() {
        return listTVShow;
    }
}
