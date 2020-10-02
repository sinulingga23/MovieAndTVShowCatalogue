package com.example.movieandtvshowcatalogue.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandtvshowcatalogue.R;
import com.example.movieandtvshowcatalogue.adapter.tv_adapter.TVShowAdapter;
import com.example.movieandtvshowcatalogue.model.TVShow;
import com.example.movieandtvshowcatalogue.viewModel.TVShowViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {
    public static final String QUERY_MOVIE_SEARCH = "QUERY_MOVIE_SEARCH";
    private TVShowAdapter tvShowAdapter;
    private ProgressBar progressBar;
    private TVShowViewModel tvShowViewModel;

    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        tvShowAdapter = new TVShowAdapter(getContext());
        RecyclerView rvTVShow = view.findViewById(R.id.rv_tv);
        rvTVShow.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvTVShow.setAdapter(tvShowAdapter);

        progressBar = view.findViewById(R.id.pb_tv);
        String querySearch = null;
        if (getArguments() != null) {
            querySearch = getArguments().getString(QUERY_MOVIE_SEARCH);
        }
        tvShowViewModel = ViewModelProviders.of(this).get(TVShowViewModel.class);
        tvShowViewModel.setListTVShow(querySearch);
        tvShowViewModel.getTVShow().observe(this, getTVShow);

        showLoading(true);
        return view;
    }

    private Observer<ArrayList<TVShow>> getTVShow = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(ArrayList<TVShow> tvShows) {
            if (tvShows != null) {
                tvShowAdapter.setData(tvShows);
            }
            showLoading(false);
        }
    };

    public void showLoading(Boolean pb) {
        if (pb) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
