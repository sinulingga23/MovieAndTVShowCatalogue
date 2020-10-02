//package com.example.consumerapp.details.details_tv;
//
//import android.util.Log;
//
//import com.example.consumerapp.model.TVShow;
//import com.example.consumerapp.network.ApiClient;
//import com.example.consumerapp.network.ApiInterface;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class TVShowDetailsModel {
//    private static final String TAG = TVShowDetailsModel.class.getSimpleName();
//
//    public void setDetailsTVShow(int tvShowId, final TVShowDetailsCallback tvShowDetailsCallback) {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<TVShow> call = apiInterface.getDetailsTVShow(tvShowId, new ApiClient().getAPiClient());
//        Log.d(TAG, "IDnya: " + tvShowId);
//        call.enqueue(new Callback<TVShow>() {
//            @Override
//            public void onResponse(Call<TVShow> call, Response<TVShow> responseBody) {
//                if (responseBody.isSuccessful()) {
//                    TVShow tvShow = responseBody.body();
//                    tvShowDetailsCallback.onSuccess(tvShow);
//                    Log.d(TAG, "Tes-> " + responseBody.body().getPoster());
//                    Log.d(TAG, "Tes-> " + responseBody.toString());
//                    Log.d(TAG, "Tes-> " + responseBody.body().getOverview());
//                } else {
//                    Log.d(TAG, "Gagal");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TVShow> call, Throwable t) {
//                Log.d(TAG, t.toString());
//                tvShowDetailsCallback.onFailed(t);
//            }
//        });
//    }
//
//    public interface TVShowDetailsCallback {
//        void onSuccess(TVShow tvShow);
//        void onFailed(Throwable t);
//    }
//}
