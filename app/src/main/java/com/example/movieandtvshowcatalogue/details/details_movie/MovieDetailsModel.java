package com.example.movieandtvshowcatalogue.details.details_movie;

import android.util.Log;

import com.example.movieandtvshowcatalogue.model.Movie;
import com.example.movieandtvshowcatalogue.network.ApiClient;
import com.example.movieandtvshowcatalogue.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsModel {
    private static final String TAG = MovieDetailsModel.class.getSimpleName();

    public void setDetailsMode(final long movieId, final MovieDetailsCallback movieDetailsCallback) {
        ApiInterface apiInterface  = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiInterface.getDetailsMovie(movieId, new ApiClient().getAPiClient()
        );
        Log.d(TAG, "IDnya: " + movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> responseBody) {
                if (responseBody.isSuccessful()) {
                    Movie movie = responseBody.body();
                    movieDetailsCallback.onSuccess(movie);
                    Log.d(TAG, "Tes-> "+ responseBody.body().getPoster());
                    Log.d(TAG, "Tes-> "+ responseBody.toString());
                    Log.d(TAG, "Tes-> "+ responseBody.body().getOverview());
                } else {
                    Log.d(TAG, "Gagal");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d(TAG, t.toString());
                movieDetailsCallback.onFailed(t);
            }


        });
    }

    public interface MovieDetailsCallback {
        void onSuccess(Movie movie);
        void onFailed(Throwable t);
    }
}