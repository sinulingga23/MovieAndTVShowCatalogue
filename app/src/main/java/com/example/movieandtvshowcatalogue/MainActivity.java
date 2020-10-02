package com.example.movieandtvshowcatalogue;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.movieandtvshowcatalogue.entity.movie.FavoriteMovieDatabase;
import com.example.movieandtvshowcatalogue.entity.tv_show.FavoriteTVShowDatabase;
import com.example.movieandtvshowcatalogue.favorite.favorite_movie.FavoriteMovieActivity;
import com.example.movieandtvshowcatalogue.favorite.favorite_tv.FavoriteTVShowActivity;
import com.example.movieandtvshowcatalogue.fragment.MovieFragment;
import com.example.movieandtvshowcatalogue.fragment.TVShowFragment;
import com.example.movieandtvshowcatalogue.notification.SettingsReminder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static FavoriteMovieDatabase favoriteMovieDatabase;
    public static FavoriteTVShowDatabase favoriteTVShowDatabase;
    private String cek = "";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_main, new MovieFragment(), MovieFragment.class.getSimpleName())
                            .commit();
                    cek = "movie";
                    setTitle(R.string.movie_catalogue);
                    return true;
                case R.id.navigation_tv:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_main, new TVShowFragment(), TVShowFragment.class.getSimpleName())
                            .commit();
                    cek = "tv";
                    setTitle(R.string.tv_show);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_movie);
        }

        favoriteMovieDatabase = FavoriteMovieDatabase.getInstance(this);
        favoriteTVShowDatabase = FavoriteTVShowDatabase.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    boolean isEmptyFields = true, isMinimalCharacter = true;
                    String inputQuery = s.trim();

                    if (inputQuery.isEmpty()) {
                        Toast.makeText(MainActivity.this, R.string.empty_text, Toast.LENGTH_SHORT).show();
                    } else {
                        isEmptyFields = false;
                    }


                    if (inputQuery.length() < 3) {
                        Toast.makeText(MainActivity.this, R.string.three_characters, Toast.LENGTH_SHORT).show();
                    } else {
                        isMinimalCharacter = false;
                    }


                    if (!isEmptyFields && !isMinimalCharacter) {
                        if (cek.equals("movie")) {
                            MovieFragment movieFragment = new MovieFragment();
                            Bundle extraBundle = new Bundle();
                            extraBundle.putString("QUERY_MOVIE_SEARCH", inputQuery);
                            movieFragment.setArguments(extraBundle);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_main, movieFragment, MovieFragment.class.getSimpleName())
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        } else if (cek.equals("tv")) {
                            TVShowFragment tvShowFragment = new TVShowFragment();
                            Bundle extraBundle = new Bundle();
                            extraBundle.putString("QUERY_TV_SHOW_SEARCH", inputQuery);
                            tvShowFragment.setArguments(extraBundle);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_main, tvShowFragment, TVShowFragment.class.getSimpleName())
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_change_language:
                Intent chageLanguageSettings = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(chageLanguageSettings);
                break;
            case R.id.favorite_movie:
                Intent favoriteMovie = new Intent(MainActivity.this, FavoriteMovieActivity.class);
                startActivity(favoriteMovie);
                break;
            case R.id.favorite_tv_show:
                Intent favoriteTVShow = new Intent(MainActivity.this, FavoriteTVShowActivity.class);
                startActivity(favoriteTVShow);
                break;
            case R.id.reminder:
                Intent toSettingsReminder = new Intent(MainActivity.this, SettingsReminder.class);
                startActivity(toSettingsReminder);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
