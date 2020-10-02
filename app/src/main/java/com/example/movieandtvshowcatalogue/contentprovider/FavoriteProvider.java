package com.example.movieandtvshowcatalogue.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.example.movieandtvshowcatalogue.entity.movie.FavoriteMovie;
import com.example.movieandtvshowcatalogue.entity.movie.FavoriteMovieDatabase;
import com.example.movieandtvshowcatalogue.entity.tv_show.FavoriteTVShow;
import com.example.movieandtvshowcatalogue.entity.tv_show.FavoriteTVShowDatabase;

import java.util.List;

public class FavoriteProvider extends ContentProvider {
    private static final String AUHTORITY = "com.example.movieandtvshowcatalogue";
    private static final String SCHEME = "content";
    private static final String TABLE_FAVORTIE_MOVIE = "favoriteMovie";
    private static final String TABLE_FAVORITE_TV_SHOW = "favoriteTVShow";
    public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME).authority(AUHTORITY).appendPath(TABLE_FAVORTIE_MOVIE).build();
    public static final Uri CONTENT_URI_TV_SHOW = new Uri.Builder().scheme(SCHEME).authority(AUHTORITY).appendPath(TABLE_FAVORITE_TV_SHOW).build();

    private FavoriteMovieDatabase favoriteMovieDatabase;
    private FavoriteTVShowDatabase favoriteTVShowDatabase;

    private static final int ACCESS_MOVIE = 1;
    private static final int ACCESS_TV_SHOW = 2;
    private static final UriMatcher sUriMathcer = new UriMatcher(UriMatcher.NO_MATCH);

    private LiveData<List<FavoriteMovie>> favoriteMovie;
    private LiveData<List<FavoriteTVShow>> favoriteTVShow;

    private FavoriteProviderHelper providerHelper = new FavoriteProviderHelper();

    public FavoriteProvider() {

    }

    static  {
        sUriMathcer.addURI(AUHTORITY, TABLE_FAVORTIE_MOVIE, ACCESS_MOVIE);
        sUriMathcer.addURI(AUHTORITY, TABLE_FAVORITE_TV_SHOW, ACCESS_TV_SHOW);
    }


    @Override
    public boolean onCreate() {
//        favoriteMovie = providerHelper.getLiveDataFavMovie();
//        favoriteTVShow = providerHelper.getLiveDataTVShow();
        favoriteMovieDatabase = FavoriteMovieDatabase.getInstance(getContext());
        favoriteTVShowDatabase = FavoriteTVShowDatabase.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursorFavorite = null;
        int accessTable = sUriMathcer.match(uri);
        switch (accessTable) {
            case ACCESS_MOVIE:
//                cursorFavorite = providerHelper.getCursorFavMovie();
                cursorFavorite = favoriteMovieDatabase.favoriteMovieDao().getCursorFavMovie();
                break;
            case ACCESS_TV_SHOW:
//                cursorFavorite = providerHelper.getCursorFavTVShow();
                cursorFavorite = favoriteTVShowDatabase.favoriteTVShowDao().getCursorFavTVShow();
                break;
        }
        return cursorFavorite;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long add = 0;
        int accessTable = sUriMathcer.match(uri);

        Context context = getContext();
        switch (accessTable) {
            case ACCESS_MOVIE:
//                add = providerHelper.insertMovieCursor(FavoriteMovie.contentValuesToFavMovie(values));
                add = favoriteMovieDatabase.favoriteMovieDao().insertMovieToCursor(FavoriteMovie.contentValuesToFavMovie(values));
                assert context != null;
                context.getContentResolver().notifyChange(CONTENT_URI_MOVIE, null);
                return ContentUris.withAppendedId(uri, add);
            case ACCESS_TV_SHOW:
//                add = providerHelper.insertTVShowCursor(FavoriteTVShow.contentValuesToFavTVShow(values));
                add = favoriteTVShowDatabase.favoriteTVShowDao().insertTVShowCursor(FavoriteTVShow.contentValuesToFavTVShow(values));
                assert context != null;
                context.getContentResolver().notifyChange(CONTENT_URI_TV_SHOW, null);
                return ContentUris.withAppendedId(uri, add);
        }
        return null;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
