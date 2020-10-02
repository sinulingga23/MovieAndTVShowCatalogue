package com.example.consumerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;

public class TVShow implements Parcelable {

    @SerializedName("id")
    private long id;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("name")
    private String name;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_average")
    private float voteAverage;

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



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }



    private ArrayList<TVShow> results;

    public void setResults(ArrayList<TVShow> results) {
        this.results = results;
    }

    public ArrayList<TVShow> getResults() {
        return  results;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public TVShow() {

    }
    public TVShow(JSONObject object) {
        try {
            String poster_path = object.getString("poster_path");
            String title = object.getString("name");
            String release_date = object.getString("first_air_date");
            String overview = object.getString("overview");

            this.poster = poster_path;
            this.name = title;
            this.firstAirDate = release_date;
            this.overview = overview;
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
        dest.writeString(this.backdropPath);
        dest.writeString(this.name);
        dest.writeInt(this.runtime);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.overview);
        dest.writeFloat(this.voteAverage);
        dest.writeTypedList(this.results);
    }

    protected TVShow(Parcel in) {
        this.id = in.readLong();
        this.poster = in.readString();
        this.backdropPath = in.readString();
        this.name = in.readString();
        this.runtime = in.readInt();
        this.firstAirDate = in.readString();
        this.overview = in.readString();
        this.voteAverage = in.readFloat();
        this.results = in.createTypedArrayList(TVShow.CREATOR);
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel source) {
            return new TVShow(source);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
}
