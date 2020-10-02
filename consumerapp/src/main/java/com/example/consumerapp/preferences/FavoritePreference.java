package com.example.consumerapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class FavoritePreference {
//    private static final String PREFS_NAME = "FavoritePreference";
    private static final String CHECK_FAVORITE = "check_favorite";
    private static final String ID_MOVIE_IN_TABLE = "id_movie";
    private SharedPreferences preferences;

    public FavoritePreference(Context context, String prefsName) {
        preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
    }

    public Boolean getFavorite() {
        return  preferences.getBoolean(CHECK_FAVORITE, true);
    }

    public void setFavorite(Boolean b) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CHECK_FAVORITE, b);
        editor.apply();
    }

    public long getIdMovieInTable() {
        return preferences.getLong(ID_MOVIE_IN_TABLE,-1);
    }

    public void setIdMovieInTable(long id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(ID_MOVIE_IN_TABLE, id);
        editor.apply();
    }


}
