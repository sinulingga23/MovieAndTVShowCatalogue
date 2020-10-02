package com.example.movieandtvshowcatalogue.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieandtvshowcatalogue.model.Movie;
import com.example.movieandtvshowcatalogue.network.ApiClient;
import com.example.movieandtvshowcatalogue.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private static final String TAG = MovieViewModel.class.getSimpleName();
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public void setListMovies(String query) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call;
        if (query != null) {
            call = apiInterface.getSearchMovie(query, new ApiClient().getAPiClient());
        } else {
            call = apiInterface.getListMovie(new ApiClient().getAPiClient());
        }


        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> responseBody) {
                if (responseBody.isSuccessful()) {
                    ArrayList<Movie> movies = responseBody.body().getResults();
//                    listMovies.setValue(responseBody.body().getResults());
                    listMovies.setValue(movies);
                    for (int i=0; i<responseBody.body().getResults().size(); i++) {
                        Log.d(TAG, "Tes-> " + responseBody.body().getResults().get(i).getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }
}
