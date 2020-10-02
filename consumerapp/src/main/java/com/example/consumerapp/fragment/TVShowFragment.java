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

import com.example.consumerapp.FavoriteTVShow;
import com.example.consumerapp.R;
import com.example.consumerapp.adapter.tv_adapter.TVShowAdapter;
import com.example.consumerapp.viewModel.TVShowViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {
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
    private TVShowAdapter tvShowAdapter;

    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        tvShowAdapter = new TVShowAdapter(getContext());
        recyclerView.setAdapter(tvShowAdapter);
        TVShowViewModel tvShowViewModel = ViewModelProviders.of(this).get(TVShowViewModel.class);
        tvShowViewModel.getTVShow().observe(this, getTVShow);
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI_TV_SHOW, null, null, null, null);
        if (cursor != null) {
            tvShowViewModel.setListTVShow(cursor);
        }
    }

    private Observer<List<FavoriteTVShow>> getTVShow = new Observer<List<FavoriteTVShow>>() {
        @Override
        public void onChanged(List<FavoriteTVShow> favoriteTVShowList) {
            if (favoriteTVShowList != null) {
                tvShowAdapter.setData(favoriteTVShowList);
            }
        }
    };


}
