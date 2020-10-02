package com.example.movieandtvshowcatalogue.network;

import com.example.movieandtvshowcatalogue.model.Movie;
import com.example.movieandtvshowcatalogue.model.MovieRelease;
import com.example.movieandtvshowcatalogue.model.TVShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("discover/movie")
    Call<Movie> getListMovie(@Query("api_key") String apiKey);
    @GET("movie/{movie_id}?")
    Call<Movie> getDetailsMovie(@Path("movie_id") long movieId, @Query("api_key") String apiKey);

    @GET("discover/tv?")
    Call<TVShow> getListTVShow(@Query("api_key") String apiKey);
    @GET("tv/{tv_show_id}")
    Call<TVShow> getDetailsTVShow(@Path("tv_show_id") int tvShowID, @Query("api_key") String apiKey);

    @GET("search/movie/")
    Call<Movie> getSearchMovie(@Query("query") String query, @Query("api_key") String apiKey);
    @GET("search/tv")
    Call<TVShow> getSearchTVShow(@Query("query") String query, @Query("api_key") String apiKey);


    @GET("discover/movie")
    Call<MovieRelease> getMovieRelease(@Query("primary_release_date.gte") String curDate1, @Query("primary_release_date.lte") String curDate2, @Query("api_key") String apiKey);

    /*
    @GET("discover/movie?")
    Call<MovieRelease> getMovieRelease(@Query("api_key") String apiKey,@Query("primary_release_date.gte") String curDate1, @Query("primary_release_date.lte") String curDate2);
    */
}
