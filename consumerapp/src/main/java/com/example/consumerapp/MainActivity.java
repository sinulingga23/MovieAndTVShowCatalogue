package com.example.consumerapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.consumerapp.fragment.MovieFragment;
import com.example.consumerapp.fragment.TVShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

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
//                    setTitle(R.string.movie_catalogue);
                    setTitle(R.string.consumer_app);
                    return true;
                case R.id.navigation_tv:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_main, new TVShowFragment(), TVShowFragment.class.getSimpleName())
                            .commit();
//                    setTitle(R.string.tv_show);
                    setTitle(R.string.consumer_app);
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

    }


    private void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
