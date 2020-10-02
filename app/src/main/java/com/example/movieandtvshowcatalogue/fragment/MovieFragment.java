package com.example.movieandtvshowcatalogue.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.movieandtvshowcatalogue.R;
import com.example.movieandtvshowcatalogue.adapter.movie_adapter.MovieAdapter;
import com.example.movieandtvshowcatalogue.model.Movie;
import com.example.movieandtvshowcatalogue.viewModel.MovieViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    public static final String QUERY_MOVIE_SEARCH = "QUERY_MOVIE_SEARCH";
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_movie, container, false);
        movieAdapter = new MovieAdapter(getContext());
        RecyclerView rvMovie = view.findViewById(R.id.rv_movie);
        rvMovie.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvMovie.setAdapter(movieAdapter);

        progressBar = view.findViewById(R.id.pb_movie);

        String querySearch = null;
        if (getArguments() != null) {
            querySearch = getArguments().getString(QUERY_MOVIE_SEARCH);
        }
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.setListMovies(querySearch);
        movieViewModel.getMovies().observe(this, getMovies);

        showLoading(true);
        return view;
    }

    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                movieAdapter.setData(movies);
            }
            showLoading(false);
        }
    };

    private void showLoading(Boolean pb) {
        if (pb) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
