package com.example.movieandtvshowcatalogue.entity.tv_show;

import android.content.ContentValues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.movieandtvshowcatalogue.entity.movie.FavoriteMovie;

@Entity(tableName = "favoriteTVShow")
public class FavoriteTVShow {
    @PrimaryKey
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "posterPath")
    private String posterPath;

    @ColumnInfo(name = "backdropPath")
    private String backdropPathh;

    @ColumnInfo(name = "firstAirDate")
    private String firstAirDate;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "voteAverage")
    private float voteAverage;

    @ColumnInfo(name = "runtime")
    private int runtime;

    public FavoriteTVShow() {

    }

    public static FavoriteTVShow contentValuesToFavTVShow(ContentValues contentValues) {
        FavoriteTVShow favoriteTVShow = new FavoriteTVShow();
        if (contentValues.containsKey("id")) favoriteTVShow.setId(contentValues.getAsLong("id"));
        if (contentValues.containsKey("name")) favoriteTVShow.setName(contentValues.getAsString("name"));
        if (contentValues.containsKey("posterPath")) favoriteTVShow.setPosterPath(contentValues.getAsString("posterPath"));
        if (contentValues.containsKey("backdropPath")) favoriteTVShow.setBackdropPathh(contentValues.getAsString("backdropPath"));
        if (contentValues.containsKey("firstAirDate")) favoriteTVShow.setFirstAirDate(contentValues.getAsString("firstAirDate"));
        if (contentValues.containsKey("overview")) favoriteTVShow.setOverview(contentValues.getAsString("overview"));
        if (contentValues.containsKey("voteAverage")) favoriteTVShow.setVoteAverage(contentValues.getAsFloat("voteAverage"));
        if (contentValues.containsKey("runtime")) favoriteTVShow.setRuntime(contentValues.getAsInteger("runtime"));
        return favoriteTVShow;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPathh() {
        return backdropPathh;
    }

    public void setBackdropPathh(String backdropPathh) {
        this.backdropPathh = backdropPathh;
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
