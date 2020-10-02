package com.example.consumerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;


public class Movie implements Parcelable {


    @SerializedName("id")
    private long id;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private float voteAverage;

//    FavoritePreference favoritePreference;

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

//    public FavoritePreference getFavoritePreference() {
//        return favoritePreference;
//    }
//
//    public void setFavoritePreference(FavoritePreference favoritePreference) {
//        this.favoritePreference = favoritePreference;
//    }



    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }



    private ArrayList<Movie> results;

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public Movie() {

    }
    public Movie(JSONObject object) {
        try {
            String poster_path = object.getString("poster_path");
            String title = object.getString("title");
            String release_date = object.getString("release_date");
            String overview = object.getString("overview");
            int id = object.getInt("id");

            this.poster = poster_path;
            this.title  = title;
            this.releaseDate = release_date;
            this.overview = overview;
            this.id = id;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeInt(this.runtime);
        dest.writeString(this.backdropPath);
        dest.writeFloat(this.voteAverage);
        dest.writeTypedList(this.results);
    }

    protected Movie(Parcel in) {
        this.id = in.readLong();
        this.poster = in.readString();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.runtime = in.readInt();
        this.backdropPath = in.readString();
        this.voteAverage = in.readFloat();
        this.results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
