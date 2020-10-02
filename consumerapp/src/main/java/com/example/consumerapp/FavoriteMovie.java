package com.example.consumerapp;

import android.content.ContentValues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favoriteMovie")
public class FavoriteMovie {
    @PrimaryKey
    private long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "posterPath")
    private String posterPath;

    @ColumnInfo(name = "backdropPath")
    private String backdropPath;

    @ColumnInfo(name = "releaseDate")
    private String releaseDate;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "voteAverage")
    private float voteAverage;

    @ColumnInfo(name = "runtime")
    private int runtime;


    public FavoriteMovie() {

    }

    public static FavoriteMovie contentValuesToFavMovie(ContentValues contentValues) {
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        if (contentValues.containsKey("id")) favoriteMovie.setId(contentValues.getAsLong("id"));
        if (contentValues.containsKey("title")) favoriteMovie.setTitle(contentValues.getAsString("title"));
        if (contentValues.containsKey("posterPath")) favoriteMovie.setPosterPath(contentValues.getAsString("posterPath"));
        if (contentValues.containsKey("backdropPath")) favoriteMovie.setBackdropPath(contentValues.getAsString("backdropPath"));
        if (contentValues.containsKey("releaseDate")) favoriteMovie.setReleaseDate(contentValues.getAsString("releaseDate"));
        if (contentValues.containsKey("overview")) favoriteMovie.setOverview(contentValues.getAsString("overview"));
        if (contentValues.containsKey("voteAverage")) favoriteMovie.setVoteAverage(contentValues.getAsFloat("voteAverage"));
        if (contentValues.containsKey("runtime")) favoriteMovie.setRuntime(contentValues.getAsInteger("runtime"));
        return favoriteMovie;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
}
