package com.example.consumerapp.fragment;


import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerapp.FavoriteMovie;
import com.example.consumerapp.R;
import com.example.consumerapp.adapter.movie_adapter.MovieAdapter;
import com.example.consumerapp.viewModel.MovieViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private static final String AUHTORITY = "com.example.movieandtvshowcatalogue";
    private static final String SCHEME = "content";
    private static final String TABLE_FAVORTIE_MOVIE = "favoriteMovie";
    private static final String TABLE_FAVORITE_TV_SHOW = "favoriteTVShow";
    public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME).authority(AUHTORITY).appendPath(TABLE_FAVORTIE_MOVIE).build();
    public static final Uri CONTENT_URI_TV_SHOW = new Uri.Builder().scheme(SCHEME).authority(AUHTORITY).appendPath(TABLE_FAVORITE_TV_SHOW).build();
    private static final int ACCESS_MOVIE = 1;
    private static final int ACCESS_TV_SHOW = 2;
    private static final UriMatcher sUriMathcer = new UriMatcher(UriMatcher.NO_MATCH);
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_movie, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(getContext());
        recyclerView.setAdapter(movieAdapter);
        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovies);
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);
        if (cursor != null) {
            movieViewModel.setListMovies(cursor);
        }
    }

    private Observer<List<FavoriteMovie>> getMovies = new Observer<List<FavoriteMovie>>() {
        @Override
        public void onChanged(List<FavoriteMovie> favoriteMovieList) {
            if (favoriteMovieList != null) {
                movieAdapter.setData(favoriteMovieList);
            }
        }
    };

}
